package priv.noby.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (Emp)实体类
 *
 * @author Noby
 * @since 2022-08-12 11:49:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Emp implements Serializable {
    private static final long serialVersionUID = 572286662274365353L;
        private Integer eid;
        private String ename;
        private Integer did;
        private Date hiredate;


}

