package org.example.lazzy.service;

import org.apache.ibatis.annotations.Delete;
import org.example.lazzy.pojo.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    List<ShoppingCart> selectAllByUserId(Long userId);

    /**
     *  query shoppingcart info by user id and dish id;
     * @param shoppingCart
     * @return
     */
    ShoppingCart selectByUserIdAndDishId(ShoppingCart shoppingCart);

    /**
     *  add shopping cart one at a time
     */
    void save(ShoppingCart shoppingCart);

    /**
     *  update the number for the specific dish
     */
    void modifyNumberByDishId(ShoppingCart shoppingCart);

    /**
     *  delete one shopping cart item by dish ID and user ID
     * @param shoppingCart
     */
    void deleteById(ShoppingCart shoppingCart);

    /**
     *  clean shopping cart
     */
    void cleanByUserId( Long userId);
}
