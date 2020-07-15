# nginx
## 基本概念
### 反向代理
### 正向代理

## nginx安装

+ 安装pcre
  + 解压安装包

  + sh configure

  + make && make install

  + 验证是否安装成功 pcre-config --version

  + 安装其他依赖 yum -y install make zlib zlib-devel gcc-c++ libtool openssl openssl-devel

  + 安装nginx 解压，configure  make & make install

  + /usr/local 文件夹下多一个nginx文件夹

  + 启动脚本 /usr/local/nginx/sbin/nginx

     

## nginx常用操作命令

+ 查看版本号 nginx -v
+ 关闭 nginx -s stop
+ 重新加载  niginx -s reload 重新加载配置 无需重启

## nginx的配置文件

### 全局块

+ worker_processes 1;处理并发数的配置

### EVENT块

+ worker_connections 1024 最大连接数

### HTTP块

#### HHTP全局块

#### SERVER块

## nginx实例应用

#### 反向代理


      listen       80;
            server_name  39.97.109.70;
      #charset koi8-r;
    
        #access_log  logs/host.access.log  main;
    
        location / {
            root   html;
            proxy_pass http://39.97.109.70:8080;
            index  index.html index.htm;
        }



#### nginx的负载均衡策略

+ 轮训每个请求按照时间顺序分配到不同的后端服务器
+ 权重根据配置的权重，权重越高分配的客户端越多
+ ip_hash根绝ip的hash结果进行分配
+ fair第三方，后端服务的响应时间分配，越短分配的越多

#### nginx实现动静分离



#### nginx高可用

+ 配置虚拟IP



#### nginx原理

+ 一个master多个worker。方便实现热部署
+ 每个worker是一个独立的个体，如果一个worker出现了问题，不会影响整体的服务
+ nginx和redis差不都都用了io多路复用的机制，每个worker是一个独立的线程
+ nginx的连接数
  + 发送一个请求占用了几个连接数，两个或者四个，访问静态资源是两个，访问
  + nginx有一个master和四个worker 最大并发的数  最大连接数/资源访问占用的连接数
+ 
