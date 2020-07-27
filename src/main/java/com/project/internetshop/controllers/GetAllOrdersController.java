package com.project.internetshop.controllers;

import com.project.internetshop.lib.Injector;
import com.project.internetshop.model.Order;
import com.project.internetshop.service.OrderService;
import com.project.internetshop.service.UserService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetAllOrdersController extends HttpServlet {
    private static final String ID_USER = "user_id";
    private static final Injector INJECTOR = Injector.getInstance("com.project");
    private final OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);
    private final UserService userService =
            (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Long userId = (Long) req.getSession().getAttribute(ID_USER);
        List<Order> allOrders;
        if (userService.get(userId).getRoles().stream()
                .anyMatch(r -> r.getRoleName().name().equals("ADMIN"))) {
            allOrders = orderService.getAll();
        } else {
            allOrders = orderService.getUserOrders(userService.get(userId));
        }
        req.setAttribute("order", allOrders);
        req.getRequestDispatcher("/WEB-INF/views/orders/all.jsp").forward(req, resp);
    }
}
