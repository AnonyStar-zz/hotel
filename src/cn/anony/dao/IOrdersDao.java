package cn.anony.dao;

import cn.anony.entity.Orders;
import cn.anony.utils.PageBean;

import java.util.List;

/**
 * Created by anony on 2016/9/25.
 */
public interface IOrdersDao {

    void update(Orders orders);
    List<Orders> query();
    void add(Orders orders);
    int getCount();
    void getAll(PageBean<Orders> pb);
    int getTotalCount();
}
