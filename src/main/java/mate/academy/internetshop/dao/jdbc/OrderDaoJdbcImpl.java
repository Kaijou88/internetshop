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
import mate.academy.internetshop.exeptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.service.UserService;
import mate.academy.internetshop.util.ConnectionUtil;
import org.apache.log4j.Logger;

@Dao
public class OrderDaoJdbcImpl implements OrderDao {
    private static final Logger LOGGER = Logger.getLogger(OrderDaoJdbcImpl.class);
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private final UserService userService =
            (UserService) INJECTOR.getInstance(UserService.class);

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
            insertToOrdersProducts(element);
            LOGGER.info("Order created with id: " + element.getId() + ". Exit from Order create()");
            return element;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create new order");
        }
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
        String query1 = "UPDATE orders SET user_id = ? WHERE order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query1);
            statement.setLong(1, element.getUser().getId());
            statement.setLong(2, element.getId());
            statement.executeUpdate();
            deleteFromOrdersProducts(element);
            insertToOrdersProducts(element);
            return element;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update product "
                    + "in shopping cart of user with id: " + element.getUser().getId());
        }
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
        String query = "SELECT products.product_id, products.product_name, products.product_price "
                + "FROM orders_products "
                + "INNER JOIN products "
                + "ON orders_products.product_id = products.product_id "
                + "WHERE orders_products.order_id = ?";

        List<Product> products = new ArrayList<>();
        Connection connection = ConnectionUtil.getConnection();
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

        Order order = new Order(userService.get(userId), products);
        order.setId(orderId);
        return Optional.of(order);
    }

    public void deleteFromOrdersProducts(Order element) {
        String query2 = "DELETE FROM orders_products WHERE order_id = ?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query2);
            statement.setLong(1, element.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete products "
                    + "from order of user with id: " + element.getUser().getId());
        }
    }

    public void insertToOrdersProducts(Order element) {
        String query3 = "INSERT INTO orders_products (order_id, product_id) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query3);
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
