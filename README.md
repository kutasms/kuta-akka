# Kuta Service Framework(KSF)
![](https://img.shields.io/badge/license-Apache%202-blue)
![](https://img.shields.io/badge/akka-2.6%2B-orange)
![](https://img.shields.io/badge/java-1.8%2B-green)

基于AKKA的分布式集群框架，包括对Akka-Http(http1.1)、Java基础类库、缓存（Redis）封装、数据库（Mysql-mybatis和Mongodb）封装等。

使用KSF您将可以快速开发基于Akka的应用程序.

最近项目上线，部署AWS服务器（RDS数据库、REDIS集群、MONGODB集群），所以对代码进行了一些调整，特别是对REDIS集群的支持，其中包括REDIS卡槽节点处理以及在所有master节点执行操作做了一些封装。

JavaDoc文档连接
--------------------------------------------------------------------------------------------------------------------
   base -> http://s1.chhd18.com/docs/base/index.html
