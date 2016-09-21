package cn.anony.servlet;

import cn.anony.entity.FoodType;
import cn.anony.factory.BeanFactory;
import cn.anony.service.IFoodTypeService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by anony on 2016/9/21.
 */
public class FoodTypeServlet extends HttpServlet {
    //调用菜系Service
    private IFoodTypeService foodTypeService = BeanFactory.getInstance("foodTypeService",IFoodTypeService.class);
   //跳转资源
    private Object uri;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        //获取操作的类型
        String method = req.getParameter("method");
        System.out.println(method);
        //判断
        if ("addFoodType".equals(method)){
            //1.添加菜系
            addFoodType(req,resp);

        }else if("list".equals(method)){
            //2.列表展示
            list(req,resp);

        }else if("viewUpdate".equals(method)){
            //3.进入更新页面
            viewUpdate(req,resp);

        }else if ("delete".equals(method)){
            //4.删除菜系
            delete(req,resp);
        }else if ("update".equals(method)){
            //5.更新菜系
            update(req,resp);
        }
    }

    //1.添加菜系
    public void addFoodType(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        try {
            //1.获取请求数据封装
            String foodTypeName = request.getParameter("foodTypeName");
            System.out.println(foodTypeName);
            FoodType foodType = new FoodType();
            foodType.setTypeName(foodTypeName);
            //2.调用service处理业务逻辑
            foodTypeService.save(foodType);
            //3.跳转
            uri = request.getRequestDispatcher("/foodType?method=list");
        }catch (Exception e){
            e.printStackTrace();
            uri = "/error/error.jsp";
        }
        goTO(request,response,uri);
    }

    //2.列表展示
    public void list(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        try {
            List<FoodType> list = foodTypeService.getAll();
            request.setAttribute("listFoodType",list);
            //跳转的菜系列表
            uri = request.getRequestDispatcher("/sys/type/foodtype_list.jsp");
        } catch (Exception e){
            e.printStackTrace();
            uri = "/error/erro.jsp";
        }
        goTO(request,response,uri);
    }

    //3.进入更新页面
    public void viewUpdate(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        try {
            //1.获取请求ID
            String id = request.getParameter("id");
            //2.根据id查询对象
            FoodType foodType = foodTypeService.findById(Integer.parseInt(id));
            //3.保存
            request.setAttribute("foodType",foodType);
            //4.跳转
            uri = request.getRequestDispatcher("/sys/type/foodtype_update.jsp");
        }catch (Exception e){
            e.printStackTrace();
            uri = "/error/error.jsp";
        }
        goTO(request,response,uri);
    }

    //4.删除
    public void delete(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        try {
            //1.获取请求数据
            String id = request.getParameter("id");
            foodTypeService.delete(Integer.parseInt(id));
            uri = "/foodType?method=list";
        }catch (Exception e){
            e.printStackTrace();
            uri = "/error/error.jsp";
        }
        goTO(request,response,uri);
    }

    //5.更新
    public void update(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
        try {
            //1.请求数据获取
            String id = request.getParameter("id");
            String name = request.getParameter("foodTypeName");
            FoodType foodType = new FoodType();
            foodType.setId(Integer.parseInt(id));
            foodType.setTypeName(name);
            //2.调用service更新方法
            foodTypeService.update(foodType);
            //3.跳转
            uri = request.getRequestDispatcher("/foodType?method=list");
        }catch (Exception e){
            e.printStackTrace();
            uri = "/error/error.jsp";
        }
        goTO(request,response,uri);
    }

        /**
         *跳转通用方法
         */
    private void goTO(HttpServletRequest request,HttpServletResponse response,Object uri) throws ServletException,IOException{
        if (uri instanceof RequestDispatcher){
            ((RequestDispatcher) uri).forward(request,response);
        }else if (uri instanceof String){
            response.sendRedirect(request.getContextPath()+uri);
        }
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
