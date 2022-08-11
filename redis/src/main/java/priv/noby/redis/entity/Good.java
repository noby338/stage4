package priv.noby.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * (Good)实体类
 *
 * @author Noby
 * @since 2022-08-11 16:32:27
 */
public class Good implements Serializable {
    private static final long serialVersionUID = -47198288158840128L;
        private Integer id;
        private String name;
        private Integer num;

}

