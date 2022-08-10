redis 2022/7/28

* java 中 redis 的使用
  * jedis
  * StringRedisTemplate 类
    * 存储对象不方便  * RedisTemplate 类
    * 存储对象时存储redis中的默认为二进制文件，可读性和跨平台性差
      * 自定义 RedisTemplate 配置类，修改默认的序列化器
    * key 为 object 存储操作不便(key 的数据类型应该都为 String)
    * 
    
