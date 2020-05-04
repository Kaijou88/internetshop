package mate.academy.internetshop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.service.ShoppingCartService;

public class GetShoppingCartController extends HttpServlet {
    private static final String ID_USER = "user_id";
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Long userId = (Long) req.getSession().getAttribute(ID_USER);
        req.setAttribute("shoppingCart", shoppingCartService.getByUserId(userId));
        req.getRequestDispatcher("WEB-INF/views/shoppingCart.jsp").forward(req, resp);
    }
}
