package com.project.internetshop.controllers;

import com.project.internetshop.lib.Injector;
import com.project.internetshop.service.OrderService;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteOrderController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("com.project");
    private final OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String orderId = req.getParameter("id");
        Long id = Long.valueOf(orderId);
        orderService.delete(id);
        resp.sendRedirect(req.getContextPath() + "/orders/all");
    }
}
