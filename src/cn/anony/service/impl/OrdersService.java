package cn.anony.service.impl;

import cn.anony.dao.IOrdersDao;
import cn.anony.entity.Orders;
import cn.anony.factory.BeanFactory;
import cn.anony.service.IOrdersService;
import cn.anony.utils.PageBean;

import java.util.List;

/**
 * Created by anony on 2016/9/25.
 */
public class OrdersService implements IOrdersService {
    private IOrdersDao ordersDao = BeanFactory.getInstance("ordersDao",IOrdersDao.class);
    @Override
    public void update(Orders orders) {
        try {
            ordersDao.update(orders);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Orders> query() {
        try {
            return ordersDao.query();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Orders orders) {
        try {
            ordersDao.add(orders);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getCount() {
        try {
            return ordersDao.getCount();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getAll(PageBean<Orders> pb) {
        try {
            ordersDao.getAll(pb);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getTotalCount() {
        try {
            return ordersDao.getTotalCount();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
