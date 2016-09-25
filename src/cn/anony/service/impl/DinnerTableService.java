package cn.anony.service.impl;

import cn.anony.dao.IDinnerTableDao;
import cn.anony.entity.DinnerTable;
import cn.anony.factory.BeanFactory;
import cn.anony.service.IDinnerTableService;

import java.util.Date;
import java.util.List;

/**
 * Created by anony on 2016/9/21.
 */
public class DinnerTableService implements IDinnerTableService {
    //工厂类创建dao
    IDinnerTableDao dinnerTableDao = BeanFactory.getInstance("dinnerTableDao",IDinnerTableDao.class);

    @Override
    public void add(DinnerTable dinnerTable) {
        try {
            dinnerTableDao.add(dinnerTable);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            dinnerTableDao.delete(id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(DinnerTable dinnerTable) {
        try {
            dinnerTableDao.update(dinnerTable);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void quitTable(Integer id) {
        try {
            dinnerTableDao.quitTable(id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DinnerTable> query() {
        try {
            return dinnerTableDao.getAll();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public DinnerTable findById(Integer id) {
        try {
            return dinnerTableDao.findById(id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DinnerTable> query(String keyword) {
        try {
            return dinnerTableDao.query(keyword);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public DinnerTable changeStatus(int id) {
        try {
            DinnerTable table = dinnerTableDao.findById(id);
            int status = table.getTableStatus();
            if (status == 0){
                status = 1;
                Date date = new Date();
                table.setOrderDate(date);
            }else if (status == 1){
                status = 0;
                table.setOrderDate(null);
            }
            table.setTableStatus(status);
            dinnerTableDao.update(table);
            return table;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
