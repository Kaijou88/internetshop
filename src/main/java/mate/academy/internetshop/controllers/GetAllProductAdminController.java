package mate.academy.internetshop.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.service.ProductService;

public class GetAllProductAdminController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private final ProductService productService =
            (ProductService) INJECTOR.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Product> allProduct = productService.getAll();
        req.setAttribute("products", allProduct);
        req.getRequestDispatcher("/WEB-INF/views/products/admin.jsp").forward(req, resp);
    }
}
