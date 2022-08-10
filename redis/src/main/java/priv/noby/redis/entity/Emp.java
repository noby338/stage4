package priv.noby.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * (Emp)实体类
 *
 * @author makejava
 * @since 2022-07-29 21:37:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Emp implements Serializable {
    private static final long serialVersionUID = -53571257720500147L;
    
    private Integer eid;
    
    private String ename;
    
    private Integer did;
    
    private String hiredate;

}




