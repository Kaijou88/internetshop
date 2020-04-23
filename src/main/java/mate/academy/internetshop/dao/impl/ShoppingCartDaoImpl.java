package mate.academy.internetshop.dao.impl;

import java.util.NoSuchElementException;
import mate.academy.internetshop.dao.ShoppingCartDao;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.ShoppingCart;

@Dao
public class ShoppingCartDaoImpl implements ShoppingCartDao {
    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        return null;
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        return null;
    }

    @Override
    public ShoppingCart get(Long shoppingCartId) {
        return Storage.shoppingCarts.stream()
                .filter(s -> s.getId().equals(shoppingCartId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't find shopping card with id "
                        + shoppingCartId));
    }
}
