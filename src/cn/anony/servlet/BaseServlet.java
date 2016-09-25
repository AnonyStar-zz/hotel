package cn.anony.servlet;

import cn.anony.factory.BeanFactory;
import cn.anony.service.IOrderDetailService;
import cn.anony.service.IOrdersService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 项目中通用的servlet，希望所有的servlet都继承此类
 * Created by anony on 2016/9/25.
 */
@WebServlet(name = "BaseServlet")
public class BaseServlet extends HttpServlet {

    //工厂创建service
    protected IOrdersService ordersService = BeanFactory.getInstance("ordersService",IOrdersService.class);
    protected IOrderDetailService orderDetailService = BeanFactory.getInstance("orderDetailService",IOrderDetailService.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //方法的返回值 ，保存跳转的资源
        Object returnValue = null;
        //获取操作类型；
        String methodName = request.getParameter("method");
        try {
            //当前运行类的字节码
            Class clazz = this.getClass();
           // 获取当前执行的方法的method类型
            Method method = clazz.getDeclaredMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
            returnValue = method.invoke(this,request,response);
        } catch (Exception e){
            e.printStackTrace();
            returnValue = "/error/error.jsp";
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}
