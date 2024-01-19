package org.example.lazzy.service.impl;

import org.apache.ibatis.annotations.Insert;
import org.example.lazzy.common.CustomException;
import org.example.lazzy.mapper.OrderDetailMapper;
import org.example.lazzy.pojo.OrderDetail;
import org.example.lazzy.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    /**
     * save one OrderDetail object
     */

    public void save(OrderDetail orderDetail){
        if(orderDetail == null) {
            throw new CustomException("order detail cannot be null");
        }
        orderDetailMapper.save(orderDetail);
    }

}
