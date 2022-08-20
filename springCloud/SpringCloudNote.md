SpringCloudNote

* 各模块端口
  * 8081 produce-emp
  * 7071 consumer-emp
  * 6061 eureka
  * 5051 config-test
  * 4041 config
  * 3031 gateway
  * 2021 auth
* Hystrix
  * 断路器，熔断器
    熔断器状态
    降级	关闭
    熔断  打开
    恢复	半开
* seata
  * 操作表storage和order进行分布式事务管理
    * 模拟同时操作消费者的dao和通过openfeign操作生产者的controller进而操作其dao
    * 模拟事务的异常
    * 通过seata分布式事务管理解决异常
* Zuul
  * 微服务网关
  * nginx实现微服务网关
  * zuul+EurekaClient
  * Zuul中的过滤器
    