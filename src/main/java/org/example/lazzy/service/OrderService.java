package org.example.lazzy.service;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.lazzy.pojo.Orders;

import java.util.List;

public interface OrderService {

    /**
     * save order
     */
    void save(Orders orders);

    // query with limit for paging query
    List<Orders> selectWithPage(int page, int pageSize);

    // query the total of records
    int total();
}
