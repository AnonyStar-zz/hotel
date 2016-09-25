package cn.anony.service;

import cn.anony.entity.Orders;
import cn.anony.utils.PageBean;

import java.util.List;

/**
 * 订单详情DAO层接口实现层
 * Created by anony on 2016/9/25.
 */
public interface IOrdersService {
    void update(Orders orders);
    List<Orders> query();
    void add(Orders orders);
    int getCount();
    void getAll(PageBean<Orders> pb);
    int getTotalCount();
}
