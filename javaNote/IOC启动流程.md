```java
@Override
	public void refresh() throws BeansException, IllegalStateException {
		synchronized (this.startupShutdownMonitor) {
			// 记录一下容器启动时间，设置一下容器状态，验证${}中的属性是否能找到，初始化事件和时间监听器
			prepareRefresh();

			// 创建BeanFactory(DefaultListableBeanFactory)，
            // 加载Bean的定义信息通过beanDefinition获得bean的配置信息，加到容器中，其实就是泛型ConcurrentHashMap<String,BeanDefinition> map
			ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

			// 设置 BeanFactory 的类加载器，添加几个 BeanPostProcessor，手动注册几个特殊的 bean
			prepareBeanFactory(beanFactory);

			try {
				// 执行容器初始化后的一些设置，如果子类实现了BeanFactoryPostProcessor，可以在BeanFactory初始化后进行一些额外的设置
				postProcessBeanFactory(beanFactory);

                // public interface BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor
				// 调研bean中实现这两个接口的实现方法，
                // 有顺序  BeanDefinitionRegistryPostProcessor(PriorityOrdered、Ordered、未实现)
                //   BeanFactoryPostProcessor(PriorityOrdered、Ordered、未实现)
				invokeBeanFactoryPostProcessors(beanFactory);

				// 注册所有BeanPostProcessor 
                // 上面中的BeanPostProcessor是以Bean定义信息进来的
                // 实际上就是加入到List<BeanPostProcessor> beanPostProcessors = new CopyOnWriteArrayList<>();
                // 后面的BeanPostProcessor执行是有顺序的，注册的时候遵循（PriorityOrdered、Ordered、未实现）的顺序
				registerBeanPostProcessors(beanFactory);

				// 初始化当前 ApplicationContext 的 MessageSource，国际化这里就不展开说了，不然没完没了了
				initMessageSource();

				// 初始化事件广播器，？？？？？？？？？？？？？？？？？？？？
				initApplicationEventMulticaster();

				// 未实现留给子类刷新容器的时候使用
                // 可以在单例bean初始化之前初始化一些特殊的Bean
				onRefresh();

				// 注册事件监听器，监听器需要实现 ApplicationListener 接口
				registerListeners();

				// 初始化所有单例bean，lazy-init、抽象除外
                // 1、初始化conversionService这个Bean
                // 2、如果没有配置字符解析器，则创建一个字符解析器
                // 3、初始化LoadTimeWeaverAware类型的Bean，AspectJ相关内容
                // 4、让bean的定义信息不能再被修改
                // 5、调用preInstantiateSingletons()
				//    1）拿到所有bean的定义信息,初始化所有的非懒加载的 singleton beans
                //    2）循环每一个类，合并父类的定义信息
                //    3）判断是不是工厂bean，如果是一个简单的工厂bean，当前工厂类本身是不被初始化的（有条件可以初始化）
                //    4）再次循环beannames,如果一个bean实现了SmartInitializingSingleton则在次进行回调
                //    5）调用getBean创建对象，getBean内调用doGetBean创建对象
                //        1)如果是一个工厂Bean则将&符去掉
                //        2)检查下是不是已经创建过了，如果已经创建过，则直接从容器拿到bean对象
                //        3)如果当前bean定义信息是存在的，而且存在负容器，调用父容器的getBean或者doGeatBean方法
                //        4)经过前面的验证开始真正的创建bean
                //        5)先初始化依赖的bean，判断依赖的bean是否又依赖当前bean（循环依赖）
                //        6)判断是否是单例bean 下面是单例bean创建过程
                //             1)确保BeanDefinition中的Class被加载
                //             2)准备方法复写，spring中支持自定义方法替换掉bean的方法的
                //              通过<lookup-method name="createCommand" bean="myCommand"/>
                //             3)让 InstantiationAwareBeanPostProcessor 在这一步有机会返回代				 //             4)调用doCreateBean方法创建bean
                //                 1)如果不是工厂bean，直接实例化，
                //                  创建对象调用createBeanInstance() 实例化bean
                //                 2)解决循环依赖
                //                 3)调用populateBean()
                //                   前面的bean只是实例化，现在为bean初始化（为类属性赋初值）
                //                 4)调用initializeBean() 执行init-method、													 beanPostPorcessor的回调
                finishBeanFactoryInitialization(beanFactory);

				// 
				finishRefresh();
			}

			catch (BeansException ex) {
				if (logger.isWarnEnabled()) {
					logger.warn("Exception encountered during context initialization - " +
							"cancelling refresh attempt: " + ex);
				}

				// Destroy already created singletons to avoid dangling resources.
				destroyBeans();

				// Reset 'active' flag.
				cancelRefresh(ex);

				// Propagate exception to caller.
				throw ex;
			}

			finally {
				// Reset common introspection caches in Spring's core, since we
				// might not ever need metadata for singleton beans anymore...
				resetCommonCaches();
			}
		}
	}
```

```java
/*******************下面是createBeanInstance() 用于创建bean的实例******************/
/*******************
判断是不是共长bean工厂方法，如果是则调用工厂方法创建bean，一般采用构造方法实例化实例，在内部调用instantiate方法，在这个方法内利用反射创建对象，如果存在方法复写，则需要CGLIB来完成该实例化******************/

protected BeanWrapper createBeanInstance(String beanName, RootBeanDefinition mbd, Object[] args) {
   // 确保已经加载了此 class
   Class<?> beanClass = resolveBeanClass(mbd, beanName);

   // 校验一下这个类的访问权限
   if (beanClass != null && !Modifier.isPublic(beanClass.getModifiers()) && !mbd.isNonPublicAccessAllowed()) {
      throw new BeanCreationException(mbd.getResourceDescription(), beanName,
            "Bean class isn't public, and non-public access not allowed: " + beanClass.getName());
   }

   if (mbd.getFactoryMethodName() != null)  {
      // 采用工厂方法实例化，不熟悉这个概念的读者请看附录，注意，不是 FactoryBean
      return instantiateUsingFactoryMethod(beanName, mbd, args);
   }

   // 如果不是第一次创建，比如第二次创建 prototype bean。
   // 这种情况下，我们可以从第一次创建知道，采用无参构造函数，还是构造函数依赖注入 来完成实例化
   boolean resolved = false;
   boolean autowireNecessary = false;
   if (args == null) {
      synchronized (mbd.constructorArgumentLock) {
         if (mbd.resolvedConstructorOrFactoryMethod != null) {
            resolved = true;
            autowireNecessary = mbd.constructorArgumentsResolved;
         }
      }
   }
   if (resolved) {
      if (autowireNecessary) {
         // 构造函数依赖注入
         return autowireConstructor(beanName, mbd, null, null);
      }
      else {
         // 无参构造函数
         return instantiateBean(beanName, mbd);
      }
   }

   // 判断是否采用有参构造函数
   Constructor<?>[] ctors = determineConstructorsFromBeanPostProcessors(beanClass, beanName);
   if (ctors != null ||
         mbd.getResolvedAutowireMode() == RootBeanDefinition.AUTOWIRE_CONSTRUCTOR ||
         mbd.hasConstructorArgumentValues() || !ObjectUtils.isEmpty(args))  {
      // 构造函数依赖注入
      return autowireConstructor(beanName, mbd, ctors, args);
   }

   // 调用无参构造函数
   return instantiateBean(beanName, mbd);
}
```



```java
/***************** bean属性的设置************************/
protected void populateBean(String beanName, RootBeanDefinition mbd, BeanWrapper bw) {
   // bean 实例的所有属性都在这里了
   PropertyValues pvs = mbd.getPropertyValues();

   if (bw == null) {
      if (!pvs.isEmpty()) {
         throw new BeanCreationException(
               mbd.getResourceDescription(), beanName, "Cannot apply property values to null instance");
      }
      else {
         // Skip property population phase for null instance.
         return;
      }
   }

   // 到这步的时候，bean 实例化完成（通过工厂方法或构造方法），但是还没开始属性设值，
   // InstantiationAwareBeanPostProcessor 的实现类可以在这里对 bean 进行状态修改，
   // 我也没找到有实际的使用，所以我们暂且忽略这块吧
   boolean continueWithPropertyPopulation = true;
   if (!mbd.isSynthetic() && hasInstantiationAwareBeanPostProcessors()) {
      for (BeanPostProcessor bp : getBeanPostProcessors()) {
         if (bp instanceof InstantiationAwareBeanPostProcessor) {
            InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor) bp;
            // 如果返回 false，代表不需要进行后续的属性设值，也不需要再经过其他的 BeanPostProcessor 的处理
            if (!ibp.postProcessAfterInstantiation(bw.getWrappedInstance(), beanName)) {
               continueWithPropertyPopulation = false;
               break;
            }
         }
      }
   }

   if (!continueWithPropertyPopulation) {
      return;
   }

   if (mbd.getResolvedAutowireMode() == RootBeanDefinition.AUTOWIRE_BY_NAME ||
         mbd.getResolvedAutowireMode() == RootBeanDefinition.AUTOWIRE_BY_TYPE) {
      MutablePropertyValues newPvs = new MutablePropertyValues(pvs);

      // 通过名字找到所有属性值，如果是 bean 依赖，先初始化依赖的 bean。记录依赖关系
      if (mbd.getResolvedAutowireMode() == RootBeanDefinition.AUTOWIRE_BY_NAME) {
         autowireByName(beanName, mbd, bw, newPvs);
      }

      // 通过类型装配。复杂一些
      if (mbd.getResolvedAutowireMode() == RootBeanDefinition.AUTOWIRE_BY_TYPE) {
         autowireByType(beanName, mbd, bw, newPvs);
      }

      pvs = newPvs;
   }

   boolean hasInstAwareBpps = hasInstantiationAwareBeanPostProcessors();
   boolean needsDepCheck = (mbd.getDependencyCheck() != RootBeanDefinition.DEPENDENCY_CHECK_NONE);

   if (hasInstAwareBpps || needsDepCheck) {
      PropertyDescriptor[] filteredPds = filterPropertyDescriptorsForDependencyCheck(bw, mbd.allowCaching);
      if (hasInstAwareBpps) {
         for (BeanPostProcessor bp : getBeanPostProcessors()) {
            if (bp instanceof InstantiationAwareBeanPostProcessor) {
               InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor) bp;
               // 这里有个非常有用的 BeanPostProcessor 进到这里: AutowiredAnnotationBeanPostProcessor
               // 对采用 @Autowired、@Value 注解的依赖进行设值，这里的内容也是非常丰富的，不过本文不会展开说了，感兴趣的读者请自行研究
               pvs = ibp.postProcessPropertyValues(pvs, filteredPds, bw.getWrappedInstance(), beanName);
               if (pvs == null) {
                  return;
               }
            }
         }
      }
      if (needsDepCheck) {
         checkDependencies(beanName, mbd, filteredPds, pvs);
      }
   }
   // 设置 bean 实例的属性值
   applyPropertyValues(beanName, mbd, bw, pvs);
}
```

```java
/**********************回调各种方法***********************/
protected Object initializeBean(final String beanName, final Object bean, RootBeanDefinition mbd) {
   if (System.getSecurityManager() != null) {
      AccessController.doPrivileged(new PrivilegedAction<Object>() {
         @Override
         public Object run() {
            invokeAwareMethods(beanName, bean);
            return null;
         }
      }, getAccessControlContext());
   }
   else {
      // 如果 bean 实现了 BeanNameAware、BeanClassLoaderAware 或 BeanFactoryAware 接口，回调
      invokeAwareMethods(beanName, bean);
   }

   Object wrappedBean = bean;
   if (mbd == null || !mbd.isSynthetic()) {
      // BeanPostProcessor 的 postProcessBeforeInitialization 回调
      wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
   }

   try {
      // 处理 bean 中定义的 init-method，
      // 或者如果 bean 实现了 InitializingBean 接口，调用 afterPropertiesSet() 方法
      invokeInitMethods(beanName, wrappedBean, mbd);
   }
   catch (Throwable ex) {
      throw new BeanCreationException(
            (mbd != null ? mbd.getResourceDescription() : null),
            beanName, "Invocation of init method failed", ex);
   }

   if (mbd == null || !mbd.isSynthetic()) {
      // BeanPostProcessor 的 postProcessAfterInitialization 回调
      wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
   }
   return wrappedBean;
}
```





### 1.prepareRefresh();

> ```java
> protected void prepareRefresh() {
> 		// 记录开始时间
> 		this.startupDate = System.currentTimeMillis();
> 		this.closed.set(false);
> 		this.active.set(true);
> 
> 		// 初始化带占位符的属性，默认的AnnotationConfigApplicationContext没有对此进行实现
> 		initPropertySources();
> 
> 		// Validate that all properties marked as required are resolvable
> 		getEnvironment().validateRequiredProperties();
> 
> 		// 初始化一个早期事件处理器
> 		this.earlyApplicationEvents = new LinkedHashSet<>();
> 	}
> ```

### 2.obtainFreshBeanFactory();

?????????????????????????????????????刷新bean工厂是嘛???????????????????????????????????????????????????????????

```java
/*********************refreshBeanFactory();***********************/
protected final void refreshBeanFactory() throws IllegalStateException {
		if (!this.refreshed.compareAndSet(false, true)) {
			throw new IllegalStateException(
					"GenericApplicationContext does not support multiple refresh attempts: just call 'refresh' once");
		}
		this.beanFactory.setSerializationId(getId());
	}
```

````java
/*********************return getBeanFactory();***********************/
public final ConfigurableListableBeanFactory getBeanFactory() {
	// 此处可以直接返回，BeanFactory在创建ApplicationContext的构造方法的时候调用的工厂的构造方法已经创建
    return this.beanFactory;
	}
````



### 3.