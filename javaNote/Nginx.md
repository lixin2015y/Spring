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







