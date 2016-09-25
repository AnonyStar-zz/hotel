package cn.anony.dao;

import cn.anony.entity.Food;
import cn.anony.utils.PageBean;

import java.util.List;

/**
 * 菜品dao数据层，接口设计
 * Created by anony on 2016/9/22.
 */
public interface IFoodDao {
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

    /**
     * 分页查询数据
     */
    void getAll(PageBean<Food> pb);

    /**
     * 查询总记录数
     */
    int getTotalCount(PageBean<Food> pb);

    /**
     * 根据食物类型找到食物
     * @param type
     * @return
     */
    List<Food> findByType(int type);
}
