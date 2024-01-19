package org.example.lazzy.mapper;

import org.apache.ibatis.annotations.*;
import org.example.lazzy.pojo.ShoppingCart;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {
    // query all the shopping cart info for the specific user
    @Select("select * from shopping_cart where user_id = #{userId};")
    List<ShoppingCart> selectAllByUserId(Long userId);

    @Select("select * from shopping_cart where user_id = #{userId} && dish_id = #{dishId};")
    ShoppingCart selectByUserIdAndDishId(ShoppingCart shoppingCart);

    // add shoppingcart one at a time
    @Insert("insert into shopping_cart (id, name, image,user_id, dish_id, setmeal_id, dish_flavor, number, amount, create_time)" +
            "values (#{id}, #{name}, #{image}, #{userId}, #{dishId}, #{setmealId}, #{dishFlavor}, #{number}, #{amount}, #{createTime});")
    void save(ShoppingCart shoppingCart);

    // update shoppingCart number by dish ID or setmeal_id
    @Update("update shopping_cart set number = #{number} where (dish_id = #{dishId} or setmeal_id = #{setmealId}) and user_id = #{userId};")
    void modifyNumberByDishId(ShoppingCart shoppingCart);


    /**
     *  delete one shopping cart item by ID and user ID
     * @param shoppingCart
     */
    @Delete("delete from shopping_cart where (dish_id = #{dishId} or setmeal_id = #{setmealId}) and user_id = #{userId};")
    void deleteById(ShoppingCart shoppingCart);

    /**
     *  clean shopping cart
     */
    @Delete("delete from shopping_cart where user_id = #{userId};")
    void cleanByUserId( Long userId);
}

