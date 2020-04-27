package mate.academy.internetshop.service;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.model.User;

public class Application {
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");

    public static void main(String[] args) {
        ProductService productService =
                (ProductService) injector.getInstance(ProductService.class);
        Product product1 = new Product("Book", new BigDecimal("150"));
        Product product2 = new Product("Sketchbook", new BigDecimal("250"));
        Product product3 = new Product("Notebook", new BigDecimal("100"));
        productService.create(product1);
        productService.create(product2);
        productService.create(product3);
        List<Product> allProducts = productService.getAll();
        System.out.println("All our products: ");
        for (Product product : allProducts) {
            System.out.println(product.toString());
        }

        UserService userService = (UserService) injector.getInstance(UserService.class);
        User user1 = new User("Tom", "tom", "qwerty");
        User user2 = new User("Tom", "tom", "qwerty");
        userService.create(user1);
        userService.create(user2);
        System.out.println("\nAll our users: " + userService.getAll());
        userService.delete((long) 1);
        System.out.println("\nAll our users: " + userService.getAll());

        ShoppingCartService shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        ShoppingCart cart = shoppingCartService.getByUserId(user2.getId());
        shoppingCartService.addProduct(cart, product1);
        System.out.println("\nOur shopping cart: " + shoppingCartService.getAllProducts(cart));

        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);
        Order order = new Order(user2, cart.getProducts());
        orderService.completeOrder(cart.getProducts(), user2);
        System.out.println("\nOur order " + orderService.getUserOrders(user2));
    }

}
