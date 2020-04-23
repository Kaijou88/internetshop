package mate.academy.internetshop.service;

import mate.academy.internetshop.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCart addItem(Long shoppingCartId, Long productId);
}
