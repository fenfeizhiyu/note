## redis databse

---

### 一.读写键空间时，对键的维护。

    *  读取一个键后，会根据该键是否存在，来记录命中次数。
    *  读取时，会更新键的LRU时间，用于计算闲置时间。
    *   会判断是否过期，如果过期，会先删除这个过期键，然后进行余下的操作。
    *  如果采用waatch命令监视了该键，那么服务器会在修改这个键后，将该键标记为dirty,从而让事务程序注意到该键的更改。
    *  每修改一个键会对脏计数器加1， 