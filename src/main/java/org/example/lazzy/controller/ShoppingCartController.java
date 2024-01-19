package org.example.lazzy.controller;

import org.example.lazzy.common.BaseContext;
import org.example.lazzy.common.CustomException;
import org.example.lazzy.common.Result;
import org.example.lazzy.pojo.Dish;
import org.example.lazzy.pojo.ShoppingCart;
import org.example.lazzy.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    /**
     *  display the shopping list by user ID
     */
    @GetMapping("/list")
    public Result<List<ShoppingCart>> list(){
        Long userId = BaseContext.getCurrentId();
        // query all the ShoppingCart info for the specific user
        List<ShoppingCart> shoppingCartList = shoppingCartService.selectAllByUserId(userId);
        if(shoppingCartList == null) {
            throw new CustomException("shopping cart cannot be null");
        }
        return Result.success(shoppingCartList);
    }

    /**
     *  add item to shopping cart
     */
    @PostMapping("/add")
    public Result<String> save(@RequestBody ShoppingCart shoppingCart){
        // check the data
        if(shoppingCart == null) {
            throw new CustomException("shopping cart does not exist");
        }

        // get user ID
        Long userId = BaseContext.getCurrentId();
        // get Dish Id
        Long dishId = shoppingCart.getDishId();
        // get Setmeal Id
        Long setmealId = shoppingCart.getSetmealId();


        // query the shopping cart for the same dish or the same setmeal
        List<ShoppingCart> shoppingCartList = shoppingCartService.selectAllByUserId(userId);
        for (ShoppingCart cart : shoppingCartList) {
            if((cart.getDishId() != null && dishId != null && cart.getDishId().compareTo(dishId) == 0)|| (cart.getSetmealId() != null && setmealId != null && cart.getSetmealId().compareTo(setmealId) == 0)){

                Integer number = cart.getNumber();
                ++number;
                cart.setNumber(number);
                // update the info
                shoppingCartService.modifyNumberByDishId(cart);
                return Result.success("update dish successful");


            }

        }

        // dish ID or setmeal ID does not exist
        // generate ID
        Long id = System.currentTimeMillis();
        shoppingCart.setNumber(1);
        shoppingCart.setId(id);
        shoppingCart.setUserId(userId);
        shoppingCart.setCreateTime(LocalDateTime.now());
        shoppingCart.setDishId(dishId);
        shoppingCart.setSetmealId(setmealId);
        shoppingCartService.save(shoppingCart);
        return Result.success("add successful");
    }

    /**
     *  sub dish by 1
     */
    @PostMapping("/sub")
    public Result<String> sub(@RequestBody ShoppingCart shoppingCart){
        // check the data
        if(shoppingCart == null) {
            throw new CustomException("shopping cart does not exist");
        }

        // get user ID
        Long userId = BaseContext.getCurrentId();

        // query database for the same dish or the same setmeal
        // get dish ID
        Long dishId = shoppingCart.getDishId();
        shoppingCart.setUserId(userId);

        // get the number for the dish
        if(dishId == null) {
            throw new CustomException("no such dish ID");
        }

        ShoppingCart shoppingCartResult = shoppingCartService.selectByUserIdAndDishId(shoppingCart);
        Integer num = shoppingCartResult.getNumber();
        // decrease number by 1 or set it to 0 if number is less than 1
        if(num >= 1) {
            --num;
        } else {
            num = 0;
        }
        shoppingCartResult.setNumber(num);
        shoppingCartService.modifyNumberByDishId(shoppingCartResult);

        return Result.success("add successful");
    }

    /**
     *  clean shopping cart
     */
    @DeleteMapping("/clean")
    public Result<String> clean(){
        Long userId = BaseContext.getCurrentId();
        shoppingCartService.cleanByUserId(userId);
        return Result.success("clean successful");
    }
}
