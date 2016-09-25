package cn.anony.service;

import cn.anony.entity.Food;
import cn.anony.utils.PageBean;

import java.util.List;

/**
 * Created by anony on 2016/9/22.
 */
public interface IFoodService {
    /**
     * 添加
     */
    void save(Food Food);
    /**
     * 更新
     */
    void update(Food Food);
    /**
     * 删除
     */
    void delete(int id);
    /**
     * 根据主键查询
     */
    Food findById(int id);

    /**
     * 查询全部
     * @return
     */
    List<Food> query();
    /**
     * 根据菜品名称查询
     */
    List<Food> query(String foodName);

    public void getAll(PageBean<Food> pb);

    List<Food> findByType(int type);

}
