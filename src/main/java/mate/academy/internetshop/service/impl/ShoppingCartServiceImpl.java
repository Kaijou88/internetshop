package mate.academy.internetshop.service.impl;

import java.util.NoSuchElementException;
import mate.academy.internetshop.dao.ProductDao;
import mate.academy.internetshop.dao.ShoppingCartDao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Inject
    private ShoppingCartDao shoppingCartDao;
    private ProductDao productDao;

    @Override
    public ShoppingCart addItem(Long shoppingCartId, Long productId) {
        ShoppingCart shoppingCart = shoppingCartDao.get(shoppingCartId);
        Product product = productDao.get(shoppingCartId)
                .orElseThrow(() -> new NoSuchElementException("Can't find"));
        shoppingCart.getProducts().add(product);
        return shoppingCartDao.update(shoppingCart);
    }
}
