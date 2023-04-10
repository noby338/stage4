package priv.noby.consumer.emp.openFeignClient;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import priv.noby.common.entity.Emp;
import priv.noby.common.entity.Order;
import priv.noby.common.entity.ResponseResult;

/**
 * 降级调用的类
 */
@Component
public class EmpApiFallback implements EmpApi{
    @Override
    public ResponseResult<Emp> queryById0(Integer eid) {
        return new ResponseResult(500, "queryById0-fallback",null);
    }

    @Override
    public ResponseResult<String> timeout(Integer time) {
        return new ResponseResult(500, "timeout-fallback",null);
    }

    @Override
    public ResponseEntity<Order> add(Order order) {
        return null;
    }

    @Override
    public ResponseResult<String> downgrade() {
        return new ResponseResult(500, "downgrade-fallback","调用了降级方法");
    }
}
