package cn.anony.dao.impl;

import cn.anony.dao.IOrdersDao;
import cn.anony.entity.Orders;
import cn.anony.utils.JdbcUtils;
import cn.anony.utils.PageBean;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.util.List;

/**
 * 订单DAO层接口实现类
 * Created by anony on 2016/9/25.
 */
public class OrdersDao implements IOrdersDao {
    private QueryRunner qr = JdbcUtils.getQueryRunner();
    String sql = null;
    @Override
    public void update(Orders orders) {
        sql = "UPDATE orders SET orderStatus =? WHERE id=?";
        try {
            qr.update(sql,orders.getOrderStatus(),orders.getId());
        } catch (Exception e){

        }
    }

    @Override
    public List<Orders> query() {
        sql = "SELECT * FROM orders";
        try {
            return qr.query(sql,new BeanListHandler<Orders>(Orders.class));
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Orders orders) {
        sql = "INSERT orders(table_id, orderDate, totalPrice, orderStatus) VALUES (?,?,?)";
        try {
            qr.update(sql,orders.getTable_id(),orders.getOrderDate(),orders.getTotalPrice());
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getCount() {
        sql = "SELECT count(*) FROM orders";
        try {
            Long count = qr.query(sql,new ScalarHandler<Long>());
            return count.intValue();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getAll(PageBean<Orders> pb) {
        try {
            //查询总记录数，设置到pb中
            int totalCount = this.getTotalCount();
            pb.setTotalCount(totalCount);
            //判断
            if (pb.getCurrentPage() <= 0){
                pb.setCurrentPage(1);
            }else if (pb.getCurrentPage() > pb.getTotalPage()){
                pb.setCurrentPage(pb.getTotalPage());
            }
            //获取当前页
            int currentPage = pb.getCurrentPage();
            int index = (currentPage-1)*pb.getPageCount();  //查询的起始行
            int count = pb.getPageCount();  //查询返回的行数
            //查询分页数据
            sql = "SELECT * FROM orders LIMIT ?,?";
            List<Orders> pageData = qr.query(sql,new BeanListHandler<Orders>(Orders.class),index,count);
            pb.setPageData(pageData);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getTotalCount() {
        try {
            sql = "SELECT count(*) FROM orders";
            Long count = qr.query(sql,new ScalarHandler<Long>());
            return count.intValue();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
