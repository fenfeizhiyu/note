
### 1、ApplicationContext是核心接口，它为一个应用提供了环境配置。当应用在运行时ApplicationContext是只读的，但你可以在该接口的实现中来支持reload功能。
    * 1.1 提供了一个bean工厂方法来访问应用组件，通过继承org.springframework.beans.factory.ListableBeanFactory来获得的；
    * 1.2 通过通用的方式来加载文件资源的能力，通过继承org.springframework.core.io.ResourceLoader来获得的；
    * 1.3 发布事件到注册的监听器的能力，通过继承ApplicationEventPublisher来获得的；
    * 1.4 解析消息，支持国际化的能力，通过继承MessageSource来获得的；
    * 1.5 context的继承机制。定义在子context将优先级别更高。这意味着，例如：一个父context可以被整个web应用共享，而每个servlet可以有自己的子context，并且这些servlet彼此独立。
