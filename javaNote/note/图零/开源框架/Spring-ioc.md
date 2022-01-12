## IOC

## 一、总体的构造方法

```java
public AnnotationConfigApplicationContext(Class<?>... componentClasses) {
   this();
   register(componentClasses);
   refresh();
}
```

### 1.1 this()

> + 获取一个beanFactory（DefaultListableBeanFactory）
> + 获取bean定义读取器，注册了很多创世纪类，比较重要的是ConfigurationClassPostProcesser

```java
// 先调用父类构造方法，这里创建了一个beanFactory
public GenericApplicationContext() {
		this.beanFactory = new DefaultListableBeanFactory();
}
// 子类构造方法，注册了beanDefinitionReader
public AnnotationConfigApplicationContext() {
    // 读取配置类用的beanDefinitionReader，在此创建过程中还注册了很多创世纪的类的bean定义
   this.reader = new AnnotatedBeanDefinitionReader(this);
    // 扫描器，用处扫描包，这个是用于手动扫描包使用的
   this.scanner = new ClassPathBeanDefinitionScanner(this);
}
```

#### 1.1.1 注册一些创世纪的bean定义

```java
public static Set<BeanDefinitionHolder> registerAnnotationConfigProcessors(
      BeanDefinitionRegistry registry, @Nullable Object source) {

    
    //注册ConfigurationClassPostProcessor
    	// 为配置类创建动态代理，解析@Import @ComponentScan等
    //注册AutowiredAnnotationBeanPostProcessor
   	//注册CommonAnnotationBeanPostProcessor
    
    //注册EventListenerMethodProcessor，用于处理事件@EventListener
    //注册DefaultEventListenerFactory,用于处理事件@EventListerner
   return beanDefs;
}
```

### 1.2 register(componentClasses);

> 把配置类注册到bean定义中，加到beanDefinitionMap中

### 1.3 refresh()重点方法

```java
synchronized (this.startupShutdownMonitor) {
   // 予刷新，与主流程关系不大，保存容器启动时间，启动标志
   prepareRefresh();

   // 获取到一个bean工厂，DefaultListableBeanFactory XML模式下会在这里读取BeanDefinition
   ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

    //还是一些准备工作，添加了两个后置处理器：
    // ApplicationContextAwareProcessor，ApplicationListenerDetector
	// 还设置了 忽略自动装配 和 允许自动装配 的接口,如果不存在某个bean的时候，spring就自动注册singleton bean
	//还设置了bean表达式解析器 等
   prepareBeanFactory(beanFactory);

   try {
      // 此方法一般是调用给子类实现的
      postProcessBeanFactory(beanFactory);

      // 调用beanFactory的后置处理器，AnnotationConfigApplicationContext在这里面扫描并注册bean定义信息
      invokeBeanFactoryPostProcessors(beanFactory);

      // 注册bean的后置处理器，因为在上一步已经将所有的bean定义注册进来
      // 在这个时候我们可以拿到所有的BeanPostProcessor
      registerBeanPostProcessors(beanFactory);

      // 初始化MessageSource
      initMessageSource();

      // 初始化时间多播器
      initApplicationEventMulticaster();

      // 给子类调用的初始化其他特殊的bean
      onRefresh();

      // 注册监听器
      registerListeners();

      // 初始化剩下的Bean，在这之前容器其实是一直在创建bean的，
       // 还有没有创建的并且是需要创建的，可以创建出来
      finishBeanFactoryInitialization(beanFactory);

      // Last step: publish corresponding event.
      finishRefresh();
   }
   catch (BeansException ex) {
      // Destroy already created singletons to avoid dangling resources.
      destroyBeans();
      // Reset 'active' flag.
      cancelRefresh(ex);
      throw ex;
   }

   finally {
      // Reset common introspection caches in Spring's core, since we
      // might not ever need metadata for singleton beans anymore...
      resetCommonCaches();
   }
}
```