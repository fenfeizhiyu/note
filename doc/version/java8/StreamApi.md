# java8API最大的2个改变：lambda表达式和StreamApi。

## 一.Stream的特性：

    * 自己不存储元是
    * 处理过程中不改变对象
    * Stream操作是延迟执行，只有需要数据的时候才会去执行，可以创建无限流。
    * 流有3种基本原始类型特化：IntStream,DoubleStream,LongStream.
    
## 二.使用Stream流有3步：创建流，中间操作，终止操作。

### 1.创建流有四种方式。
    
    * 通过Collection集合的Stream()或者parallelStream()方法分别创建串行流和并行流。
    * 通过数组Arrays类的静态方法由数据创建流
    * 直接用Stream.of方法由指定值直接创建流。
    * 通过文件创建流。利用File.lines()方法返回一个文件流，每个元素都是其中的一行内容。
    * 通过函数创建流，Stream.iterate()和Stream.generate()创建无限流。前者通过迭代生成无限流，后者直接调用函数生成无限流，例如随机函数。

### 2.中间操作，在触发终止操作时会一次性全部处理，没有触发终止操作则不会处理。中间操作定义成Stream类的实例方法。
    
    * 筛选和切片:filter过滤，limit限制返回元素数量，skip根据条件跳过元素,distinct去重。
    * 映射:Map将元素经过函数后转换为一个新的元素。flatMap将各个子集合的元素拆分出来映射成一个流.
    * 排序sorted()，对元素进行排序。
    
##### 2.1 中间操作分为有状态和无状态。

    * 有状态操作：当前元素的操作只有拿到所有元素之后才能继续下去。sorted,limit,distinct,skip,peek
        
    * 无状态操作：当前元素的操作不受其他元素的影响。

### 3.终止操作
    
    * 查找,匹配,迭代:
     
| 方法 | 描述 |
| ----| ---- |
| foreach | 遍历流，在并行模式下不一定会按顺序遍历 |
| forEachOrdered | 保证顺序遍历 |
| allMatch | 接收一个 Predicate 函数，当流中每个元素都符合该断言时才返回true，否则返回false |
| noneMatch | 接收一个 Predicate 函数，当流中每个元素都不符合该断言时才返回true，否则返回false |
| anyMatch | 接收一个 Predicate 函数，只要流中有一个元素满足该断言则返回true，否则返回false  |
|  findFirst |返回流中第一个元素|
| findAny | 返回流中的任意元素 |
| count | 返回流中元素个数 |
| max | 返回流中元素最大值 |
| min | 返回流中元素最小值 |

    * 归约与收集:收集主要实现是Collector工厂方法类。所有收集函数都是一种特殊的规约过程，规约的实现方法为reduce
    
    * reduce方法 一般是2个参数，identity初始值和accumulator.apply()累加器函数，初始值会和流中的第一个元素组成apply方法的参数得到的结果
    和下一元素组成apply()方法的参数，依次调用apply()方法，直到最后得到结果。在并行模式下，流中的元素会分配给多个线程执行，这时会产生多个结果
    因此需要第三个参数combiner来合并多个结果。
    
    * 任意的u,  combiner(identity,u)的结果和U是相同的，combiner 必须和accumulator要兼容
    

   
##### 3.1 终止操作分为短路操作和非短路操作。

    * 短路操作：只要遇到符合条件的操作即可返回。
    
    * 非短路操作:必须处理所有元素后才可以返回。
    
