package cn.anony.entity;

import java.util.Date;

/**
 * Created by anony on 2016/9/21.
 */
public class DinnerTable {
    private int id;  //餐桌主键
    private String tableName;  //餐桌名
    private int tableStatus;  //餐桌状态
    private Date orderDate;  //预定时间

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getTableStatus() {
        return tableStatus;
    }

    public void setTableStatus(int tableStatus) {
        this.tableStatus = tableStatus;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
