package cn.anony.service;

import cn.anony.entity.FoodType;

import java.util.List;

/**
 * 3.菜系模块业务逻辑层，接口设计
 * Created by anony on 2016/9/21.
 */
public interface IFoodTypeService {
    /**
     * 添加
     * @param foodType
     */
    void save(FoodType foodType);

    /**
     * 更新
     * @param foodType
     */
    void update(FoodType foodType);

    /**
     * 删除
     * @param id
     */
    void delete(int id);

    /**
     * 按主键查询
     * @param id
     * @return
     */
    FoodType findById(int id);

    /**
     * 查询全部
     * @return
     */
    List<FoodType> getAll();

    /**
     * 按菜系名称查询
     * @param typeName
     * @return
     */
    List<FoodType> getAll(String typeName);
}
