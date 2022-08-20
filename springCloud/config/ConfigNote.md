ConfigNote

* config远程配置
  * 服务端
    1. 导入config服务端依赖
    2. 配置配置文件，写入远程gitee的uri
    3. 启动类@EnableConfigServer
  * 客户端
    1. 导入config客户端端依赖
    2. 导入springboot中的监控插件
    3. 写入bootstrap.yml，远程Gitee，监控插件
    4. controller配置自动刷新注解@RefreshScope
    5. 远程注解更新后发出post请求更新配置 POST http://localhost:5051/actuator/refresh