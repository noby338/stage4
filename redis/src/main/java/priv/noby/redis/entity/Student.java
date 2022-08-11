package priv.noby.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * (Student)实体类
 *
 * @author makejava
 * @since 2022-08-05 23:45:14
 */
public class Student implements Serializable {
    private static final long serialVersionUID = 847074965399466270L;
        private Integer id;
        private String name;
        private Integer age;
        private Boolean gender;

    public Student(String name, Integer age, Boolean gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
}

