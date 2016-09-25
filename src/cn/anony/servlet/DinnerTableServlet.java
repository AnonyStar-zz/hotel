package cn.anony.servlet;

import cn.anony.entity.DinnerTable;
import cn.anony.factory.BeanFactory;
import cn.anony.service.IDinnerTableService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by anony on 2016/9/21.
 */
@WebServlet(name = "DinnerTableServlet")
public class DinnerTableServlet extends HttpServlet {
    //工厂创建service
    private IDinnerTableService dinnerTableService = BeanFactory.getInstance("dinnerTableService",IDinnerTableService.class);
    private Object uri;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        List<DinnerTable> list = dinnerTableService.query();
        config.getServletContext().setAttribute("table",list);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        String method = request.getParameter("method");
        if ("add".equals(method)){
            add(request,response);
        }else if ("list".equals(method)){
            list(request,response);
        }else if ("update".equals(method)){
            update(request,response);
        }else if ("delete".equals(method)){
            delete(request,response);
        }else if ("search".equals(method)){
            search(request,response);
        }
    }

    //1.添加
    protected void add(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String tableName = request.getParameter("tableName");
        if (tableName != null){
            DinnerTable dinnerTable = new DinnerTable();
            dinnerTable.setTableName(tableName);
            dinnerTableService.add(dinnerTable);
            list(request,response);
        }
    }

    //2.餐桌列表展示
    protected void list(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            List<DinnerTable> list = dinnerTableService.query();
            request.setAttribute("list",list);
            request.getServletContext().setAttribute("table",list);
            uri = request.getRequestDispatcher("/sys/board/boardList.jsp");
        }catch (Exception e){
            e.printStackTrace();
            uri = "/error/error.jsp";
        }
        request.getRequestDispatcher("/sys/board/boardList.jsp").forward(request, response);
    }

    //3.餐桌更新
    protected void update(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        try {
            String id = request.getParameter("id");
            dinnerTableService.changeStatus(Integer.parseInt(id));
            list(request,response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //4.删除
    protected void delete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        try {
            String id = request.getParameter("id");
            dinnerTableService.delete(Integer.parseInt(id));
        }catch (Exception e){
            e.printStackTrace();
        }
        list(request,response);
    }

    //5.查询
    protected void search(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        try {
            String keyword = request.getParameter("keyword");
            if (keyword != null){
                List<DinnerTable> list = dinnerTableService.query(keyword);
                request.setAttribute("list",list);
                request.getRequestDispatcher("/sys/board/boardList.jsp").forward(request,response);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * t通用的跳转方法
     * @param request
     * @param response
     * @param uri
     * @throws ServletException
     * @throws IOException
     */
    private void goTO(HttpServletRequest request,HttpServletResponse response,Object uri)
            throws ServletException,IOException{
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
