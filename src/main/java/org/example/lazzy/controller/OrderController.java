package org.example.lazzy.controller;

import org.apache.ibatis.annotations.Delete;
import org.example.lazzy.common.BaseContext;
import org.example.lazzy.common.CustomException;
import org.example.lazzy.common.Result;
import org.example.lazzy.dto.OrdersDto;
import org.example.lazzy.pojo.*;
import org.example.lazzy.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AddressBookService addressBookService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderDetailService orderDetailService;


    /**
     *  accept submit info
     */
    @Transactional
    @PostMapping(("/submit"))
    public Result<String> submit(@RequestBody Orders orders){
        if(orders == null) {
            throw new CustomException("orders is null");
        }
        // get user id
        Long userId = BaseContext.getCurrentId();
        orders.setUserId(userId); // set user id
        // generate order number
        String orderNum = UUID.randomUUID().toString();
        orders.setOrderTime(LocalDateTime.now()); // order time
        orders.setNumber(orderNum); // set order number
        Long orderId = System.currentTimeMillis();
        orders.setId(orderId); // generate id
        // query shopping cart info by user id
        List<ShoppingCart> shoppingCartList = shoppingCartService.selectAllByUserId(userId);
        if(shoppingCartList == null || shoppingCartList.size() == 0) {
            throw new CustomException("There is no item in the cart");
        }
        AtomicInteger amount = new AtomicInteger(0);
        for (ShoppingCart shoppingCart : shoppingCartList) {
            amount.addAndGet(shoppingCart.getAmount().multiply(new BigDecimal(shoppingCart.getNumber())).intValue());
        }
        orders.setAmount(new BigDecimal(amount.get())); // total amount
        // get address by addressId
        AddressBook addressBook = addressBookService.selectById(orders.getAddressBookId());
        if(addressBook == null) {
            throw new CustomException("address book is null");
        }
        orders.setAddress(addressBook.getDetail());// address
        orders.setPhone(addressBook.getPhone()); // phone
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setConsignee(addressBook.getConsignee());
        User user = userService.selectByPhone(addressBook.getPhone());

        if(user != null && user.getName() != null) {
            orders.setUserName(user.getName());
        }
        orders.setStatus(2);

        // insert the order into orders
        orderService.save(orders);

        // insert data into order_details
        shoppingCartList.forEach(item -> {
            // prepare the data for orderDetail
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);

            // query dish info
            orderDetail.setId(System.currentTimeMillis());
            orderDetail.setImage(item.getImage());
            orderDetail.setName(item.getName());
            orderDetail.setAmount(item.getAmount());
            orderDetail.setNumber(item.getNumber());
            if(item.getDishFlavor() != null) {
                orderDetail.setDishFlavor(item.getDishFlavor());
            }


            if(item.getDishId() != null) {
                // the item is a dish
                // get the dish id
                Long dishId = item.getDishId();
                orderDetail.setDishId(dishId);
            } else {

                // the item is a setmeal, contains several dishes
                // get setmeal ID
                Long setmealId = item.getSetmealId();
                // set setmeal ID
                orderDetail.setSetmealId(setmealId);
            }
            // save the data into order_detail
            orderDetailService.save(orderDetail);
        });



        /**
         *  after payment paid, clear the shopping cart
         */

        // clear the shopping cart
        shoppingCartService.cleanByUserId(userId);
//        shoppingCartList.forEach(item -> {
//            ShoppingCart shoppingCart = new ShoppingCart();
//            shoppingCart.setUserId(userId);
//            if(item.getDishId() != null) {
//                // the item is a dish
//                shoppingCart.setDishId(item.getDishId());
//            } else {
//                // the item is a setmeal
//                shoppingCart.setSetmealId(item.getSetmealId());
//            }
//            shoppingCartService.deleteById(shoppingCart);
//        });
        return Result.success("sumbit successful");
    }

    /**
     *  paging query for order list
     */
    @GetMapping("/userPage")
    public Result<PageBean<Orders>> list(Integer page, Integer pageSize){
        // check page and pageSize
        if(page == null || pageSize == null) {
            throw new CustomException("page or pageSize cannot be null");
        }
        if(page < 0 || pageSize <= 0) {
            throw  new CustomException("page needs greater than or equal to 0, page size needs greater than 0");
        }
        List<Orders> orders = orderService.selectWithPage(page, pageSize);
        if(orders == null) {
            throw new CustomException("no orders exist");
        }

//        PageBean<OrdersDto> pageBean = new PageBean<>();
//        orders.stream().map(order -> {
//            OrdersDto ordersDto = new OrdersDto();
//            List<OrderDetail> orderDetails = orderDetailService.selectByOrderId(order.getId());
//            ordersDto.setOrderDetails(orderDetails);
//            ordersDto.setPhone(order.getPhone());
//            ordersDto.setAddress(order.getAddress());
//            ordersDto.setConsignee(order.getConsignee());
//            ordersDto.setUserName(order.getUserName());
//            return ordersDto;
//        }).collect(Collectors.toList());

        PageBean<Orders> pageBean = new PageBean<>();
        pageBean.setTotal(orderService.total());
        pageBean.setRecords(orders);
        return Result.success(pageBean);
    }


}
