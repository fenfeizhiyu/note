### 1.Core组件定义了资源的访问方式，把所有资源都抽象成一个接口Resouce，用来发现，加载，并处理Bean之间的关系。
### 2.结构:
    * 1.1 annotation :注解解析器和工具类
    * 1.2 codec 各种编码工具
    * 1.3 convert 转码工具
    * 1.4 env spring3.1开始提供的新的属性管理API，提供配置的读取和环境划分能力，
        主要接口:PropertySource,Environment,PropertyResolver，实现类：
        * 1.4.1:MapPropertySoruce:属性来自Map
        * 1.4.2:ResourcePropertySource:属性来自properties文件。
        * 1.4.3:ServletContextPropertySource:属性来自ServletContext上下文初始化参数。
        * 1.4.4:CompositePropertySource:提供了组合PropertySource的功能，查找顺序就是注册顺序。
        * 1.4.5：Environment:环境，本身是一个PropertyResolver,但是提供了Profile特性，既可以根据环境得到相应的数据，(激活不同的profile，可以得到不同的属性数据)
        * 1.4.6：Profile:剖面，Environment使Spring具有了剖面特性，只有激活的剖面组件才会祖册到Spring容器，这是Context包中的一个注解。另外Context包中还有一个叫做EnvironmentAware的类，ApplicatonContext是其子类，因此我们在SpringContext中可以获取所有配置和Profile信息。
   * 1.5：IO
        * 1.5.1:提供一个Resource接口，封装Url资源，File资源，ClassPath资源，服务器相关资源等，统一通过这个接口访问。
            * 1.5.1.1：ByteArrayResource:代表byte[]数组资源，可多次读取，isopen永远返回false.
            * 1.5.1.2:InputStreamResouce:返回字节流，只能读取一次。
            * 1.5.1.3:FileSystemResource:底层文件字节流。
            * 1.5.1.4:ClassPathResource:使用ClassLoader进行加载资源，这些资源存在类路径中或jar中。
            * 1.5.1.6:UrlResource:读取网络资源
            * 1.5.1.7:ServletContextResource:读取web应用资源。
        * 1.5.2:ResourceLoader:加载指定资源。
   * 1.6：Serializer  序列化和反序列化工具。
   * 1.7：style 格式化输出工具类.
   * 1.8:task
        在javaSE 5.0引入ThreadPoolExecutor,ScheduledthreadPoolExecutor后，Spring在其基础上进行扩展，通过IOC配置形式自定义他们的暴雷的各个属性。例如:TaskExecutor,ApplicationEventMulticaster组件，AbstactMessageListenerContainer和对Quartz的整合都使用了TaskExecutor抽象来提供线程池。
   * 1.9:backOff:
       spring封装的退避算法，用于获取重试间隔，FixedBackOff是按固定间隔重试，ExponentialBackOff是按指数时间间隔重试。

   * 1.10 concurrent
        spring中对Future的扩展，支持Future的适配，FutureTask添加多个回调函数。
    * 1.11 工具类:
           * 1.11.1:ClassUtils提供操作class类的方法。
           * 1.11.2:CollectionUtils:集合工具类，提供集合的转换，查找，判空等方法。
           * 1.11.3:DigestUtils:对java的MessageDigest的封装，提供单向加密算法。
           * 1.11.4:ReflectionUtils:反射工具类。
           * 1.11.5:SystemPropertyUtils:placeholder解析工具类。
