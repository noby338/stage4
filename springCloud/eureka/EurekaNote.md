EurekaNote

* Eureka是一个Netflix开发的微服务的注册中心,可以在分布式项目中管理多个模块之间的相互调用
  * 该模块启用时Eureka服务端才可用，消费者consumer-emp和生产者provider-emp都为该服务的客户端
  * 三部分组成
    * Eureka Server (注册中心)提供服务注册和发现
    * Service Provider 服务提供方，将自身服务注册到Eureka ，从而使服务消费方能够找到
      * 本项目中的provider-emp
    * Service Consumer 服务消费方从Eureka获取注册服务列表，从而能够消费服务 
      * 本项目中的consumer-emp