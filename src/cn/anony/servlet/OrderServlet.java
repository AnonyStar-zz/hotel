package cn.anony.servlet;

import cn.anony.entity.OrderDetail;
import cn.anony.entity.Orders;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.util.List;

/**
 * Created by anony on 2016/9/25.
 */
@WebServlet(name = "OrderServlet")
public class OrderServlet extends BaseServlet {

    @Override
    public void init() throws ServletException {
        List<Orders> orders = ordersService.query();
        List<OrderDetail> orderDetail = orderDetailService.query();
        this.getServletContext().setAttribute("orders",orders);
        this.getServletContext().setAttribute("orderDetail",orderDetail);
    }

}
