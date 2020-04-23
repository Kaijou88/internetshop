package mate.academy.internetshop.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;

public class Application {
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);

        initializeDb(productService);

        List<Product> allProducts = productService.getAll();
        for (Product product : allProducts) {
            System.out.println(product.toString());
        }
    }

    private static void initializeDb(ProductService productService) {
        Product product1 = new Product("Book", new BigDecimal("150"));
        Product product2 = new Product("Sketchbook", new BigDecimal("250"));
        Product product3 = new Product("Notebook", new BigDecimal("100"));

        productService.create(product1);
        productService.create(product2);
        productService.create(product3);
    }
}
