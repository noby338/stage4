package priv.noby.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * (Dep)实体类
 *
 * @author makejava
 * @since 2022-07-29 21:37:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dep implements Serializable {
    private static final long serialVersionUID = -58692043034729851L;
    
    private Integer did;
    
    private String dname;

}




