package com.project.internetshop.controllers;

import com.project.internetshop.lib.Injector;
import com.project.internetshop.model.Product;
import com.project.internetshop.model.ShoppingCart;
import com.project.internetshop.service.ProductService;
import com.project.internetshop.service.ShoppingCartService;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddProductToShoppingCartController extends HttpServlet {
    private static final String ID_USER = "user_id";
    private static final Injector INJECTOR = Injector.getInstance("com.project");
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) INJECTOR.getInstance(ShoppingCartService.class);
    private final ProductService productService =
            (ProductService) INJECTOR.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String productId = req.getParameter("id");
        Long id = Long.valueOf(productId);
        Long userId = (Long) req.getSession().getAttribute(ID_USER);
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(userId);
        Product product = productService.get(id);
        shoppingCartService.addProduct(shoppingCart, product);
        resp.sendRedirect(req.getContextPath() + "/products/for_user");
    }
}
