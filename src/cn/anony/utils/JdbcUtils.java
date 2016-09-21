package cn.anony.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;

/**
 * 封装常用的jdbc操作
 * Created by anony on 2016/9/21.
 */
public class JdbcUtils {
    //1.初始化连接池
    private static DataSource dataSource;
    static {
        dataSource = new ComboPooledDataSource();
    }
    public  static DataSource getDataSource(){
        return dataSource;
    }

    /**
     * 创建DbUtils常用工具对象
     */
    public static QueryRunner getQueryRunner(){
        return new QueryRunner(dataSource);
    }
}
