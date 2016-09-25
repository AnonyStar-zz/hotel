package cn.anony.dao;

import cn.anony.entity.OrderDetail;

import java.util.List;

/**
 * Created by anony on 2016/9/25.
 */
public interface IOrderDetailDao {

    void add(OrderDetail od);
    List<OrderDetail> query();
    List<OrderDetail> findByOrderid(int id);
}
