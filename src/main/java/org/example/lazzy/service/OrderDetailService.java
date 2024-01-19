package org.example.lazzy.service;

import org.apache.ibatis.annotations.Insert;
import org.example.lazzy.pojo.OrderDetail;

public interface OrderDetailService {

    /**
     * save one OrderDetail object
     */
    void save(OrderDetail orderDetail);
}
