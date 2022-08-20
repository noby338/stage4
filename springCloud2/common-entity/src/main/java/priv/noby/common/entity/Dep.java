package priv.noby.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * (Dep)实体类
 *
 * @author Noby
 * @since 2022-08-12 11:49:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dep implements Serializable {
    private static final long serialVersionUID = -58657434773403083L;
        private Integer did;
        private String dname;

}

