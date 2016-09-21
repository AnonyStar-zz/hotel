package cn.anony.service.impl;

import cn.anony.dao.IFoodTypeDao;
import cn.anony.entity.FoodType;
import cn.anony.factory.BeanFactory;
import cn.anony.service.IFoodTypeService;

import java.util.List;

/**
 * 3.菜系模块业务逻辑层,接口实现类
 * Created by anony on 2016/9/21.
 */
public class FoodTypeService implements IFoodTypeService {

    //工厂类创建对象
    private IFoodTypeDao foodTypeDao = BeanFactory.getInstance("foodTypeDao",IFoodTypeDao.class);

    @Override
    public void save(FoodType foodType) {
        try {
            foodTypeDao.save(foodType);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(FoodType foodType) {
        try {
            foodTypeDao.update(foodType);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try {
            foodTypeDao.delete(id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public FoodType findById(int id) {
        try {
            return foodTypeDao.findById(id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<FoodType> getAll() {
        try {
            return foodTypeDao.getAll();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<FoodType> getAll(String typeName) {
        try {
            return foodTypeDao.getAll(typeName);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
