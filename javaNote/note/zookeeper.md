# zookeeper 

### 1.1 核心概念

+ 文件系统
+ 监听通知机制

### 1.2 节点的类型

+ persitent 持久化目录节点

  > 客户端与zk断开连接后，该节点依然存在，只要不手动删除他永远存在

+ persitent_sequential 持久化顺序编号目录节点

  > 客户端与zk断开连接后，该节点依然存在，只是zk给改节点名称进行顺序编号

+ ephemeral 临时目录节点

  > 客户端与zk断开连接后，删除该节点

+ ephemeral_sequential 临时顺序编号目录节点

  > 客户端与zk断开连接后，删除节点，zk会给该节点进行顺序编号

+ container  容器节点  3.5.3版本新增

  > 如果容器节点中没有子节点，则该节点将被zk删除，60s删除一次

+ ttl节点，类似于redis的expire 不准确，是后台线程轮训来删除的，该功能需要使用配置开启，默认关闭状态

### 1.3 事件监听机制

客户端注册监听它关心的任意节点，或者目录及递归目录子节点

+ 对节点监听
  + 节点被删除修改的时候，客户端将通知
+ 对目录监听
  + 当目录有子节点创建，或者有子节点被删除，对应的客户端被通知

> **通知具有一次性，一旦触发监听即被移除**

### 1.4 zookeeper的典型应用场景

+ 分布式配置中心
+ 分布式注册中心
+ 分布式队列
+ 集群选举
+ 分布式屏障
+ 发布订阅

### 1.5 zookeeper节点的状态

> get -s /zookeeper
>
> stat /zookeeper
>
> 两条命令可以查看节点状态

+ cZxid = 0x0

  > 创建node的事务id

+ ctime = Thu Jan 01 08:00:00 CST 1970

  > node的创建时间

+ mZxid = 0x0

  > 最后添加或者删除子节点的事务id

+ mtime = Thu Jan 01 08:00:00 CST 1970

  > 最近修改的时间

+ pZxid = 0x4ed421

  > 最后添加或者删除字节点的事务id，当子节点列表发生变化也会记录

+ cversion = 3

  > node的子节点版本，一个节点的子节点增加和删除都会影响这个版本

+ dataVersion = 0

  > znode的当前数据版本

+ aclVersion = 0

+ ephemeralOwner = 0x0

+ dataLength = 0

+ numChildren = 1

  > 子node的数量

### 1.6 zookeeper的事件类型

+ None：链接建立事件
+ NodeCreated：节点创建
+ NodeDeleted：节点删除
+ NodeDataChanged：节点数据变化
+ NodeChaildrenChange：子节点列表变化
+ DataWatchRemoved：节点监听被移除
+ ChildWatchRemoved：子节点监听被移除

### 1.7 zookeeper内存数据和持久化

针对每一次的事务操作，zookeeper都会将他们记录到事务中

事务日志在集群中的半数以上机器写成功，才返回当前事务写成功

**1.事务日志的内容：**

操作时间，客户端会话ID，CXID,ZXID,操作类型，节点路径，节点数据（用 #+ascii 码表示），节点版本。

zookeeper会频繁进行IO操作，不断追加写磁盘会开辟新的磁盘块，为增加效率zk会采用磁盘预分配，类似于mysql

**2.事务文件的格式**

文件名为  log.<当前最大事务id>，顺序写入，写满创建新的文件，文件名中的事务id也就是文件内容中的最小的事务id

**3.数据快照**

数据快照用于记录Zookeeper服务器上某一时刻的全量数据

可以进行配置snapCount，请求snapCount个事务后生成快照，snapshot.<当时最大事务ID>

>  **为避免集群中所有机器同事进行快照，实际为事务达到[snapCount/2 + 随机数(1-snapCount/2)]个数开始快照**

**4.有了事务日志，为什么还要进行快照？**

恢复数据的时候，可以先恢复快照数据，再通过增量恢复事务日志中的数据 即可

