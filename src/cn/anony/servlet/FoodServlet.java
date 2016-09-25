package cn.anony.servlet;

import cn.anony.entity.Food;
import cn.anony.entity.FoodType;
import cn.anony.factory.BeanFactory;
import cn.anony.service.IFoodService;
import cn.anony.service.IFoodTypeService;
import cn.anony.utils.PageBean;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by anony on 2016/9/22.
 */
@WebServlet(name = "FoodServlet")
public class FoodServlet extends HttpServlet {
    //工厂创建service层对象
    private IFoodService foodService = BeanFactory.getInstance("foodService",IFoodService.class);
    private IFoodTypeService foodTypeService = BeanFactory.getInstance("foodTypeService",IFoodTypeService.class);
    private Object uri;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        PageBean<Food> pageBean = new PageBean<Food>();
        pageBean.setPageCount(6);
        foodService.getAll(pageBean);
        List<Food> list = foodService.query();
        config.getServletContext().setAttribute("food",list);
        config.getServletContext().setAttribute("pb",pageBean);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String method = request.getParameter("method");
        if ("list".equals(method)){
            list(request,response);
        }else if ("addFood".equals(method)){
            save(request,response);
        }else if ("viewUpdate".equals(method)){
            //进入更新页面
            viewUpdate(request,response);
        }else if ("update".equals(method)){
            update(request,response);
        }else if ("delete".equals(method)){
            delete(request,response);
        } else if ("findFoodType".equals(method)) {
            findFoodType(request,response);
            uri = request.getRequestDispatcher("/sys/food/saveFood.jsp");
            goTo(request,response,uri);
        }
    }

    //菜系的列表获取
    protected void findFoodType(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        List<FoodType> list = foodTypeService.getAll();
        request.setAttribute("foodtypes",list);
    }
    //1.列表展示
    protected void list(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        try {
            //1.获取当前页参数；（第一次访问当前页为null
            String currPage = request.getParameter("currentPage");
            //判断
            if (currPage == null || "".equals(currPage.trim())){
                currPage = "1"; //第一次访问，设置当前页为1
            }
            //转换
            int currentPage = Integer.parseInt(currPage);
            //2.创建PageBean对象，设置当前页的参数：传入service方法参数
            PageBean<Food> pageBean = new PageBean<Food>();
            pageBean.setCurrentPage(currentPage);
            //3.调用service
            foodService.getAll(pageBean);  //pagebean已经被dao填充了数据
           //4.保存pagebean对象，到reques域中
            List<Food> list = pageBean.getPageData();
            //活的食物类别的方法
            List<FoodType> types = new ArrayList<FoodType>();

            if (list != null){
                for (Food food:list){
                    FoodType foodType = foodTypeService.findById(food.getFoodType_id());
                    types.add(foodType);
                }
            }
            request.setAttribute("types",types);
            request.setAttribute("pageBean",pageBean);
            request.setAttribute("list",list);
           uri = request.getRequestDispatcher("/sys/food/foodList.jsp");
        }catch (Exception e){
            e.printStackTrace();
          uri = "/error/error.jsp";
        }
        goTo(request,response,uri);
    }

    //2.添加
    protected void save(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        try {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(10*1024*1024);    //单个文件的大小
            upload.setSizeMax(50*1024*1024);     //总文件大小限制
            upload.setHeaderEncoding("utf-8");  //对中文文件编码的处理

            if (upload.isMultipartContent(request)){
                Food food = new Food();
                List<FileItem> list = upload.parseRequest(request);
                for (FileItem item : list){
                    if (item.isFormField()){    //普通文本内容
                        String name = item.getFieldName();
                        //获取值
                        String value = item.getString();
                        value = new String(value.getBytes("ISO-8859-1"),"UTF-8");
                        BeanUtils.setProperty(food,name,value);
                    }else {     //上传内容
                        String fileName = item.getFieldName();
                        String path = getServletContext().getRealPath("/upload");
                        File f = new File(path);
                        if (!f.exists()){
                            f.mkdir();
                        }
                        //全部绝对路径
                        String name = item.getName();
                        BeanUtils.setProperty(food,fileName,"upload/"+name);
                        //拼接文件名
                        File file = new File(path,name);
                        //上传
                        if (!file.isDirectory()){
                            item.write(file);
                        }
                        item.delete();
                    }
                }
                foodService.save(food);
            }
            list(request,response);
        }catch (Exception e){
            e.printStackTrace();
            response.sendRedirect("/error/error.jsp");
            goTo(request,response,uri);
        }
    }

    //3.进入更新页面
    protected void viewUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        try {
            findFoodType(request,response);
            String id = request.getParameter("id");
            Food food = new Food();
            food = foodService.findById(Integer.parseInt(id));
            request.setAttribute("food",food);
            //得到食物里面的食物类型ID
            int foodType_id = food.getFoodType_id();
            FoodType type = foodTypeService.findById(foodType_id);
            request.setAttribute("type",type);
            uri = request.getRequestDispatcher("/sys/food/updateFood.jsp");
        }catch (Exception e){
            e.printStackTrace();
            response.sendRedirect("/error/error.jsp");
        }
        goTo(request,response,uri);
    }

    //4.更新
    protected void update(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        try {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(50*1024*1024);
            upload.setFileSizeMax(10*1024*1024);
            upload.setHeaderEncoding("UTF-8");
            if (upload.isMultipartContent(request)){
                Food food = new Food();
                List<FileItem> list = upload.parseRequest(request);
                for (FileItem item : list){
                    if (item.isFormField()){
                        String name = item.getFieldName();
                        String value = item.getString();
                        value = new String(value.getBytes("ISO-8859-1"),"UTF-8");
                        BeanUtils.setProperty(food,name,value);
                    }else {
                        String fileName = item.getFieldName();
                        String path = getServletContext().getRealPath("/upload");
                        File f = new File(path);
                        if (!f.exists()){
                            f.mkdir();
                        }
                        String name = item.getName();
                        if (name != null && !"".equals(name.trim())){
                            BeanUtils.setProperty(food,fileName,"upload/"+name);
                            File file = new File(path,name);
                            if (!file.isDirectory()){
                                item.write(file);
                            }
                            item.delete();
                        }else {
                            int id = food.getId();
                            String img = foodService.findById(id).getImg();
                            BeanUtils.setProperty(food,"img",img);
                        }
                    }
                }
                foodService.update(food);
            }
            list(request,response);
        }catch (Exception e){
            e.printStackTrace();
            response.sendRedirect("/error/error.jsp");
            goTo(request,response,uri);
        }
    }

    //5.删除
    protected void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        try {
            String id = request.getParameter("id");
            foodService.delete(Integer.parseInt(id));
            list(request,response);
        }catch (Exception e){
            e.printStackTrace();
            response.sendRedirect("/error/error.jsp");
            goTo(request,response,uri);
        }
    }



    //通用的跳转方法
    protected void goTo(HttpServletRequest request, HttpServletResponse response,Object uri)
            throws ServletException, IOException{
        if (uri instanceof RequestDispatcher){
            ((RequestDispatcher) uri).forward(request,response);
        }else if (uri instanceof String){
            response.sendRedirect(request.getContextPath() + uri);
        }
    }


        @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
        this.doPost(request,response);
    }
}
