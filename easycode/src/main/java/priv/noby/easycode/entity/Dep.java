package priv.noby.easycode.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * (Dep)实体类
 *
 * @author Noby
 * @since 2022-08-09 11:34:44
 */
public class Dep implements Serializable {
    private static final long serialVersionUID = -67852721263215835L;
        private Integer did;
        private String dname;

}

