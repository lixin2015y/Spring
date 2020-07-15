### docker 安装kafka

```bash
# kafka 需要zookeeper进行管理需要先下载zookeeper
docker pull wurstmeister/zookeeper

docker run -d --name zookeeper -p 2181:2181 -v /etc/localtime:/etc/localtime wurstmeister/zookeeper


# 查找kafka的镜像
docker search kafka 

# 下载镜像
docker pull wurstmeister/kafka

# 安装
docker run -d --name kafka -p 9092:9092 -e KAFKA_BROKER_ID=0 -e KAFKA_ZOOKEEPER_CONNECT=localhost:2181/kafka -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 -v /etc/localtime:/etc/localtime wurstmeister/kafka


-e KAFKA_BROKER_ID=0  在kafka集群中，每个kafka都有一个BROKER_ID来区分自己
-e KAFKA_ZOOKEEPER_CONNECT=192.168.155.56:2181/kafka 配置zookeeper管理kafka的路径192.168.155.56:2181/kafka
-e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://192.168.155.56:9092  把kafka的地址端口注册给zookeeper
-e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 配置kafka的监听端口
-v /etc/localtime:/etc/localtime 容器同步虚拟机的时间

# 验证kafka是不是安装成功
docker exec -it kafka /bin/sh






```

### linux 安装zookeeper

```sql
---下载
wget http://mirror.bit.edu.cn/apache/zookeeper/zookeeper-3.5.8/apache-zookeeper-3.5.8-bin.tar.gz

--修改配置文件
cp zoo_sample.cfg zoo.cfg
--启动脚本
sh zkServer.sh start
```



### 安装kafka

```sql
---下载
wget http://archive.apache.org/dist/kafka/2.0.0/kafka_2.11-2.0.0.tgz
---启动
sh kafka-server-start.sh ../config/server.properties
---创建topic
sh kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic demo
---查看topic
sh kafka-topics.sh --list --zookeeper localhost:2181
---删除topic
sh kafka-topics.sh --zookeeper localhost:2181 --delete --topic demo

---启动生产者
sh kafka-console-producer.sh --broker-list localhost:9092 --topic demo
---

```

