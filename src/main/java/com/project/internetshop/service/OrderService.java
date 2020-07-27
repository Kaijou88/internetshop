package com.project.internetshop.service;

import com.project.internetshop.model.Order;
import com.project.internetshop.model.Product;
import com.project.internetshop.model.User;
import java.util.List;

public interface OrderService extends GenericService<Order, Long> {
    Order completeOrder(List<Product> products, User user);

    List<Order> getUserOrders(User user);
}
