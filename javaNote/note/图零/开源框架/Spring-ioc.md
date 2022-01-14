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



## 二、解决循环依赖问题

### 2.1 整体梳理

```java
protected <T> T doGetBean(final String name, @Nullable final Class<T> requiredType,
      @Nullable final Object[] args, boolean typeCheckOnly) throws BeansException {

   final String beanName = transformedBeanName(name);
   Object bean;

   // 调用doGetBean这里可以先从一级缓存中拿，每次调用getBean都会先从单例池中获取
   Object sharedInstance = getSingleton(beanName);
   if (sharedInstance != null && args == null) {
      bean = getObjectForBeanInstance(sharedInstance, name, beanName, null);
   } else {
       // 一级缓存中没有，说明是还没有创建
	  	sharedInstance = getSingleton(beanName, () -> {
            try {
                return createBean(beanName, mbd, args);
            }
            catch (BeansException ex) {
                destroySingleton(beanName);
                throw ex;
            }
        });
       bean = getObjectForBeanInstance(sharedInstance, name, beanName, mbd);
   } 
   return (T) bean;
}
```



```java
public Object getSingleton(String beanName, ObjectFactory<?> singletonFactory) {
   Assert.notNull(beanName, "Bean name must not be null");
    // 在这里加锁，防止多线程创建和访问bean
   synchronized (this.singletonObjects) {
      // 拿到锁后再进行判断一下，
      Object singletonObject = this.singletonObjects.get(beanName);
      if (singletonObject == null) {
         // 开始创建，将bean标记为创建中，singletonsCurrentlyInCreation这个类里面
         beforeSingletonCreation(beanName);
         boolean newSingleton = false;
         boolean recordSuppressedExceptions = (this.suppressedExceptions == null);
         if (recordSuppressedExceptions) {
            this.suppressedExceptions = new LinkedHashSet<>();
         }
         try {
            singletonObject = singletonFactory.getObject();
            newSingleton = true;
         }
         catch (IllegalStateException ex) {
            // Has the singleton object implicitly appeared in the meantime ->
            // if yes, proceed with it since the exception indicates that state.
            singletonObject = this.singletonObjects.get(beanName);
            if (singletonObject == null) {
               throw ex;
            }
         }
         catch (BeanCreationException ex) {
            if (recordSuppressedExceptions) {
               for (Exception suppressedException : this.suppressedExceptions) {
                  ex.addRelatedCause(suppressedException);
               }
            }
            throw ex;
         }
         finally {
            if (recordSuppressedExceptions) {
               this.suppressedExceptions = null;
            }
            afterSingletonCreation(beanName);
         }
         if (newSingleton) {
            addSingleton(beanName, singletonObject);
         }
      }
      return singletonObject;
   }
```

```java
public class DefaultSingletonBeanRegistry extends SimpleAliasRegistry implements SingletonBeanRegistry {

  


   /** Cache of singleton objects: bean name to bean instance. */
   private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

   /** Cache of singleton factories: bean name to ObjectFactory. */
   private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);

   /** Cache of early singleton objects: bean name to bean instance. */
   private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

   /** Set of registered singletons, containing the bean names in registration order. */
   private final Set<String> registeredSingletons = new LinkedHashSet<>(256);

   /** Names of beans that are currently in creation. */
   private final Set<String> singletonsCurrentlyInCreation =
         Collections.newSetFromMap(new ConcurrentHashMap<>(16));

   /** Names of beans currently excluded from in creation checks. */
   private final Set<String> inCreationCheckExclusions =
         Collections.newSetFromMap(new ConcurrentHashMap<>(16));

   /** Collection of suppressed Exceptions, available for associating related causes. */
   @Nullable
   private Set<Exception> suppressedExceptions;

   /** Flag that indicates whether we're currently within destroySingletons. */
   private boolean singletonsCurrentlyInDestruction = false;

   /** Disposable bean instances: bean name to disposable instance. */
   private final Map<String, Object> disposableBeans = new LinkedHashMap<>();

   /** Map between containing bean names: bean name to Set of bean names that the bean contains. */
   private final Map<String, Set<String>> containedBeanMap = new ConcurrentHashMap<>(16);

   /** Map between dependent bean names: bean name to Set of dependent bean names. */
   private final Map<String, Set<String>> dependentBeanMap = new ConcurrentHashMap<>(64);

   /** Map between depending bean names: bean name to Set of bean names for the bean's dependencies. */
   private final Map<String, Set<String>> dependenciesForBeanMap = new ConcurrentHashMap<>(64);
```

## 三、beanPostProcessor执行时机

### 3.1 在创建bean之前调用，调用doCreateBean之前

```
try {
   // Give BeanPostProcessors a chance to return a proxy instead of the target bean instance.
   Object bean = resolveBeforeInstantiation(beanName, mbdToUse);
   if (bean != null) {
      return bean;
   }
}

try {
   Object beanInstance = doCreateBean(beanName, mbdToUse, args);
   if (logger.isTraceEnabled()) {
      logger.trace("Finished creating instance of bean '" + beanName + "'");
   }
   return beanInstance;
}



if (bp instanceof InstantiationAwareBeanPostProcessor) {
				InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor) bp;
				Object result = ibp.postProcessBeforeInstantiation(beanClass, beanName);
				if (result != null) {
					return result;
				}
}

```

### 3.2 实例化之后，允许beanPostProcessor修改bean定义

```java
// Allow post-processors to modify the merged bean definition.
synchronized (mbd.postProcessingLock) {
   if (!mbd.postProcessed) {
      try {
         applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName);
      }
      catch (Throwable ex) {
         throw new BeanCreationException(mbd.getResourceDescription(), beanName,
               "Post-processing of merged bean definition failed", ex);
      }
      mbd.postProcessed = true;
   }
}

protected void applyMergedBeanDefinitionPostProcessors(RootBeanDefinition mbd, Class<?> beanType, String beanName) {
		for (BeanPostProcessor bp : getBeanPostProcessors()) {
			if (bp instanceof MergedBeanDefinitionPostProcessor) {
				MergedBeanDefinitionPostProcessor bdp = (MergedBeanDefinitionPostProcessor) bp;
				bdp.postProcessMergedBeanDefinition(mbd, beanType, beanName);
			}
		}
	}
```

