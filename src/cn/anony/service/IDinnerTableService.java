package cn.anony.service;

import cn.anony.entity.DinnerTable;

import java.util.List;

/**
 * Created by anony on 2016/9/21.
 */
public interface IDinnerTableService {
    /**
     * 添加餐桌
     * @param dinnerTable
     */
    void add(DinnerTable dinnerTable);

    /**
     * 删除餐桌
     * @param id
     */
    void delete(Integer id);

    /**
     * 修改餐桌
     * @param dinnerTable
     */
    void update(DinnerTable dinnerTable);

    /**
     * 退桌
     * @param id
     */
    void quitTable(Integer id);

    /**
     * 餐桌列表展示
     * @return
     */
    List<DinnerTable> query();

    /**
     * 按照主键查找餐桌
     * @param id
     * @return
     */
    DinnerTable findById(Integer id);

    /**
     * 按关键字搜索餐桌
     * @param keyword
     * @return
     */
    List<DinnerTable> query(String keyword);

    DinnerTable changeStatus(int id);

}
