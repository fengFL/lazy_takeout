package org.example.lazzy.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.lazzy.pojo.Orders;

import java.util.List;

@Mapper
public interface OrderMapper {
    @Insert("insert into orders (id, number, status, user_id, address_book_id, order_time, checkout_time, pay_method, amount, remark," +
            "phone, address, user_name, consignee) values (#{id}, #{number}, #{status}, #{userId}, #{addressBookId}, #{orderTime}, #{checkoutTime}," +
            " #{payMethod}, #{amount}, #{remark}, #{phone}, #{address}, #{userName}, #{consignee})")
    void save(Orders orders);

    // query with limit
    @Select("select * from orders limit #{index}, #{pageSize};")
    List<Orders> selectWithPage(@Param("index") int index, @Param("pageSize") int pageSize);

    // query the total count
    @Select("select count(*) from orders;")
    int total();
}
