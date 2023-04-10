package priv.noby.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Order)实体类
 *
 * @author Noby
 * @since 2022-08-15 20:52:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {
    private static final long serialVersionUID = -85289077188231842L;
    private Long id;
    private Long userId;//用户id
    private Long productId;//产品id
    private Integer count;//数量
    private Double money;//金额
    private Integer status;//订单状态：0：创建中；1：已完结

}

