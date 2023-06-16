package priv.noby.elasticsearch.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 用于存储到ES中的hotel2的数据，该数据相较与HotelDoc，多了自动补全
 */
@Data
@NoArgsConstructor
public class HotelDoc2 {
    private Long id;
    private String name;
    private String address;
    private Integer price;
    private Integer score;
    private String brand;
    private String city;
    private String starName;
    private String business;
    private String location;
    private String pic;
    private List<String> suggestion;

    public HotelDoc2(Hotel hotel) {
        this.id = hotel.getId();
        this.name = hotel.getName();
        this.address = hotel.getAddress();
        this.price = hotel.getPrice();
        this.score = hotel.getScore();
        this.brand = hotel.getBrand();
        this.city = hotel.getCity();
        this.starName = hotel.getStarName();
        this.business = hotel.getBusiness();
        this.location = hotel.getLatitude() + ", " + hotel.getLongitude();
        this.pic = hotel.getPic();
        if (this.business.contains("/")) {
            // business有多个值，以/分割
            String[] split = this.business.split("/");
            this.suggestion = new ArrayList<>();
            this.suggestion.addAll(Arrays.asList(split));
            this.suggestion.add(this.brand);
        } else {
            this.suggestion = Arrays.asList(this.brand,this.business);
        }
    }
}
