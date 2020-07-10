```java
@Override
	public void refresh() throws BeansException, IllegalStateException {
		synchronized (this.startupShutdownMonitor) {
			// 记录一下容器启动时间，设置一下容器状态，验证${}中的属性是否能找到，初始化事件和时间监听器
			prepareRefresh();

			// 创建BeanFactory(DefaultListableBeanFactory)，
            // 加载Bean的定义信息通过beanDefinition获得bean的配置信息，加到容器中，其实就是泛型Map
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
                //        6)判断是否是单例bean 
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

