ConfigTestNote

* nacos远程注解的使用
  * 添加nacos config依赖
  * 添加配置文件bootstrap.yml
    * nacos的服务端配置列表添加配置文件 config2-dev.yaml
      * 配置文件中填写常改配置(可填写部分配置由远程配置)
    * 实现远程配置的即时生效
      * 方式1
        * controller添加@RefreshScope注解
      * 方式2
        * 在spring容器中注入一个类，类上定义@ConfigurationProperties(prefix = "pattern")注解
        * 类中定义需要实时更新的变量
        * 在使用类中通过@Autowaired注入该对象
        * 通过对象.get属性名获取属性
    * 使用mysql存储配置文件(默认是使用nacos内置的数据库)
      * 修改软件nacos软件的config/application.properties文件
      * 启动startup.cmd