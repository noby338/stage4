package priv.noby.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description
 * @Author noby
 * @Date 2022/7/28 16:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {
    Integer id;
    String name;
    Integer age;
    Boolean gender;

    public Student(String name, Integer age, Boolean gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
}
