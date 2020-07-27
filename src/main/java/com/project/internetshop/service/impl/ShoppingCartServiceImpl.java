package com.project.internetshop.service.impl;

import com.project.internetshop.dao.ShoppingCartDao;
import com.project.internetshop.lib.Inject;
import com.project.internetshop.lib.Service;
import com.project.internetshop.model.Product;
import com.project.internetshop.model.ShoppingCart;
import com.project.internetshop.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Inject
    private ShoppingCartDao shoppingCartDao;

    @Override
    public ShoppingCart addProduct(ShoppingCart shoppingCart, Product product) {
        shoppingCart.getProducts().add(product);
        return update(shoppingCart);
    }

    @Override
    public boolean deleteProduct(ShoppingCart shoppingCart, Product product) {
        if (shoppingCart.getProducts().contains(product)) {
            shoppingCart.getProducts().remove(product);
            update(shoppingCart);
            return true;
        }
        return false;
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.getProducts().clear();
        update(shoppingCart);
    }

    @Override
    public ShoppingCart getByUserId(Long userId) {
        return shoppingCartDao.getCartByUid(userId);
    }

    private ShoppingCart update(ShoppingCart shoppingCart) {
        return shoppingCartDao.update(shoppingCart);
    }
}
