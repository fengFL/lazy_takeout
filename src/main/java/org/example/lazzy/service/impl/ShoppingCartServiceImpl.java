package org.example.lazzy.service.impl;

import org.apache.ibatis.annotations.Delete;
import org.example.lazzy.mapper.ShoppingCartMapper;
import org.example.lazzy.pojo.ShoppingCart;
import org.example.lazzy.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    /**
     * query all the shopping cart info for the specific user
     * @param userId
     * @return
     */
    @Override
    public List<ShoppingCart> selectAllByUserId(Long userId) {
        return shoppingCartMapper.selectAllByUserId(userId);

    }

    /**
     *  query shopping cart info by user id and dish id
     * @param shoppingCart
     * @return
     */
    @Override
    public ShoppingCart selectByUserIdAndDishId(ShoppingCart shoppingCart) {
        return shoppingCartMapper.selectByUserIdAndDishId(shoppingCart);

    }

    /**
     *  add shopping cart one at a time
     */
    public void save(ShoppingCart shoppingCart){
        shoppingCartMapper.save(shoppingCart);
    }

    /**
     *  update the number for the specific dish
     */
    public void modifyNumberByDishId(ShoppingCart shoppingCart){
        shoppingCartMapper.modifyNumberByDishId(shoppingCart);
    }

    /**
     *  delete one item in shopping cart by ID
     * @param shoppingCart
     */
    @Override
    public void deleteById(ShoppingCart shoppingCart) {
        shoppingCartMapper.deleteById(shoppingCart);
    }

    /**
     *  clean shopping cart
     */

    public void cleanByUserId( Long userId){
        shoppingCartMapper.cleanByUserId(userId);
    }

}
