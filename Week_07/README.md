* [MySQL insert的优化](https://dev.mysql.com/doc/refman/8.0/en/insert-optimization.html)  
* Java应用层面的优化  
    * 使用连接池  
    * 多线程并发  

| 插入方式 | 耗时 | 备注 |
| :--- | :---:  | :---  |
| 单一链接，单次insert，一条记录一提交  | life-time long |    |
| 单一链接，批量insert  | 132秒 |    |
| 单一链接，单次insert，value列表形式  | 8秒 |    |
| 连接池，十个线程，批量insert，每个线程插入10W  | 单一线程耗时19秒以内 |    |

* 多数据源，基于DataSource的配置来选择性的注入，动态性不足  
* 基于ShardingSphere-jdbc，参照demo工程：sharding-spring-namespace-jpa-example  
    * 配置application-replica-query.properties定义了主从库的配置，同时定义了从库的轮训负载均衡机制