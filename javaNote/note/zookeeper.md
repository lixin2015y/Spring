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

### 1.4 ACL 权限控制
