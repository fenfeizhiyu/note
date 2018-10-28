## redis实现

---

### 一.动态字符串

#### 1.Redis没有用C语言默认的字符串结构，而是自己定义了一个字符串结构，兼容C语言的字符串结构，可以复用c语言里面的部分字符串函数。

  * 可以方便的查询字符串长度，时间复杂度为O(1),如果用C语言结构，复杂度为O(n),只需要访问其中的len成员。
  * sds结构中一些操作是二进制安全的，因为sds使用len属性来判断字符串结尾，而不是0，不会因为空格等特殊字符串而发生中断，不会溢出。
  * 关键是减少了字符串内存重分配次数。（检查修改后的字符串长度，如果小于1MB则分配2倍的大小，如果大于1MB，则分配1MB未使用空间，最后加上一Byte是0）
  * 惰性空间释放，删除字符时，多出来的空间不会释放，而是通过free字段记录。

### 二. 链表

#### 1. 链表提供了高效的节点重排能力，以及顺序的节点访问。列表键的实现，发布与订阅，慢查询，监视器等都是用链表来实现。

  * 双端：链表节点有prev和next指针，获取节点的前置节点，和后续节点的复杂度都是O(1)
  * 无环: 表头和表尾都是Null，
  * 有表头和表尾指针
  * 有链表计数len
  * 多态，节点里面使用void*指针存放数据，可存任意类型，节````点内设置3个函数指针dup,free,match用于操作节点数据，所以链表里面可以保存不同类型的数据。

### 三.字典

#### 1.字典(关联数组，映射)，是保存键值对的抽象数据结构。

    /* 字典结构 每个字典有两个哈希表，实现渐进式哈希时需要用在将旧表rehash到新表 */
    typedef struct dict {

      dictType *type; /* 类型特定函数 */
      void *privdata; /* 保存类型特定函数需要使用的参数 */
      dictht ht[2]; /* 保存的两个哈希表，ht[0]是真正使用的，ht[1]会在rehash时使用 */
      long rehashidx; /* rehashing not in progress if rehashidx == -1 rehash进度，如果不等于-1，说明还在进行rehash */
      unsigned long iterators; /* number of iterators currently running 正在运行中的遍历器数量 */

    } dict;


    /* 哈希表结构 */
    typedef struct dictht {
      dictEntry **table; /* 哈希表节点数组 */
      unsigned long size; /* 哈希表大小 */
      unsigned long sizemask; /* 哈希表大小掩码，用于计算哈希表的索引值，大小总是dictht.size - 1 */
      unsigned long used; /* 哈希表已经使用的节点数量 */
    } dictht;

    /* 哈希表节点 */
    typedef struct dictEntry {
      void *key; /* 键名 */
      union {
          void *val;
          uint64_t u64;
          int64_t s64;
          double d;
        } v; /* 值 */
     struct dictEntry *next; /* 指向下一个节点, 将多个哈希值相同的键值对连接起来*/
     } dictEntry;

     /* 保存一连串操作特定类型键值对的函数 */
     typedef struct dictType {
       uint64_t (*hashFunction)(const void *key); /* 哈希函数 */
       void *(*keyDup)(void *privdata, const void *key); /* 复制键函数 */
       void *(*valDup)(void *privdata, const void *obj); /* 复制值函数 */
       int (*keyCompare)(void *privdata, const void *key1, const void *key2); /* 比较键函数 */
       void (*keyDestructor)(void *privdata, void *key); /* 销毁键函数 */
       void (*valDestructor)(void *privdata, void *obj); /* 销毁值函数 */
    } dictType;

#### 2.dictht

    redis使用字典dict定义，里面有个函数指针type用于实现多态，里面包含两个dictht哈希表，每个哈希表记录了该表大小，哈希掩码等信息，一个用于存放值，一个用于进行rehash。dictht里面定义了一个dictEntry数组，用于存放数据，每个dictEntry保存着一个键值对，值用一个结构体表示，里面定义了一个void*指针，可以存放任意结构数据，也定义unit64_t指针，int64_t指针，存放整数。dictEntry里面定义了next指针，指向下一个相同键的值。

#### 3.哈希算法

    当字典被泳衣数据库底层实现时，或者hash键的底层实现时，Redis使用MurmurHash2算法计算hash值，好处是对于有规律的键输入，也有很好的随机性分布。


#### 4.rehash

    整个rehash过程不是一步完成的，而是分多次完成，如果hash表中存在巨大的的键值对时，若一次进行reshash,很有肯能造成服务器宕机。

  - 为ht[1]分配空间，让字典同时持有ht[0]和ht[1]两个hash表
  - 维持索引计数器变量rehasidx,并将它的值设为0，表示rehash开始。
  - 每次对字典进行增删改查时，新增数据会增加的ht[1]上，同时将ht[0]的rehashidx的索引上的所有键值对rehash到ht[1],将rehashidex值加1。
  - 当ht[0]的所有键值对都被rehash到ht[1]中，程序将rehashidx的值设置为-1，表示rehash操作完成

### 四.跳跃表

    如果一个有序集合包含的元素数量比较多，又或者有序集合中元素的成员是比较长的字符串时，redis就会使用跳跃表。

    * 跳跃表:
      - 一个跳表应该有几个层（level）组成；
      - 跳表的第一层包含所有的元素；
      - 每一层都是一个有序的链表；
      - 如果元素x出现在第i层，则所有比i小的层都包含x；
      - 第i层的元素通过一个down指针指向下一层拥有相同值的元素；
      - 在每一层中，-1和1两个元素都出现(分别表示INT_MIN和INT_MAX)；
      - Top指针指向最高层的第一个元素。

    * 理想跳跃表，最底层保存所有元素，每上一层元素个数是下一层的2分之1，且均匀分布.
    * 简单跳跃表的实现，同过概率来实现分层，当新增一个数据时，先保存到第一层，然后利用概率(1/2)决定是否在上一层保存，如果是，则创建一层，将这个节点保存到上一层，在数据量大的情况下，根据概率分布，接近理想跳跃表。
    * redis跳跃表:
      - 1.随机算法,层数越高，出现的概率越小。
      - 2.插入时，先找到插入位置，再根据跨度计算节点排名，创建节点时，利用随机函数生成这个节点的层数。
      - 3.查找时，从高层往下查找、

### 五 整数集合

    当一个集合只包含整数，并且数量不多时会用整数集合。

    * 利用一个结构体intset表示 ,里面数组contents存放元素，length记录元素大小，uint32_t,记录整数类型,(int16,int32,int64)
    * contents里面的元素类型都是一样的，当新加入的元素类型大于已有类型时，会对所有元素进行升级。
    * contents里面元素只升级，不降级。

### 六 压缩列表

    ziplist是列表键和哈希键的底层实现之一，当一个列表键只包含少量列表项，并且每个列表项要么是小整数值，要么就是长度比较短的字符串，那么就用redis压缩列表。

    * 压缩列表有多个连续存放的entry组成，外面包含一些必要信息，依次是,zlbyets:记录压缩列表占用内存字节数，zltail记录压缩列表最后一个entry的距离起始位置的偏移量，zllen记录entry数量，zlend结束符。
    * 一个entry由previous_entry_length，encoding,content组成，previous_entry_length记录上一个entry的长度，encoding,记录保存的数据类型以及长度。content存放内容。
      - previous_entry_length有一个字节或者5个字节。当前一个entry长度小于254字节时，那它是1字节，否则是5字节。利用它可以算出上一个节点的起始位置，最终可以从表尾向表头回溯整个列表。
    * 连锁更新： 当插入新节点，或者删除节点时，由于会计算更新节点位置的previous_entry_length长度，可能会触发previous_entry_length由1字节变为5字节。


### 七 对象

  redis没有直接用上面的数据结构实现键值对数据库，而是基于这些数据结构实现一个对象系统。字符串对象，列表对象，哈希对象，集合对象，有序集合对象。
    - 可以根据不同的场景采用不同的数据结构。
    - 在执行命令时，可以检查对象类型。
    - 对象系统还实现了基于引用计数的内存回收机制，当程序不使用某个对象的时候，这个对象所占内存会被收回。
    - 通过引用计数实现了对象共享机制，在不同数据库键之间共享。
    - 对象里面带有访问时间记录信息，可以计算数据库键的空转时间，在设置了maxmemory的时候，空转时长大的会被删除。
    - 对象由redisObject表示，里面有type字段记录对象类型，encoding记录编码，ptr泛型指针指向数据存放地址
    - type命令查看对象类型，object encoding命令查看对象编码
    - 编码表:

| 编码常量 | 所对应的底层结构 |
|---|---|
| redis_encoding_int  | long类型的整数  |
|redis_encoding_embstr |embstr编码的简单SDS|
| redis_encoding_raw|简单sds   |  
|redis_encoding_ht   | 字典 |   
|redis_encoding_linkedlist   |双端链表   |   
|redis_encoding_ziplist   |压缩链表   |   
|redis_encoding_intset   | 整数集合  |   
|redis_encoding_skiplist   |跳跃表和字典   |   


    1、字符串对象，有三种编码，对应3种实现，int,raw,embstr
      - embstr 编码专门保存短字符串对象，这种类型的对象只需一次内存分配即可创建，其中数据长度固定为39字节。
      - raw为保存比较长的字符串设计，需要单独为数据存储分配一次内存。
      - long类型专门保存整数。
      - Long类型的字符串，在修改为不是整数的字符串时会变为raw类型，embstr类型的字符串，在发生修改时会变为raw类型。
    对于double类型的数据，redis会转换为字符串进行存储，在需要计算的时候，把字符串转换为数字。

    2.列表对象,可以是ziplist和linkedList的实现.
        * 当列表对象保存的所有字符串对象长度都小于64个字符。
        * 列表对象的元素个数小于512个。
     这些会用ziplist实现，否则会用linkedList实现。
     
    3.哈希对象，可以用ziplist或者hashtable实现。hash对象，即允许值有多个field,每个field是一个键值对。
     
        *  采用压缩列表时，field的键和值紧挨着存放，接着是下一个field,先加入的会在表头位置，后加入的在表尾位置。
        
        *   采用hashtable实现时，每个键值对都采用一个字典值来实现。
        
    转换条件：当哈希对象所存键和值得字符串长度都小于64字，对象的field数量小于512个时采用ziplist.。否则用hashTable.
    
    4. 集合对象，可以用intset或者hashTable实现。
        *  当采用intset实现时，集合元素按字符串排列，
        * 当采用hashTable实现时，字典的每个键是一个字符串对象，包含一个集合元素，其值为null.
        
    转换条件：当集合对象所存键和值得字符串长度都小于64字，对象的field数量小于512个时采用ziplist.。否则用hashTable.
    
    5 有序集合，采用ziplist或者skiplist实现。
        * ziplist 结构中，按分值大小保存所有元素，成员在前，分值紧随其后。
        
        * skiplist结构中，采用zset结构，zset由zskiplist和dict组成，其中，dict的键保存了成员值，dict的值保存了其分值，通过字典dict可以采用O(1)来查找分值。
          zset的zskiplist按分值从小到大保存了元素的成员和分值，通过跳跃表可以进行范围操作。
          
    转换条件:有序集合对象保存的元素小于128个，所有元素成员长度小于64字节时采用ziplist，否则采用skiplist.
     
    6.对象的空转时长，通过redisObject的lru属性来保存，记录了对象最后一次的访问时间，可以用于计算空转时长，和在设置了最大内存时，和内存回收算法为volatile-lru或者allkeys-lru那么空转时间较高的会被优先回收。
    
        
      
     
