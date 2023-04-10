# redis3
- 集群环境session的同步
    - 在多个微服务部署的场景中，用户登录之后，从服务A生成session, 拿到sessionId，去请求服务B，服务B没有这个session, 就会出现鉴权不通过的情况。这时需要引入分布式session, 常见的是基于redis 的分布式session
    - 使用步骤(前提是使用了redis的依赖)
        1. 导入 spring-session-data-redis
        2. login controller
            * 此步骤将session存入redis
        3. getSession controller
            - 此步骤存redis中获取session
    - 测试时，启动两个端口8080和8081，端口8080调用login实现将信息存放在session中，端口8081调用getSession从输出noby则说明session在两个tomcat之间共享。
- 使用spring-session-data-redis实现Session共享的原理比较简单：
    - 在传统的Servlet容器中，Session是存储在内存中的，每个Web服务器都有自己独立的Session存储空间。因此，当一个用户在一个Web服务器上登录后，如果请求被转发到另一个Web服务器上，那么另一个Web服务器并不能获取到该用户的Session信息，从而导致Session无法共享。
    - 而通过使用Redis作为Session存储介质，多个Web服务器可以共享同一个Redis服务，将Session数据存储在Redis中，从而实现了Session的共享。具体实现过程如下：
        - 当一个用户在任意一个Web服务器上登录时，该Web服务器会在Redis中创建一个新的Session，并将Session ID 保存到Cookie中，返回给客户端。
        - 客户端在后续的请求中携带该Cookie，请求会被转发到任意一个Web服务器上。
        - 当该Web服务器接收到请求后，会根据Cookie中的Session ID 从Redis中获取对应的Session数据，从而实现了Session的共享。
