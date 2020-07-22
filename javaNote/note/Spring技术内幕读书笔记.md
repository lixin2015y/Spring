# Spring技术内幕

### 1.BeanFactory的ApplicationContext有什么区别

+ BeanFactory是最近本的容器，它提供了基本的容器方法，如getBean。containsBean()。isSingleton()  isPrototype()等方法
+ 各种ApplicationContex实在基本容器的基础上实现了更加高级的功能的IOC容器，如支持不同的信息源（扩展了MessageSource接口）、

支持应用事件等

### 2.IOC容器的初始化过程

#### 2.1资源定位

> 由ResourceLoader通过统一的Resouce接口来完成各种形式的BeanDefinition的加载
>
> 如类文件路径下的使用ClassPathResource，

#### 2.2载入和解析

> IOC容器使用一个HashMap来维护BeanDefinition，通过不同的BeandefinitionReader来实现Beandefinition的载入
>
> BeanDefinition的载入分成两部分：1、通过XML解析器，得到document对象，2、按照Spring的Bean的规则进行解析

#### 2.3注册

> 将BeanDefinition加入到容器中的BeanDefinitionMap中

### IOC容器容器的依赖注入

