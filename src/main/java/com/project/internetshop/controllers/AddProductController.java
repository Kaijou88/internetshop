package com.project.internetshop.controllers;

import com.project.internetshop.lib.Injector;
import com.project.internetshop.model.Product;
import com.project.internetshop.service.ProductService;
import java.io.IOException;
import java.math.BigDecimal;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddProductController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("com.project");
    private final ProductService productService =
            (ProductService) INJECTOR.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/addProduct.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String name = req.getParameter("name");
        String productPrice = req.getParameter("price");
        BigDecimal price = BigDecimal.valueOf(Long.parseLong(productPrice));
        Product product = new Product(name, price);
        productService.create(product);
        resp.sendRedirect(req.getContextPath() + "/products/add_new");
    }
}
