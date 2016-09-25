package cn.anony.service.impl;

import cn.anony.dao.IFoodDao;
import cn.anony.entity.Food;
import cn.anony.factory.BeanFactory;
import cn.anony.service.IFoodService;
import cn.anony.utils.PageBean;

import java.util.List;

/**
 * Created by anony on 2016/9/22.
 */
public class FoodService implements IFoodService {
    //工厂创建dao对象
    private IFoodDao foodDao = BeanFactory.getInstance("foodDao",IFoodDao.class);
    @Override
    public void save(Food food) {
        try {
            foodDao.save(food);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Food food) {
        try {
            foodDao.update(food);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try {
            foodDao.delete(id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Food findById(int id) {
        try {
            return foodDao.findById(id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Food> query() {
        try {
            return foodDao.query();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Food> query(String foodName) {
        try {
            return foodDao.query(foodName);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getAll(PageBean<Food> pb) {
        try {
            foodDao.getAll(pb);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Food> findByType(int type) {
        try {
            return foodDao.findByType(type);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
