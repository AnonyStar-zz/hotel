package cn.anony.service.impl;

import cn.anony.dao.IOrderDetailDao;
import cn.anony.entity.OrderDetail;
import cn.anony.factory.BeanFactory;
import cn.anony.service.IOrderDetailService;

import java.util.List;

/**
 * Created by anony on 2016/9/25.
 */
public class OrderDetailService implements IOrderDetailService {
    private IOrderDetailDao orderDetailDao = BeanFactory.getInstance("orderDetailDao",IOrderDetailDao.class);
    @Override
    public void add(OrderDetail od) {
        try {
            orderDetailDao.add(od);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OrderDetail> query() {
        try {
            return orderDetailDao.query();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OrderDetail> findByOrderid(int id) {
        try {
            return orderDetailDao.findByOrderid(id);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
