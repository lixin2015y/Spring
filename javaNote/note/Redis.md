# Redis
### Redis的安装
```bash
# 解压
# 安装yum install gcc-c++
# 编译 make
# 安装 make install
# 默认安装在/usr/local/bin目录下
# 拷贝配置文件 cp /opt/module/redis-5.0.7/redis.conf ./
# 默认不是后台启动，将配置文件中的daemonize修改为 yes 
# 使用指定的配置文件启动 redis-server redis.conf
# 本地连接 redis-cli
# 关闭链接 redis-cli shutdown

# 修改可以远程链接
vim redis.conf
# 修改配置文件redis.conf配置文件：（注释掉bind:127.0.0.1）和修改保护模式为no
protected-mode no
```
### Redis默认的性能测试工具
+ redis-benchmark 可以使用这个工具进行性能测试

### Redis基础知识
+ 默认16个数据库，默认使用第0个，可以使用select切换数据库
+ 清空全部数据 flushall，清除db数据flushdb
### 为什么单线程这么快
+ 将数据全部放到内存中，单线程避免了多线程导致的CPU上下文切换
### 为什么单线程而不是多线程

+ 短板效应性能瓶颈在网络和IO中，不需要多线程，除非网络和IO优化到单核CPU性能不够
+ 单线程是处理网络请求的时候，如果开启多线程是可以进行多线程IO的
+ 使用IO多路复用：
  + 使用一个IO监听多个连接，那个连接发消息，就处理那个连接
  + 相比于多线程，没有多线程线程状态的切换，CPU性能上的消耗

### 常用命令

```mysql based
exists name # 判断一个key是否存在
expire name 10 # 设置key十秒过期
move name 1 # 删除当前key
ttl name # 设置过期时间后可以查看key的剩余时间
type name # 查看当前key是什么类型的
```
### String（存储对象，分布式锁）
```bash
append key1 vvvv # 追加字符串
strlen key1 # 截取字符串长度
incr k1 # 可以将数字+1 非数字不变
decr k1 # 将数字-1
incrby k1 10 # 加10
decrby k1 10 # 减10
getrange k1 2 3 # 字符串截取
setrange k1 0 h # 字符串替换
setnx k k  # 不存在当前key才会去创建，成功返回1失败返回0可以用作分布式锁
setex k 10 v # 创建key并设置过期时间
mset k1 v1 k2 v2 # 批量设置key 也可以设置过期时间或者不存在才创建（批量操作是原子性操作）
mget k1 k2 # 批量获取

#设置对象
mset user:1:name lixin user:1:age 24
# 获取对象
mget user:1:name user:1:age
# 获取原来的值 替换新的值
getset k1 bbb 

```

### List（队列，栈）

```bash
lpush k v1 v2 v3 # 左插入
rpush k v4  # 右插入
lpop k # 左移除
rpop k # 右移除
lindex k 0 # 查看索引未知的元素
ltrim list 1 2 # 通过下标截取指定的长度，原来的list会被修改
rpoplpush list newlist # 移除最后一个元素并且添加到新的列表中
lset list 0  # 修改列表元素
linsert list before l2 llllll # 在列表的一个元素前面添加元素
```



### Set（共同关注，好友推荐）

```bash
sadd myset "hello"  # 添加元素
smembers myset  # 显示所有元素
sismember myset hello # 判断是不是set的元素
scard myset  # 获取所有元素
srem myset hello  # 移除所有元素
srandmember myset # 从集合中取出一个随机的元素
spop myset # 随机移除一个元素

# 交并差
sdiff set1 set2 # 差集
sinter set1 set2 # 交集
sunion set1 set2 # 并集
```



### Hash

本质上和string没有什么区别，K-Map

```bash
127.0.0.1:6379> hset h1 k1 v1  # 设置元素
(integer) 1
127.0.0.1:6379> hget h1 k1  # 获取元素
"v1"
127.0.0.1:6379> hgetall h1 # 获取所有元素
1) "k1"
2) "v1"
3) "k2"
4) "h2"
127.0.0.1:6379> hdel h1 k1  # 删除字段
(integer) 1
127.0.0.1:6379>  hsetnx h1 k3 v3  # 字段不存在则设置
```

### Zset（成绩表，工资表排序、排行榜）

```bash
127.0.0.1:6379> zadd z1 1 one  # 指定元素在set中的顺序
(integer) 1
127.0.0.1:6379> zadd z1 2 two
(integer) 1
127.0.0.1:6379> zadd z1 3 three
127.0.0.1:6379> zrange z1 0 -1
1) "one"
2) "two"
3) "three"


zrangebyscore z1 -inf +inf # 在这个区间进行排序（最小值，最大值）
zrevrange z1 0 -1  # 从大到小排序
zrangebyscore z1 -inf 3  # 判断3以下的值

127.0.0.1:6379> zcard z1 # 查看有几个元素
(integer) 4

127.0.0.1:6379> zcount z1 3 4  # 查看区间的元素数量



```



## 特殊数据类型

### gepspatial做地理位置查询距离计算(附近的人)

底层基于zset 可以使用zset来删除地理位置信息

```bash
127.0.0.1:6379> geoadd china:city 116.40 39.90 beijing # 添加城市的经纬度
(integer) 1
127.0.0.1:6379> geoadd china:city 121.47 31.23 shanghai
(integer) 1
127.0.0.1:6379> geoadd china:city 106.50 29.53 chongqing
(integer) 1

127.0.0.1:6379> geopos china:city chongqing # 显示经纬度
1) 1) "106.49999767541885376"
   2) "29.52999957900659211"

127.0.0.1:6379> geodist china:city beijing  shanghai # 计算距离
"1067378.7564" 

127.0.0.1:6379> georadius china:city 110 30 400 km # 以一个经纬度半径搜索元素
1) "chongqing" 
```



#### hyperloglog 做基数统计，可以用作网站的UV统计

```bash
127.0.0.1:6379>  pfadd pf1 a b c d # 添加元素
(integer) 1
127.0.0.1:6379>  pfadd pf2 c d e f
(integer) 1
127.0.0.1:6379> pfcount pf1  # 统计基数
(integer) 4
127.0.0.1:6379> pfmerge pf1 pf2 # 合并两个key的元素
OK
127.0.0.1:6379> pfmerge pf3 pf1 pf2
OK
127.0.0.1:6379> pfcount pf3
(integer) 6
127.0.0.1:6379> 

```

#### bitmaps

用户登录，365天打卡，统计打卡天数， 两个状态切换的都可以使用bigmaps统计

```bash
127.0.0.1:6379>  setbit sb 3 1 # 设置位
127.0.0.1:6379> getbit sb 0  # 获取位
127.0.0.1:6379> bitcount sb # 统计1的数量
(integer) 3
```





## 事务

+ 一组命令的集合，一起按照加入队列的顺序执行，不允许被别人打断

+ redis事务没有隔离级别的概念

+ 执行流程

  ```bash
  # 开启事务
  127.0.0.1:6379> 
  127.0.0.1:6379> multi
  OK
  # 命令加入事务队列
  127.0.0.1:6379> set l1 v1 
  QUEUED
  127.0.0.1:6379> set l2 v2
  QUEUED
  127.0.0.1:6379> set l3 v3
  QUEUED
  127.0.0.1:6379> set l2 v4
  QUEUED
  # 执行事务
  127.0.0.1:6379> exec
  ```

+ 编译期的错误会导致事务所有命令都不执行（命令写的有问题）

+ 运行期的错误会导致单条语句不执行，不影响其他语句（0做除数、给一个string自增）

## 乐观锁（秒杀系统）

#### 悲观锁

+ 做什么都加锁

#### 乐观锁

+ 获取version，更新的时候比较version

```bash
# Redis的乐观锁
127.0.0.1:6379> watch a # 开启对a的一个监视
OK
127.0.0.1:6379> multi # 开启一个事务
OK
127.0.0.1:6379> set a 10 # 设置值
QUEUED
127.0.0.1:6379> exec  # 执行事务
(nil)
127.0.0.1:6379>  # 当被监视的key被修改后，事务执行会不成功

unwatch a # 如果事务执行失败，先解锁，再次监视拿到最新的值

```



## springboot整合redis

+ 默认序列化方式是jdk的序列化
+ 未序列化的对象是无法存入到redis中的

### 实现自己的redisTemplate

```java
@Bean
    RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // 设置序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        return template;
    }
```



## Redis持久化

### RDB

#### rdb触发的条件

+ 配置文件设置的save条件满足
+ 使用shutdown命令
+ 执行save或者bgsave
  + save在主线程上执行会阻塞所有，无法接受新的请求
  + bgsave会fork一个子线程，可以接受新的请求
+ 执行flushall命令，会触发，但是数据库中没有数据，所以无意义

#### 优点：

+ 适合大规模的数据恢复
+ 对数据的完整性要求不高

#### 缺点：

+ 需要进行一定的时间间隔，可能会丢失数据
+ 开启一个紫禁城的时候会占用内存

### AOF

默认不开启，需要手动进行设置，将配置文件的appendonly改为yes 就开启了aof

记录更新操作，每隔一段时间一同步，

当日志文件过大的时候可以进行日志重写

#### 优点：

+ 每次修改都同步，数据完整性会更好
+ 每秒同步，也可能会丢失一秒的数据

#### 缺点：

+ aop日志比较大，数据恢复比较慢



## 发布订阅（站内广播）

+ subscribe lixin  订阅频道
+ publish lixin aaaa 发布消息

## 主从复制



### 主从复制的优点

+ 热备份数据，是持久化之外的一个数据冗余方式
+ 高可用，主节点宕机，从节点可以快速使用
+ 读写分离

```bash
127.0.0.1:6379> info replication # 查看自己的设置
# Replication
role:master
connected_slaves:0
master_replid:a9d44a85843242a040e2015a8cd0830aa33b9c8d
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:0
second_repl_offset:-1
repl_backlog_active:0
repl_backlog_size:1048576
repl_backlog_first_byte_offset:0
repl_backlog_histlen:0

```

复制配置文件，修改端口号，修改日志文件名字 pid进程文件





redis默认每台机器都是主节点，

salveof localhost 6379 配置当前服务的主节点

主机可以写，从机不能写

从节点连接主节点可以会触发一次主节点的bgsave，进行一次全量复制，后面会进行增量复制同步数据

