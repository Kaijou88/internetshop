package com.project.internetshop.controllers;

import com.project.internetshop.lib.Injector;
import com.project.internetshop.service.OrderService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetOrderController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("com.project");
    private final OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String orderId = req.getParameter("id");
        Long id = Long.valueOf(orderId);
        req.setAttribute("products", orderService.get(id).getProducts());
        req.getRequestDispatcher("/WEB-INF/views/orders/order.jsp").forward(req, resp);
    }
}
