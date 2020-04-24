package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.model.ShoppingCart;

public interface ShoppingCartDao {
    ShoppingCart create(ShoppingCart shoppingCart);

    boolean delete(Long id);

    ShoppingCart update(ShoppingCart shoppingCart);

    List<ShoppingCart> getAll();

    Optional<ShoppingCart> get(Long id);
}
