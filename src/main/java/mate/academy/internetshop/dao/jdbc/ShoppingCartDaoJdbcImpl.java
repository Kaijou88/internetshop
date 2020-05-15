package mate.academy.internetshop.dao.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.ShoppingCartDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exeptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.util.ConnectionUtil;
import org.apache.log4j.Logger;

@Dao
public class ShoppingCartDaoJdbcImpl implements ShoppingCartDao {
    private static final Logger LOGGER = Logger.getLogger(ShoppingCartDaoJdbcImpl.class);
    @Inject
    private UserDao userDao;

    @Override
    public ShoppingCart create(ShoppingCart element) {
        String query = "INSERT INTO shopping_carts (user_id) VALUE (?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, element.getUser().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            element.setId(resultSet.getLong(1));
            LOGGER.info("Shopping cart creat with id: " + element.getId());
            return element;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create new shopping cart");
        }
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        String query = "SELECT * FROM shopping_carts WHERE cart_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getShoppingCartFromResultSet(resultSet);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find shopping cart with id: " + id);
        }
    }

    @Override
    public List<ShoppingCart> getAll() {
        String query = "SELECT * FROM shopping_carts";
        List<ShoppingCart> shoppingCarts = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet != null && resultSet.next()) {
                shoppingCarts.add(getShoppingCartFromResultSet(resultSet).get());
            }
            return shoppingCarts;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't established connection to DB");
        }
    }

    @Override
    public ShoppingCart getCartByUid(Long uid) {
        String query = "SELECT cart_id, user_id FROM shopping_carts WHERE user_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, uid);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getShoppingCartFromResultSet(resultSet).get();
            }
            return create(new ShoppingCart(userDao.get(uid).get()));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't established connection to DB");
        }
    }

    @Override
    public ShoppingCart update(ShoppingCart element) {
        deleteFromShoppingCartsProducts(element);
        insertToShoppingCartsProducts(element);
        return element;
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM shopping_carts WHERE cart_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete shopping cart with id: " + id);
        }
    }

    public Optional<ShoppingCart> getShoppingCartFromResultSet(ResultSet resultSet)
            throws SQLException {
        long shoppingCartId = resultSet.getLong("cart_id");
        long userId = resultSet.getLong("user_id");
        ShoppingCart shoppingCart = new ShoppingCart(userDao.get(userId).get());
        shoppingCart.setId(shoppingCartId);
        String query = "SELECT products.product_id, products.product_name, products.product_price "
                + "FROM shopping_carts_products "
                + "INNER JOIN products "
                + "ON shopping_carts_products.product_id = products.product_id "
                + "WHERE shopping_carts_products.cart_id = ?";

        List<Product> products = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, shoppingCartId);
            ResultSet resultSetOfProducts = statement.executeQuery();
            while (resultSetOfProducts.next()) {
                Long productId = resultSetOfProducts.getLong("product_id");
                String name = resultSetOfProducts.getString("product_name");
                BigDecimal price = resultSetOfProducts.getBigDecimal("product_price");

                Product product = new Product(name, price);
                product.setId(productId);
                products.add(product);
            }
        }
        shoppingCart.setProducts(products);
        return Optional.of(shoppingCart);
    }

    public void deleteFromShoppingCartsProducts(ShoppingCart element) {
        String query = "DELETE FROM shopping_carts_products WHERE cart_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, element.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete products "
                    + "from shopping cart of user with id: " + element.getUser().getId());
        }
    }

    public void insertToShoppingCartsProducts(ShoppingCart element) {
        String query = "INSERT INTO shopping_carts_products (cart_id, product_id) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            for (Product product : element.getProducts()) {
                statement.setLong(1, element.getId());
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert products "
                    + "to shopping cart of user with id: " + element.getUser().getId());
        }
    }
}
