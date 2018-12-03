## redis 命令

---

### 一.redis的启动

    启动:nohup ./redis.sh > temp.log 2>&1 &

    登录:redis-cli


### 二.键的命令:

  1.del命令：
   删除n个字符串的复杂度为O(N) ，删除单个列表，集合，有序集合或哈希表，时间复杂度为O(m),m为以上数据结构的元素数量.
   返回被删数量。

  2.expireat,expire,pexpireat  key

    pexpireat:它以毫秒为单位设置 key 的过期 unix 时间戳
    expireat:以秒为单位设置 key 的过期 unix 时间戳
    expire:以秒为单位设置过期时间

    设置键的过期时间.expireat命令接受的时间参数是unix时间戳。

  3.keys: 查找符合给定模式的pattern的key。

    KEYS 的速度非常快，但在一个大的数据库中使用它仍然可能造成性能问题，如果你需要从一个数据集中查找特定的 key ，你最好还是用 Redis 的集合结构(set)来代替。

  4.Object:命令允许从内部察看给定 key 的 Redis 对象。

  * OBJECT REFCOUNT <key> 返回给定 key 引用所储存的值的次数。此命令主要用于除错。
  * OBJECT ENCODING <key> 返回给定 key 锁储存的值所使用的内部表示(representation)。
  * OBJECT IDLETIME <key> 返回给定 key 自储存以来的空闲时间(idle， 没有被读取也没有被写入)，以秒为单位。

  5.PERSIST key 移除key的生存时间

    当生存时间移除成功时，返回 1 .

  6.TTL,pttl

  * TTL：以秒为单位返回
  * pttl:以毫秒为单位返回

  7.RANDOMKEY 从当前数据库中随机返回(不删除)一个 key 。

  8.RENAME key newkey 为键改名

  * 当 key 和 newkey 相同，或者 key 不存在时，返回一个错误。
  * 当 newkey 已经存在时， RENAME 命令将覆盖旧值。

  9.RENAMENX key newkey

  * 当且仅当 newkey 不存在时，将 key 改名为 newkey 。
  * 当 key 不存在时，返回一个错误。


### 三.字符串命令

  1.APPEND key value追加字符串,返回追加 value 之后， key 中字符串的长度。

  * 如果 key 已经存在并且是一个字符串， APPEND 命令将 value 追加到 key 原来的值的末尾。
  * 如果 key 不存在， APPEND 就简单地将给定 key 设为 value ，就像执行 SET key value 一样。

   应用:可以为一系列定长(fixed-size)数据(sample)提供一种紧凑的表示方式，通常称之为时间序列。通过getrange,setrange,strlen来操作。


  2.BITCOUNT key [start] [end]  计算给定字符串中，被设置为 1 的比特位的数量。

  * 一般情况下，给定的整个字符串都会被进行计数，通过指定额外的 start 或 end 参数，可以让计数只在特定的位上进行。start 和 end 参数的设置和 GETRANGE 命令类似，都可以使用负数值： 比如 -1 表示最后一个字节， -2 表示倒数第二个字节，以此类推。
  * 不存在的 key 被当成是空字符串来处理，因此对一个不存在的 key 进行 BITCOUNT 操作，结果为 0 。

  3.BITOP operation destkey key [key ...] 对一个或多个保存二进制位的字符串 key 进行位元操作，并将结果保存到 destkey 上。

  * BITOP AND destkey key [key ...] ，对一个或多个 key 求逻辑并，并将结果保存到 destkey 。
  * BITOP OR destkey key [key ...] ，对一个或多个 key 求逻辑或，并将结果保存到 destkey 。
  * BITOP XOR destkey key [key ...] ，对一个或多个 key 求逻辑异或，并将结果保存到 destkey 。
  * BITOP NOT destkey key ，对给定 key 求逻辑非，并将结果保存到 destkey 。


  4. DECR key 将 key 中储存的数字值减一

  5. DECRBY key decrement 将 key 所储存的值减去减量 decrement 。

  6. GETBIT key offset 对 key 所储存的字符串值，获取指定偏移量上的位(bit)。

  - 当 offset 比字符串值的长度大，或者 key 不存在时，返回 0

  7. GETRANGE key start end

  * 返回 key 中字符串值的子字符串，字符串的截取范围由 start 和 end 两个偏移量决定(包括 start 和 end 在内)。
  * 负数偏移量表示从字符串最后开始计数， -1 表示最后一个字符， -2 表示倒数第二个，以此类推。
  8. GETSET key value 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。

  * 当 key 存在但不是字符串类型时，返回一个错误。
  * GETSET 可以和 INCR 组合使用，实现一个有原子性(atomic)复位操作的计数器(counter)。
  * 例子:每次当某个事件发生时，进程可能对一个名为 mycount 的 key 调用 INCR 操作，通常我们还要在一个原子时间内同时完成获得计数器的值和将计数器值复位为 0 两个操作。

  9. INCR key 将 key 中储存的数字值增一。
  10. INCRBY key increment 将 key 所储存的值加上增量 increment 。
  11. INCRBYFLOAT key increment 为 key 中所储存的值加上浮点数增量 increment 。

  * 如果 key 不存在，那么 INCRBYFLOAT 会先将 key 的值设为 0 ，再执行加法操作。
  * 无论是 key 的值，还是增量 increment ，都可以使用像 2.0e7 、 3e5 、 90e-2 那样的指数符号(exponential notation)来表示，但是，执行 INCRBYFLOAT 命令之后的值总是以同样的形式储存，也即是，它们总是由一个数字，一个（可选的）小数点和一个任意位的小数部分组成（比如 3.14 、 69.768 ，诸如此类)，小数部分尾随的 0 会被移除，如果有需要的话，还会将浮点数改为整数（比如 3.0 会被保存成 3 ）。

  12. MGET key [key ...] 返回所有(一个或多个)给定 key 的值。
    * 如果给定的 key 里面，有某个 key 不存在，那么这个 key 返回特殊值 nil 。因此，该命令永不失败。
  13. MSET key value [key value ...]  同时设置一个或多个 key-value 对。
  14. MSETNX key value [key value ...] 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在。
  15. PSETEX key milliseconds value 它以毫秒为单位设置 key 的生存时间
  16. SETEX key seconds value 将值 value 关联到 key ，并将 key 的生存时间设为 seconds (以秒为单位)。
  17. SETBIT key offset value 对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)。
  18. SETNX key value  将 key 的值设为 value ，当且仅当 key 不存在。
  19. SETRANGE key offset value  用 value 参数覆写(overwrite)给定 key 所储存的字符串值，从偏移量 offset 开始。

    * SETRANGE 命令会确保字符串足够长以便将 value 设置在指定的偏移量上，如果给定 key 原来储存的字符串长度比偏移量小(比如字符串只有 5 个字符长，但你设置的 offset 是 10 )，那么原字符和偏移量之间的空白将用零字节(zerobytes, "\x00" )来填充。
    * 注意你能使用的最大偏移量是 2^29-1(536870911) ，因为 Redis 字符串的大小被限制在 512 兆(megabytes)以内。如果你需要使用比这更大的空间，你可以使用多个 key 。
    * 当生成一个很长的字符串时，Redis 需要分配内存空间，该操作有时候可能会造成服务器阻塞(block)。在2010年的Macbook Pro上，设置偏移量为 536870911(512MB 内存分配)，耗费约 300 毫秒，设置偏移量为 134217728(128MB 内存分配)，耗费约 80 毫秒，设置偏移量 33554432(32MB 内存分配)，耗费约 30 毫秒，设置偏移量为 8388608(8MB 内存分配)，耗费约 8 毫秒。 注意若首次内存分配成功之后，再对同一个 key 调用 SETRANGE 操作，无须再重新内存。

  20. STRLEN key 返回 key 所储存的字符串值的长度。

    * 位的设置或清除取决于 value 参数，可以是 0 也可以是 1 。
    * offset 参数必须大于或等于 0 ，小于 2^32 (bit 映射被限制在 512 MB 之内)。
    * 对使用大的 offset 的 SETBIT 操作来说，内存分配可能造成 Redis 服务器被阻塞。

  21. BITFIELD key [GET type offset] [SET type offset value] [INCRBY type offset increment] [OVERFLOW WRAP|SAT|FAIL]  BITFIELD 命令可以将一个 Redis 字符串看作是一个由二进制位组成的数组， 并对这个数组中储存的长度不同的整数进行访问 （被储存的整数无需进行对齐）。 换句话说， 通过这个命令， 用户可以执行诸如 “对偏移量 1234 上的 5 位长有符号整数进行设置”、 “获取偏移量 4567 上的 31 位长无符号整数”等操作。 此外， BITFIELD 命令还可以对指定的整数执行加法操作和减法操作， 并且这些操作可以通过设置妥善地处理计算时出现的溢出情况。

    * GET <type> <offset> - 返回指定的位域
    * SET <type> <offset> <value> - 设置指定的位域并返回其旧值。
    * INCRBY <type> <offset> <increment> - 递增或递减（如果给定负递增）指定的位域并返回新值。
    * OVERFLOW :通过设置溢出行为来改变连续的 INCRBY子 命令调用的行为
      - 在期望整数类型的情况下，可以通过i为有符号整数和u无符号整数加上整数类型的位数来构成它。例如u8，一个8位的无符号整数，i16是一个16位的有符号整数。支持的类型对于有符号整数最多为64位，对于无符号整数最多为63位。使用无符号整数的限制是由于当前Redis协议无法将64位无符号整数作为答复返回。位和位置偏移有两种方式可以指定位域命令中的偏移量。如果指定了一个没有任何前缀的数字，它将被用作字符串内的基于零的位偏移量。但是如果偏移量前缀为a#字符，指定的偏移量乘以整数类型的宽度，例如：BITFIELD mystring SET i8＃0 100 i8＃1 200将设置第一个i8整数在偏移量0和第二个偏移量为8.这种方式你没有如果你想要的是一个给定大小的整数数组，你可以在你的客户端内部进行数学运算。溢出控制使用该OVERFLOW命令，用户可以通过指定一个来微调增量的行为或减少溢出（或下溢）以下行为：
        - WRAP：环绕，包含有符号和无符号整数。在无符号整数的情况下，包装类似于以整数可以包含的最大值（C标准行为）来执行操作。使用带符号整数，而不是包装意味着溢出重新开始朝向最负值，并且溢出朝向最正值，例如，如果i8整数设置为127，则将其递增1 -128。
        - SAT：使用饱和算术，即在下溢时将该值设置为最小整数值，并在溢出时将其设置为最大整数值。例如，i8从数值120开始递增一个以10 为增量的整数将导致数值127，并且进一步增量将始终使数值保持在127.在下溢时发生同样的情况，但是朝向该数值被阻塞在最大负值。
        - FAIL：在这种模式下，没有检测到溢出或下溢操作。相应的返回值设置为 NULL，以向调用者发送信号。

      - 每条OVERFLOW语句只影响子命令列表中后面的 INCRBY命令，直到下一条OVERFLOW语句为止。

    * 例子:
      - BITFIELD mykey incrby u2 100 1 OVERFLOW SAT incrby u2 102 1
      - BITFIELD mykey incrby u2 100 1 OVERFLOW SAT incrby u2 102 1
    * 位的顺序
      - 00000001 01110000  BITFIELD 使用的表示认为位图的位号为0是第一个字节的最高有效位，依此类推，因此例如在偏移7处将5位无符号整数设置为值23

### 四. 哈希表命令

  1. HSET key field value 将哈希表 key 中的域 field 的值设为 value 。
  2. HDEL key field [field ...] 删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。
  3. HEXISTS key field 查看哈希表 key 中，给定域 field 是否存在。
  4. HGET key field 返回哈希表 key 中给定域 field 的值。
  5. HGETALL key 返回哈希表 key 中，所有的域和值。

    * 在返回值里，紧跟每个域名(field name)之后是域的值(value)，所以返回值的长度是哈希表大小的两倍。

  6. HINCRBY key field increment 为哈希表 key 中的域 field 的值加上增量 increment 。

    * 对一个储存字符串值的域 field 执行 HINCRBY 命令将造成一个错误。
    * 本操作的值被限制在 64 位(bit)有符号数字表示之内。
  7. HINCRBYFLOAT key field increment 为哈希表 key 中的域 field 加上浮点数增量 increment 。
  8. HKEYS key 返回哈希表 key 中的所有域。
  9. HLEN key 返回哈希表 key 中域的数量。
  10. HMGET key field [field ...] 返回哈希表 key 中，一个或多个给定域的值。
  11. HMSET key field value [field value ...] 同时将多个 field-value (域-值)对设置到哈希表 key 中。
  12. HSETNX key field value 将哈希表 key 中的域 field 的值设置为 value ，当且仅当域 field 不存在。
  13. HVALS key 返回哈希表 key 中所有域的值。
  14. HSTRLEN key field 返回哈希表 key 中， 与给定域 field 相关联的值的字符串长度（string length）。

### 五.列表

  1. BLPOP key [key ...] timeout 列表的阻塞式(blocking)弹出原语。

    * 它是 LPOP 命令的阻塞版本，当给定列表内没有任何元素可供弹出的时候，连接将被 BLPOP 命令阻塞，直到等待超时或发现可弹出元素为止。
    * 当给定多个 key 参数时，按参数 key 的先后顺序依次检查各个列表，弹出第一个非空列表的头元素。
    * 当 BLPOP 被调用时，如果给定 key 内至少有一个非空列表，那么弹出遇到的第一个非空列表的头元素，并和被弹出元素所属的列表的名字一起，组成结果返回给调用者。
    * 如果所有给定 key 都不存在或包含空列表，那么 BLPOP 命令将阻塞连接，直到等待超时，或有另一个客户端对给定 key 的任意一个执行 LPUSH 或 RPUSH 命令为止。
    * 相同的 key 可以被多个客户端同时阻塞。

  2. BRPOP 是列表的阻塞式(blocking)弹出原语。 弹出尾部元素
  3. RPOPLPUSH source destination

    * 将列表 source 中的最后一个元素(尾元素)弹出，并返回给客户端。
    * 将 source 弹出的元素插入到列表 destination ，作为 destination 列表的的头元素。

  4. BRPOPLPUSH source destination timeout
    * 当列表 source 为空时， BRPOPLPUSH 命令将阻塞连接，直到等待超时，或有另一个客户端对 source 执行 LPUSH 或 RPUSH 命令为止。超时参数 timeout 接受一个以秒为单位的数字作为值。超时参设为 0 表示阻塞时间可以无限期延长(block indefinitely) 。

  5. LINDEX key index 返回列表 key 中，下标为 index 的元素。
  6. LINSERT key BEFORE|AFTER pivot value
  * 将值 value 插入到列表 key 当中，位于值 pivot 之前或之后。

  7. LLEN key 返回列表 key 的长度。

  8. LPOP key 移除并返回列表 key 的头元素。
  9. LPUSH key value [value ...] 将一个或多个值 value 插入到列表 key 的表头
  10. LPUSHX key value  

  * 将值 value 插入到列表 key 的表头，当且仅当 key 存在并且是一个列表。
  * 和 LPUSH 命令相反，当 key 不存在时， LPUSHX 命令什么也不做。

  11. LRANGE key start stop 返回列表 key 中指定区间内的元素，区间以偏移量 start 和 stop 指定。
  12. LREM key count value 根据参数 count 的值，移除列表中与参数 value 相等的元素。

  * count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
  * count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
  * count = 0 : 移除表中所有与 value 相等的值。

  13. LSET key index value 将列表 key 下标为 index 的元素的值设置为 value 。
  14. LTRIM key start stop

  * 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
  * 执行命令 LTRIM list 0 2 ，表示只保留列表 list 的前三个元素，其余元素全部删除。

  15. RPOP key  移除并返回列表 key 的尾元素。

  16. RPOPLPUSH source destination

    * 将列表 source 中的最后一个元素(尾元素)弹出，并返回给客户端。
    * 将 source 弹出的元素插入到列表 destination ，作为 destination 列表的的头元素。
    * 如果 source 不存在，值 nil 被返回，并且不执行其他动作。
    * 有两个列表 source 和 destination ， source 列表有元素 a, b, c ， destination 列表有元素 x, y, z ，执行 RPOPLPUSH source destination 之后， source 列表包含元素 a, b ， destination 列表包含元素 c, x, y, z ，并且元素 c 会被返回给客户端。

  17. RPUSH key value [value ...] 将一个或多个值 value 插入到列表 key 的表尾(最右边)
    * 如果 key 不存在，一个空列表会被创建并执行 RPUSH 操作。

  18. RPUSHX key value 将值 value 插入到列表 key 的表尾，当且仅当 key 存在并且是一个列表。


### 六 集合

  1. SADD key member [member ...] 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。
  2. SCARD key 返回集合 key 的基数(集合中元素的数量)。
  3. SDIFF key [key ...] 返回一个集合的全部成员，该集合是所有给定集合之间的差集。
  4. SDIFFSTORE destination key [key ...]
  * 这个命令的作用和 SDIFF 类似，但它将结果保存到 destination 集合，而不是简单地返回结果集。
  * 如果 destination 集合已经存在，则将其覆盖。
  5. SISMEMBER key member 判断 member 元素是否集合 key 的成员。
  6. SMEMBERS key 返回集合 key 中的所有成员。
  7. SMOVE source destination member 将 member 元素从 source 集合移动到 destination 集合。
  8. SPOP key 移除并返回集合中的一个随机元素。
  9. SRANDMEMBER key [count] 那么返回集合中的一个随机元素。
    * 如果 count 为负数，那么命令返回一个数组，数组中的元素可能会重复出现多次，而数组的长度为 count 的绝对值。
    * 如果 count 为正数，且小于集合基数，那么命令返回一个包含 count 个元素的数组，数组中的元素各不相同。如果 count 大于等于集合基数，那么返回整个集合

  10. SREM key member [member ...] 移除集合 key 中的一个或多个 member 元素，不存在的 member 元素会被忽略。

  11. SUNION key [key ...] 返回一个集合的全部成员，该集合是所有给定集合的并集。

  12. SUNIONSTORE destination key [key ...] 这个命令类似于 SUNION 命令，但它将结果保存到 destination 集合，而不是简单地返回结果集。


### 七 有序集合

  1. ZADD key score member [[score member] [score member] ...] 将一个或多个 member 元素及其 score 值加入到有序集 key 当中。

  2. ZCARD key 返回有序集 key 的基数。
  3. ZCOUNT key min max 返回有序集 key 中， score 值在 min 和 max 之间(默认包括 score 值等于 min 或 max )的成员的数量。
  4. ZINCRBY key increment member 为有序集 key 的成员 member 的 score 值加上增量 increment 。
  5. ZRANGE key start stop [WITHSCORES] 返回有序集 key 中，指定区间内的成员。
  6.
