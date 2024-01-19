package org.example.lazzy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.example.lazzy.pojo.OrderDetail;

@Mapper
public interface OrderDetailMapper {
    /**
     * save one OrderDetail object
     */
    @Insert("insert into order_detail (id, name, image, order_id, dish_id, setmeal_id, dish_flavor, number, amount) " +
            "values(#{id}, #{name}, #{image}, #{orderId}, #{dishId}, #{setmealId}, #{dishFlavor}, #{number}, #{amount});")
    void save(OrderDetail orderDetail);
}
