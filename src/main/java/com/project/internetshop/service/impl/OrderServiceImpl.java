package com.project.internetshop.service.impl;

import com.project.internetshop.dao.OrderDao;
import com.project.internetshop.lib.Inject;
import com.project.internetshop.lib.Service;
import com.project.internetshop.model.Order;
import com.project.internetshop.model.Product;
import com.project.internetshop.model.User;
import com.project.internetshop.service.OrderService;
import com.project.internetshop.service.ShoppingCartService;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    private OrderDao orderDao;
    @Inject
    private ShoppingCartService shoppingCartService;

    @Override
    public Order completeOrder(List<Product> products, User user) {
        List<Product> productOrder = new ArrayList<>(products);
        shoppingCartService.clear(shoppingCartService.getByUserId(user.getId()));
        return orderDao.create(new Order(user, productOrder));
    }

    @Override
    public List<Order> getUserOrders(User user) {
        return orderDao.getAll().stream()
                .filter(o -> o.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Order get(Long id) {
        return orderDao.get(id)
                .orElseThrow(() -> new NoSuchElementException("Can't find order with id " + id));
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public boolean delete(Long id) {
        return orderDao.delete(id);
    }
}
