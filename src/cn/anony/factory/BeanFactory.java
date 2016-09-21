package cn.anony.factory;

import java.util.ResourceBundle;

/**
 * 工厂：创建dao或service实例
 * Created by anony on 2016/9/21.
 */
public class BeanFactory {
    //加载配置文件
    private static ResourceBundle bundle;
    static {
        bundle = ResourceBundle.getBundle("instance");
    }

    public static <T> T getInstance(String key,Class<T> clazz){
        String className = bundle.getString(key);
        try {
            return (T) Class.forName(className).newInstance();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
