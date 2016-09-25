package cn.anony.dao.impl;

import cn.anony.dao.IFoodDao;
import cn.anony.entity.Food;
import cn.anony.utils.Condition;
import cn.anony.utils.JdbcUtils;
import cn.anony.utils.PageBean;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anony on 2016/9/22.
 */
public class FoodDao implements IFoodDao {
    private QueryRunner qr = JdbcUtils.getQueryRunner();
    String sql;
    @Override
    public void save(Food food) {
        try {
            sql = "INSERT food(foodName, foodType_id, price, mprice, remark, img) VALUES (?,?,?,?,?,?)";
            qr.update(sql,food.getFoodName(),food.getFoodType_id(),food.getPrice(),food.getMprice(),food.getRemark(),food.getImg());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Food food) {
        try {
            sql = "UPDATE food SET foodName=?,foodType_id=?,price=?,mprice=?,remark=?,img=? WHERE id=?;";
            qr.update(sql,food.getFoodName(),food.getFoodType_id(),food.getPrice(),food.getMprice(),
                      food.getRemark(),food.getImg(),food.getId());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try {
            sql = "DELETE FROM food WHERE id =?";
            qr.update(sql,id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public Food findById(int id) {
        try {
            sql = "SELECT * FROM food WHERE id =?";
            return qr.query(sql,new BeanHandler<Food>(Food.class),id);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Food> query() {
        try {
            sql = "SELECT * FROM food";
            return  qr.query(sql,new BeanListHandler<Food>(Food.class));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Food> query(String foodName) {
        try {
            sql = "SELECT *FROM food WHERE foodName LIKE ?";
            return qr.query(sql,new BeanListHandler<Food>(Food.class),"%"+foodName+"%");
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getAll(PageBean<Food> pb) {
        //2.查询总记录数，设置到pb对象中
        int totalCount = this.getTotalCount(pb);
        pb.setTotalCount(totalCount);

        List<Object> list = new ArrayList<Object>();
        /*
		 * 问题： jsp页面，如果当前页为首页，再点击上一页报错！
		 *              如果当前页为末页，再点下一页显示有问题！
		 * 解决：
		 * 	   1. 如果当前页 <= 0;       当前页设置当前页为1;
		 * 	   2. 如果当前页 > 最大页数；  当前页设置为最大页数
		 */
        //判断
        if (pb.getCurrentPage() <= 0){
            pb.setCurrentPage(1);
        }else if (pb.getCurrentPage() > pb.getTotalPage()){
            pb.setCurrentPage(pb.getTotalPage());
        }

        //1.获取当前页面，计算查询的起始行、返回行数
        int currentPage = pb.getCurrentPage();
        int index = (currentPage - 1) *pb.getPageCount();
        int count = pb.getPageCount();

        Condition condition = pb.getCondition();

        //3.分页查询数据，把查询到的数据设置到pb对象中
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT ");
        sb.append("     f.id, ");
        sb.append("     f.foodName,");
        sb.append("     f.foodType_id,");
        sb.append("     f.price,");
        sb.append("     f.mprice,");
        sb.append("     f.remark,");
        sb.append("     f.img,ft.typeName");
        sb.append(" FROM ");
        sb.append("     food f,foodtype ft ");
        sb.append(" WHERE 1=1 ");
        sb.append(" AND  f.foodType_id=ft.id");

        //判断
        if (condition != null){
            String foodName = condition.getFoodName();
            if (foodName != null && !foodName.isEmpty()){
                sb.append(" AND f.foodName LIKE ?");
                list.add("%"+foodName+"%");
            }
            int type_id = condition.getFoodType_id();
            if(type_id > 0 ){
                sb.append(" AND f.foodType_id=?");
                list.add((type_id));
            }
        }
        sb.append(" limit ?,?");
        list.add(index);
        list.add(count);
        try {
            //根据当前页，查询当前页数据（一页数据）
            if (index >= 0){
                List<Food> pageData = qr.query(sb.toString(),new BeanListHandler<Food>(Food.class),list.toArray());
                pb.setPageData(pageData);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public int getTotalCount(PageBean<Food> pb) {
        StringBuilder sb = new StringBuilder();
        List<Object> list = new ArrayList<Object>();
        sb.append(" SELECT ");
        sb.append("   count(*)");
        sb.append("   FROM food f, ");
        sb.append("   foodtype ft ");
        sb.append(" WHERE  1=1 ");
        sb.append(" AND f.foodType_id=ft.id ");

        Condition condition = pb.getCondition();
        //判断
        if (condition != null){
            String foodName = condition.getFoodName();
            if (foodName != null && !foodName.isEmpty()){
                sb.append(" AND f.foodName LIKE ? ");
                list.add("%"+foodName+"%");
            }
            int type_id = condition.getFoodType_id();
            if (type_id > 0){
                sb.append(" AND f.foodType_id=?");
                list.add(type_id);
            }
        }
        try {
            //执行查询，返回结果的第一行和第一列
            Long count = qr.query(sb.toString(),new ScalarHandler<Long>(),list.toArray());
            return count.intValue();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Food> findByType(int type) {
        try {
            //根据食物类型找到食物
             sql = "SELECT * FROM food WHERE foodType_id =?";
            return qr.query(sql,new BeanListHandler<Food>(Food.class),type);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
