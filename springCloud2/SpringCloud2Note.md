springCloud2Note

* 各模块端口
  * 8081 produce-emp
  * 7071 consumer-emp
  
  * 3031 gateway
  * 2021 auth
* seata
  * 操作表storage和order进行分布式事务管理
    * 模拟同时操作消费者的dao和通过openfeign操作生产者的controller进而操作其dao
    * 模拟事务的异常
    * 通过seata分布式事务管理解决异常

    