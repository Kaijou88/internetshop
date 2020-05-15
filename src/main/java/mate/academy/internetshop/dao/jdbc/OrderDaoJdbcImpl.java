package mate.academy.internetshop.dao.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exeptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.util.ConnectionUtil;
import org.apache.log4j.Logger;

@Dao
public class OrderDaoJdbcImpl implements OrderDao {
    private static final Logger LOGGER = Logger.getLogger(OrderDaoJdbcImpl.class);
    @Inject
    private UserDao userDao;

    @Override
    public Order create(Order element) {
        String query = "INSERT INTO orders (user_id) VALUE (?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, element.getUser().getId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            element.setId(resultSet.getLong(1));
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create new order");
        }
        insertToOrdersProducts(element);
        LOGGER.info("Order created with id: " + element.getId() + ". Exit from Order create()");
        return element;
    }

    @Override
    public Optional<Order> get(Long id) {
        String query = "SELECT * FROM orders WHERE order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getOrderFromResultSet(resultSet);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find order with id: " + id);
        }
    }

    @Override
    public List<Order> getAll() {
        String query = "SELECT * FROM orders";
        List<Order> orders = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet != null && resultSet.next()) {
                orders.add(getOrderFromResultSet(resultSet).get());
            }
            return orders;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't established connection to DB");
        }
    }

    @Override
    public Order update(Order element) {
        deleteFromOrdersProducts(element);
        insertToOrdersProducts(element);
        return element;
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM orders WHERE order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete order with id: " + id);
        }
    }

    public Optional<Order> getOrderFromResultSet(ResultSet resultSet)
            throws SQLException {
        long orderId = resultSet.getLong("order_id");
        long userId = resultSet.getLong("user_id");
        List<Product> products = getProductsForResultSet(orderId);
        Order order = new Order(userDao.get(userId).get(), products);
        order.setId(orderId);
        return Optional.of(order);
    }

    public List<Product> getProductsForResultSet(Long orderId) {
        String query = "SELECT products.product_id, products.product_name, products.product_price "
                + "FROM orders_products "
                + "INNER JOIN products "
                + "ON orders_products.product_id = products.product_id "
                + "WHERE orders_products.order_id = ?";

        List<Product> products = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderId);
            ResultSet resultSetOfProducts = statement.executeQuery();
            while (resultSetOfProducts.next()) {
                Long productId = resultSetOfProducts.getLong("product_id");
                String name = resultSetOfProducts.getString("product_name");
                BigDecimal price = resultSetOfProducts.getBigDecimal("product_price");
                Product product = new Product(name, price);
                product.setId(productId);
                products.add(product);
            }
            return products;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get products "
                    + "from order with id: " + orderId);
        }
    }

    public void deleteFromOrdersProducts(Order element) {
        String query = "DELETE FROM orders_products WHERE order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, element.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete products "
                    + "from order of user with id: " + element.getUser().getId());
        }
    }

    public void insertToOrdersProducts(Order element) {
        String query = "INSERT INTO orders_products (order_id, product_id) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            for (Product product : element.getProducts()) {
                statement.setLong(1, element.getId());
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't insert products "
                    + "to order of user with id: " + element.getUser().getId());
        }
    }
}
