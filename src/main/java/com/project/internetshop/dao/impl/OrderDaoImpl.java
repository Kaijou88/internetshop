package com.project.internetshop.dao.impl;

import com.project.internetshop.dao.OrderDao;
import com.project.internetshop.dao.Storage;
import com.project.internetshop.model.Order;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class OrderDaoImpl implements OrderDao {

    @Override
    public Order create(Order order) {
        Storage.addOrder(order);
        return order;
    }

    @Override
    public Optional<Order> get(Long id) {
        return Storage.orders.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Order> getAll() {
        return Storage.orders;
    }

    @Override
    public Order update(Order order) {
        IntStream.range(0, Storage.orders.size())
                .filter(o -> order.getId().equals(Storage.orders.get(o).getId()))
                .forEach(o -> Storage.orders.set(o, order));
        return order;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.orders.removeIf(o -> o.getId().equals(id));
    }
}
