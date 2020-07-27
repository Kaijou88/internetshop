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

public class InjectProductsController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("com.project");
    private final ProductService productService =
            (ProductService) INJECTOR.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Product product1 = new Product("Mouse Hunt Cat Toy", BigDecimal.valueOf(38));
        Product product2 = new Product("Cat Grooming Glove", BigDecimal.valueOf(9));
        Product product3 = new Product("Smart Laser Teaser Ball", BigDecimal.valueOf(22));
        Product product4 = new Product("Feather Duster Cat Wand Toy", BigDecimal.valueOf(8));
        Product product5 = new Product("Fish Cat Hanging Scratcher", BigDecimal.valueOf(10));
        Product product6 = new Product("Milton Pinot Meow Cat Wine", BigDecimal.valueOf(9));
        Product product7 = new Product("Custom Pet Bed", BigDecimal.valueOf(111));
        Product product8 = new Product("Modern Wave Scratching Post", BigDecimal.valueOf(349));
        Product product9 = new Product("Chubby Hamster Cat Toy", BigDecimal.valueOf(15));
        Product product10 = new Product("Fluffy mouse", BigDecimal.valueOf(15));
        Product product11 = new Product("Automatic Laser Cat Toy", BigDecimal.valueOf(43));

        productService.create(product1);
        productService.create(product2);
        productService.create(product3);
        productService.create(product4);
        productService.create(product5);
        productService.create(product6);
        productService.create(product7);
        productService.create(product8);
        productService.create(product9);
        productService.create(product10);
        productService.create(product11);

        req.getRequestDispatcher("/WEB-INF/views/injectProducts.jsp").forward(req, resp);
    }
}
