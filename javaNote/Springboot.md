## 可快速创建Springboot应用
## Springboot的配置
+ 默认使用两种配置文件
   + application.xml
   + application.yml

## 将配置文件的属性进行绑定
```java
@Component //必须是容器的组件才能使用该注解
@ConfigurationProperties(prefix = "user")
public class User {

    private Integer id;

    private String name;

    private String email;

    private List<String> hobby;
}
// yml的配置
user:
  id: 1
  name: lixin
  email: lixin2015y@163.com
  hobby:
    - 篮球
    - 足球
```

