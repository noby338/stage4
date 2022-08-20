ConsumerEmpNote

* 该模块是集群环境中调用其他模块的模块(模拟消费者)
  * 该模块引用的 common-entity 为公共模块
  * 该模块调用其他模块的controller的方法
    * 使用RestTemplate类
      * 输入请求的ip和端口
      * Eureka中的DiscoveryClient通过服务名获取要调用服务的IP地址和端口
      * Eureka中的Ribbon通过服务名获取要调用服务的IP地址和端口
        * Ribbon为客户端的负载均衡(nginx为服务端的负载均衡)，生产者从服务列表中获取了一个服务的多个地址，由客户端的负载算法决定向那个服务器发送请求
    * OpenFeign通过服务名获取要调用服务的IP地址和端口(注册中心的生产者基于Eureka)
      * 使用步骤
        1. 导入依赖
        2. 启动类添加注解
        3. 声明OpenFeign接口，接口添加注解
          * 接口的请求路径必须和生产者的controller一致
        4. 本模块controller调用该接口
      * 超时配置
        * 配置类可指定超时时间
      * OpenFeign日志
        * 当spring的日志打开该日志才生效 
        * 日志级别 NONE，BASIC，HEADERS，FULL
        * 配置文件中配置日志