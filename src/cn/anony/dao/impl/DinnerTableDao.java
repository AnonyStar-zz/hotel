package cn.anony.dao.impl;

import cn.anony.dao.IDinnerTableDao;
import cn.anony.entity.DinnerTable;
import cn.anony.utils.JdbcUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

/**
 * 餐桌dao层，接口实现类
 * Created by anony on 2016/9/21.
 */
public class DinnerTableDao implements IDinnerTableDao {

    private QueryRunner qr = JdbcUtils.getQueryRunner();
    String sql;

    @Override
    public void add(DinnerTable dinnerTable) {
        try {
            sql = "INSERT dinnertable(tableName) VALUES (?)";
            qr.update(sql,dinnerTable.getTableName());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            sql = "DELETE FROM dinnertable WHERE id=?;";
            qr.update(sql,id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(DinnerTable dinnerTable) {
        try {
            sql = "UPDATE dinnertable SET tableStatus=?,orderDate=? WHERE id=?;";
            qr.update(sql,dinnerTable.getTableStatus(),dinnerTable.getOrderDate(),dinnerTable.getId());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void quitTable(Integer id) {
        try {
            sql = "UPDATE dinnertable SET tableStatus=?,orderDate=? WHERE id=?;";
            qr.update(sql,0,null,id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DinnerTable> getAll() {
        try {
            sql = "SELECT * FROM dinnertable;";
            return qr.query(sql, new BeanListHandler<DinnerTable>(DinnerTable.class));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public DinnerTable findById(Integer id) {
        try {
            sql = "SELECT * FROM dinnertable WHERE id=?;";
            return qr.query(sql,new BeanHandler<DinnerTable>(DinnerTable.class),id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DinnerTable> query(String keyword) {
        try {
            sql = "SELECT * FROM dinnertable WHERE tableName LIKE ?;";
            return qr.query(sql,new BeanListHandler<DinnerTable>(DinnerTable.class),"%"+keyword+"%");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
