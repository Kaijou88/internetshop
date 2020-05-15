package mate.academy.internetshop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.ShoppingCartService;
import mate.academy.internetshop.service.UserService;
import org.apache.log4j.Logger;

public class CompleteOrderController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(CompleteOrderController.class);
    private static final String ID_USER = "user_id";
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);
    private final OrderService orderService =
            (OrderService) INJECTOR.getInstance(OrderService.class);
    private final UserService userService =
            (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/shoppingCart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Long userId = (Long) req.getSession().getAttribute(ID_USER);
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(userId);
        if (shoppingCart.getProducts().isEmpty()) {
            req.setAttribute("message",
                    "You can't finish your order. Your shopping cart is empty.");
            LOGGER.info("User with id: "
                    + userId + " tries to finish order with empty shopping cart.");
            req.getRequestDispatcher("/WEB-INF/views/shoppingCart.jsp").forward(req, resp);
            return;
        }
        Order order = orderService.completeOrder(
                shoppingCart.getProducts(), userService.get(userId));
        shoppingCartService.clear(shoppingCart);
        resp.sendRedirect(req.getContextPath() + "/orders/details?id=" + order.getId());
    }
}
