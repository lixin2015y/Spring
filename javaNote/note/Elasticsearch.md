# Electicsearch

### 1.简介ES

#### 1.1ES和solr的区别

+ Solr是基于Zookeeper进行的分布式管理，ES有自己独立的分布式协调工具
+ Solr支持更多的格式JSON、XML、CSV，ES只支持JSON格式
+ Solr插入删除慢，ES可以做到快速的实时查询

#### 1.2

### 2.安装

#### 2.1安装ES

```bash
## 解压完成后需要给非root用户问价目录权限，es默认拒绝root用户启动
chown -R es:es /usr/local/elasticsearch/

## 问题1
[1]: max file descriptors [4096] for elasticsearch process is too low, increase to at least [65535]
# 修改问价 /etc/security/limits.conf追加
* soft nofile 65536
* hard nofile 65536

# 问题2
[2]: max virtual memory areas vm.max_map_count [65530] is too low, increase to at 
least [262144]

# 在/etc/sysctl.conf文件最后添加一行
vm.max_map_count=262144
# 再执行命令
sudo sysctl -p

# 访问http://192.168.2.101:9200/即可发现基本信息

```

#### 2.2安装Kibana

(基于nodejs的一个前端可是话界面，可以通过命令行启动)

```bash
# 解压后执行脚本启动

# 修改
sudo vim kibana.yml
# 修改serve.host 为0.0.0.0
# 汉化kibana
i18n.locale: "zh-CN"

```

#### 2.3安装ik分词器

```bash
# 将ik分词器解压到
# /opt/module/elasticsearch/elasticsearch-6.8.6/plugins/ik

#重启

# 测试ik分词 查看当前ES集群的插件
sh elasticsearch-plugin list 
```

```bash
# 最少切分分词
GET _analyze 
{
  "analyzer": "ik_smart",
  "text": "中国共产党"

}

## 最细粒度分词
GET _analyze 
{
  "analyzer": "ik_max_word",
  "text": "中国共产党"
}
```

分词原理是通过字典（dic）文件来实现的，定义了基本的结束标识开始表示，我们也可以加入自己的字典

### 3. ES的基本操作

#### 3.1创建索引

+ 建立索引并且添加数据

```http
PUT /索引名/类型名/id 

PUT /test/type/1
{
  "name": "李新",
  "age": 3
}
```



+ 建立索引并设置映射

```http
PUT /tset2?include_type_name=false
{
  "mappings": {
    "properties": {
      "name": {
        "type": "text"
      },
      "age": {
        "type": "long"
      },
      "birthday": {
        "type": "date"
      }
    }
  }
}

```

+ 查看索引的结构(如果未指定文档类型，则会使用默认的_doc)

```http
GET /tset2
```

+ 修改

```http
PUT /test/type/1
{
  "name": "李新update",
  "age": 3
}

{
  "_index" : "test",
  "_type" : "type",
  "_id" : "1",
  "_version" : 2,  # version会加1
  "result" : "updated", # 变成了updated
  "_shards" : {
    "total" : 2,
    "successful" : 1,
    "failed" : 0
  },
  "_seq_no" : 1,
  "_primary_term" : 3
}
```



+ 特殊命令

```http
GET /_cat/indices?v  # 查看索引、

```

#### 3.2 更新数据

+ 更新部分字段

```http
POST /shopping/_doc/1 HTTP/1.1
Host: 172.16.2.56:9200
Content-Type: application/json
Content-Length: 41

{
    "name": "lixin",
    "age": 12
}
```

+ 更新全量数据



#### 3.3 复杂查询

```http
172.16.2.56:9200/shopping/_search?q=name:lixin



GET /shopping/_search HTTP/1.1
Host: 172.16.2.56:9200
Content-Type: application/json
Content-Length: 85
{
    "query":{
        "match":{
            "name": "lixin"
        }
    }
}


#### 全量查询（分页）
GET /shopping/_search HTTP/1.1
Host: 172.16.2.56:9200
Content-Type: application/json
Content-Length: 108

{
    "query": {
        "match_all": {
            
        }
    },
    
    // 分页
    "from": 0,
    "size": 2,
    
    ## 指定查询显示字段
    "_source": ["name"],
    
    // 按照age排序
    "sort": {
        "age": {
            "order": "desc"
        }
    }
}




{
    "query": {
        "bool": {
        // must 表示and
            "must": [
                {
                    "match": {
                        "name": "lixin"
                    }
                },
                {
                    "match": {
                        "age": 123
                    }
                }
            ],
            
            // should 表示or
            "should": [
                {
                    "match": {
                        "name": "lixin"
                    }
                },
                {
                    "match": {
                        "age": 123
                    }
                }
            ],
            
            // 范围查询
            "filter": {
                "range": {
                    "age": {
                        "gt": 5000
                    }
                }
            }
        }
    },
    // 对name进行高亮
    "highlight": {
    	"fields": {
    		"name": {}
    	}
    }
}


// 分组统计
{
    "aggs": {
        "name_group": {
            "terms":{
                "field": "age"
            }
        }
    }
}

// 平均
{
    "aggs": {
        "name_avg": {
            "avf":{
                "field": "age"
            }
        }
    }
}
```





#### 3.4 创建索引并指定mapping

```json
{
    "properties": {
        // type为text会进行分词，建立倒排索引
        "name": {
            "type": "text",
            "index": true
        },
        "age": {
            "type":"keyword",
            "index": true
        },
        // keyword必须精确匹配，不进行分词
        "birthday": {
            "type": "keyword",
            // index为false，不被索引，不支持查询
            "index": false
        }
    }
}
```



### 4. es集群内架构

#### 4.1 集群配置，

把不同的节点集群名称配置成相同的，就组成一个集群，后启动的节点配置发现

http://192.168.2.100:9200/_cat/nodes  查看集群节点状态

```properties
cluster.name: es-cluster
# 节点名称
node.name: hadoop101
# ip地址
network.host: hadoop101

node.master: true
node.data: true
http.port: 9200

http.cors.allow-origin: "*"
http.cors.enabled: true
http.max_content_length: 200mb

# 第一次启动指定master节点
cluster.initial_master_nodes: ["hadoop100"]

# 节点发现
discovery.seed_hosts: ["hadoop100:9300", "hadoop101:9300", "hadoop102:9300"]
gateway.recover_after_nodes: 2
network.tcp.keep_alive: true
network.tcp.no_delay: true
transport.tcp.compress: true
cluster.routing.allocation.cluster_concurrent_rebalance: 16
cluster.routing.allocation.node_concurrent_recoveries: 16
cluster.routing.allocation.node_initial_primaries_recoveries: 16

```



#### 4.2 索引（Index）

一个文档是一个可被索引的基础信息单元，也就是一条数据 比如：你可以拥有某一个客户的文档，某一个产品的一个文档，当然，也可以拥有某个 订单的一个文档。文档以 JSON（Javascript Object Notation）格式来表示，而 JSON 是一个 到处存在的互联网数据交互格式。 在一个 index/type 里面，你可以存储任意多的文档。

#### 4.3 type

在一个索引中，你可以定义一种或多种类型。 一个类型是你的索引的一个逻辑上的分类/分区，其语义完全由你来定。通常，会为具 有一组共同字段的文档定义一个类型。不同的版本，类型发生了不同的变化

#### 4.4 文档

在一个索引中，你可以定义一种或多种类型。 一个类型是你的索引的一个逻辑上的分类/分区，其语义完全由你来定。通常，会为具 有一组共同字段的文档定义一个类型。不同的版本，类型发生了不同的变化

#### 4.5 分片

一个索引可以存储超出单个节点硬件限制的大量数据。比如，一个具有 10 亿文档数据 的索引占据 1TB 的磁盘空间，而任一节点都可能没有这样大的磁盘空间。或者单个节点处 理搜索请求，响应太慢。为了解决这个问题，Elasticsearch 提供了将索引划分成多份的能力， 每一份就称之为分片。当你创建一个索引的时候，你可以指定你想要的分片的数量。每个分 片本身也是一个功能完善并且独立的“索引”，这个“索引”可以被放置到集群中的任何节点 上。

#### 4.6 副本

在一个网络 / 云的环境里，失败随时都可能发生，在某个分片/节点不知怎么的就处于 离线状态，或者由于任何原因消失了，这种情况下，有一个故障转移机制是非常有用并且是 强烈推荐的。为此目的，Elasticsearch 允许你创建分片的一份或多份拷贝，这些拷贝叫做复 制分片(副本)。

**默认情况下，Elasticsearch 中的每个索引被分片 1 个主分片和 1 个复制**

#### 4.7 路由的计算

+ 存数据

计算公式 ： id(hash) % 主分片数量，   
主分片数量是创建索引的时候就会制定的，无法修改

+ 取数据，使用分片控制

访问任何一个节点即可，

+ 新建、索引和删除 请求都是 写 操作， 必须在主分片上面完成之后才能被复制到相关 的副本分片



#### 4.8 写数据过程



consistency参数

设置成one：表示只要主分片活跃就可以进行写入操作

设置成all：必须所有的副本本片活跃才可以进行写入操作

设置成quorum：表示超过半数活跃才可已进行写入

**quorum计算方式（（主分片+副本数量）/ 2）+ 1**

timeout参数

如果没有足够的副本写入，则会进入等待，



#### 4.9 读数据过程

1. 客户端向 Node 1 发送获取请求。 
2. 节点使用文档的 _id 来确定文档属于分片 0 。分片 0 的副本分片存在于所有的三个 节点上。 在这种情况下，它将请求转发到 Node 2 。 
3. Node 2 将文档返回给 Node 1 ，然后将文档返回给客户端。 在处理读取请求时，协调结点在每次请求的时候都会通过轮询所有的副本分片来达到负载均 衡。在文档被检索时，已经被索引的文档可能已经存在于主分片上但是还没有复制到副本分 片。 在这种情况下，副本分片可能会报告文档不存在，但是主分片可能成功返回文档。 一 旦索引请求成功返回给用户，文档在主分片和副本分片都是可用的。

#### 4.10 为什么要进行分片

索引数据太多，使用lucence进行搜索效率会下降

#### 4.11 分词器

ik_max_word 最细粒度

ik_smart 最粗粒度

#### 4.12 倒排索引

+ 词条：索引中最小的查询单元，一般为一个单词，词组
+ 词典：字典的集合，一般使用B+树或者hash表来实现
+ 倒排表：每个词条出现的频率



#### 4.13 文档的搜索

