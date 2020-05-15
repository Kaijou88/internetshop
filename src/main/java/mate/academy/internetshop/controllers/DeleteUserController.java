package mate.academy.internetshop.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

public class DeleteUserController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private final UserService userService = (UserService) INJECTOR.getInstance(UserService.class);
    private final OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

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
