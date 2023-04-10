package priv.noby.redis2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Student)实体类
 *
 * @author Noby
 * @since 2022-11-11 21:48:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {
    private static final long serialVersionUID = 654043394525671145L;
    private Integer id;
    private String name;
    private Integer age;
    private Integer gender;

}

