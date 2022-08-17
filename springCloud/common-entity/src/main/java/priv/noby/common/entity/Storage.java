package priv.noby.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Storage)实体类
 *
 * @author Noby
 * @since 2022-08-15 20:50:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Storage implements Serializable {
    private static final long serialVersionUID = -50696766625271432L;
        private Long id;
        private Long productId;//产品id
        private Integer total;//总库存
        private Integer used;//已用库存
        private Integer residue;//剩余库存

}

