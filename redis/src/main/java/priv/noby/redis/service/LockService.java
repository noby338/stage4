package priv.noby.redis.service;

import priv.noby.redis.entity.Student;

public interface LockService {
    Student queryById(Integer id);
    void incr();
    void incr2();
    void incr3();
    void incrRedis();
}
