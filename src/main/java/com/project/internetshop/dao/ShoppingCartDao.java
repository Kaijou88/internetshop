package com.project.internetshop.dao;

import com.project.internetshop.model.ShoppingCart;

public interface ShoppingCartDao extends GenericDao<ShoppingCart, Long> {
    ShoppingCart getCartByUid(Long uid);
}
