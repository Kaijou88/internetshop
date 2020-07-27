package com.project.internetshop.service;

import com.project.internetshop.model.Product;
import com.project.internetshop.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart addProduct(ShoppingCart shoppingCart, Product product);

    boolean deleteProduct(ShoppingCart shoppingCart, Product product);

    void clear(ShoppingCart shoppingCart);

    ShoppingCart getByUserId(Long userId);
}
