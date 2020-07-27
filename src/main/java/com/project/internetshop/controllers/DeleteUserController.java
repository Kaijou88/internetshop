package com.project.internetshop.controllers;

import com.project.internetshop.lib.Injector;
import com.project.internetshop.model.Order;
import com.project.internetshop.service.OrderService;
import com.project.internetshop.service.UserService;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUserController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("com.project");
    private final UserService userService = (UserService) INJECTOR.getInstance(UserService.class);
    private final OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String userId = req.getParameter("id");
        Long id = Long.valueOf(userId);
        List<Order> orders = orderService.getUserOrders(userService.get(id));
        for (Order order : orders) {
            orderService.delete(order.getId());
        }
        userService.delete(id);
        resp.sendRedirect(req.getContextPath() + "/users/all");
    }
}
