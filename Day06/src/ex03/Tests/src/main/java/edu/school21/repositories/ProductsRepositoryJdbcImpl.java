package edu.school21.repositories;

import edu.school21.exceptions.DatabaseException;
import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private static final String SQL_FIND_ALL = "SELECT * FROM products";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM products WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE products SET name = ?, price = ? WHERE id = ?";
    private static final String SQL_INSERT = "INSERT INTO products (name, price) VALUES (?, ?)";
    private static final String SQL_DELETE = "DELETE FROM products WHERE id = ?";

    private DataSource dataSource;

    public ProductsRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL);
                ) {
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price")
                );
                products.add(product);
            }
        } catch (SQLException throwables) {
            throw new DatabaseException(throwables);
        }
        return products;
    }

    @Override
    public Optional<Product> findById(Long id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_FIND_BY_ID);
            ) {
            statement.setLong(1, id);
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            if (resultSet.next()) {
                Product product = new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price")
                );
                return Optional.of(product);
            }
        } catch (SQLException throwables) {
            throw new DatabaseException(throwables);
        }
        return Optional.empty();
    }

    @Override
    public void update(Product product) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);
            ) {
            statement.setString(1, product.getName());
            statement.setInt(2, product.getPrice());
            statement.setLong(3, product.getId());
            statement.execute();
        } catch (SQLException throwables) {
            throw new DatabaseException(throwables);
        }
    }

    @Override
    public void save(Product product) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_INSERT);
            ) {
            statement.setString(1, product.getName());
            statement.setInt(2, product.getPrice());
            statement.execute();
        } catch (SQLException throwables) {
            throw new DatabaseException(throwables);
        }
    }

    @Override
    public void delete(Long id) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_DELETE);
            ) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException throwables) {
            throw new DatabaseException(throwables);
        }
    }
}
