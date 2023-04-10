springCloud2Note

* 各模块端口
  * 8081 produce-emp
  * 7071 consumer-emp
    * sentinel:8719，管控台8080
  * 5051 config-test
  * 3031 gateway
  * 2021 auth
* seata
  * 操作表storage和order进行分布式事务管理
    * 模拟同时操作消费者的dao和通过openfeign操作生产者的controller进而操作其dao
    * 模拟事务的异常
    * 通过seata分布式事务管理解决异常
* sentinel
  * 断路器
  * 打开cmd， java -jar sentinel-dashboard-1.8.1.jar

    