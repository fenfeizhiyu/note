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
  * 多态，节点里面使用void*指针存放数据，可存任意类型，节点内设置3个函数指针dup,free,match用于操作节点数据，所以链表里面可以保存不同类型的数据。

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
