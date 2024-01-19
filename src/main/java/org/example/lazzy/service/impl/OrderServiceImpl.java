package org.example.lazzy.service.impl;

import org.example.lazzy.mapper.OrderMapper;
import org.example.lazzy.pojo.Orders;
import org.example.lazzy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    /**
     *  save order
     * @param orders
     */
    @Override
    public void save(Orders orders) {
        orderMapper.save(orders);
    }

    // paging query
    @Override
    public List<Orders> selectWithPage(int page, int pageSize) {
        // calculate the start index
        int index = (page - 1) * pageSize;
        return orderMapper.selectWithPage(index, pageSize);
    }

    @Override
    public int total() {
        return orderMapper.total();
    }
}
