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

