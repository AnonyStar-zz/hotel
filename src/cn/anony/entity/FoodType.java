package cn.anony.entity;

/**
 * Created by anony on 2016/9/20.
 */

/**
 * 1.菜系模板，实体类设计
 */
public class FoodType {
    private int id;
    private  String typeName;

    public void setId(int id) {
        this.id = id;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    public int getId() {
        return id;
    }
    public String getTypeName() {
        return typeName;
    }
}
