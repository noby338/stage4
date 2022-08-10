package priv.noby.easycode.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * (Emp)实体类
 *
 * @author Noby
 * @since 2022-08-09 11:34:44
 */
public class Emp implements Serializable {
    private static final long serialVersionUID = -56761101041569549L;
        private Integer eid;
        private String ename;
        private Integer did;
        private Date hiredate;

}

