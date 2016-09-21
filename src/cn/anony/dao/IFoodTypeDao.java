package cn.anony.dao;

import cn.anony.entity.FoodType;

import java.util.List;

/**
 * 菜系模块，dao接口设计
 * Created by anony on 2016/9/20.
 */
public interface IFoodTypeDao {
    /**
     * 添加
     */
    void save(FoodType foodType);
    /**
     * 更新
     */
    void update(FoodType foodType);
    /**
     * 删除
     */
    void delete(int id);
    /**
     * 根据主键查询
     */
    FoodType findById(int id);

    /**
     * 查询全部
     * @return
     */
    List<FoodType> getAll();
    /**
     * 根据菜系名称查询
     */
    List<FoodType> getAll(String typeName);
}
