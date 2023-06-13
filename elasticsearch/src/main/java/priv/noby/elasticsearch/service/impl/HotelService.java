package priv.noby.elasticsearch.service.impl;

import priv.noby.elasticsearch.mapper.HotelMapper;
import priv.noby.elasticsearch.pojo.Hotel;
import priv.noby.elasticsearch.service.IHotelService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class HotelService extends ServiceImpl<HotelMapper, Hotel> implements IHotelService {
}
