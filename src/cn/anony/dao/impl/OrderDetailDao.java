package cn.anony.dao.impl;

import cn.anony.dao.IOrderDetailDao;
import cn.anony.entity.OrderDetail;
import cn.anony.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

/**
 * Created by anony on 2016/9/25.
 */
public class OrderDetailDao implements IOrderDetailDao {
    private QueryRunner qr = JdbcUtils.getQueryRunner();
    String sql=null;
    @Override
    public void add(OrderDetail od) {
        sql = "INSERT orderdetail(orderId, food_id, foodCount) VALUES (?,?,?)";
        try {
            qr.update(sql,od.getOrderId(),od.getFood_id(),od.getFoodCount());
        } catch (Exception e){
            throw  new RuntimeException(e);
        }
    }

    @Override
    public List<OrderDetail> query() {
        try {
            sql = "SELECT * FROM orderdetail";
            return qr.query(sql,new BeanListHandler<OrderDetail>(OrderDetail.class));
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<OrderDetail> findByOrderid(int id) {
        try {
            sql = "SELECT * FROM orderdetail where orderId=?";
            return qr.query(sql,new BeanListHandler<OrderDetail>(OrderDetail.class),id);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
