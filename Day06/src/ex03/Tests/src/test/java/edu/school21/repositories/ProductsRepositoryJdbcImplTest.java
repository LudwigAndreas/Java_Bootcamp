package edu.school21.repositories;

import edu.school21.exceptions.DatabaseException;
import edu.school21.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImplTest {
    private DataSource dataSource;
    private ProductsRepositoryJdbcImpl productsRepositoryJdbc;

    private final List<Product> ALL_PRODUCTS = Arrays.asList(
            new Product(0L, "orange", 95),
            new Product(1L, "grape", 120),
            new Product(2L, "pear", 85),
            new Product(3L, "kiwi", 70),
            new Product(4L, "blueberry", 130),
            new Product(5L, "pineapple", 160),
            new Product(6L, "mango", 110),
            new Product(7L, "pomegranate", 140),
            new Product(8L, "coconut", 180),
            new Product(9L, "raspberry", 95),
            new Product(10L, "apricot", 75),
            new Product(11L, "plum", 90),
            new Product(12L, "cantaloupe", 105),
            new Product(13L, "fig", 100),
            new Product(14L, "guava", 120)
    );

    private final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(0L, "orange", 95);
    private final Product EXPECTED_UPDATED_PRODUCT = new Product(0L, "orange", 100);
    private final Product EXPECTED_SAVED_PRODUCT = new Product(15L, "lemon", 90);

    @BeforeEach
    void init() {
        dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        productsRepositoryJdbc = new ProductsRepositoryJdbcImpl(dataSource);
    }

    @Test
    void findAllTest() {
        List<Product> actual = productsRepositoryJdbc.findAll();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(actual.size(), ALL_PRODUCTS.size());
        Assertions.assertTrue(actual.containsAll(ALL_PRODUCTS));
    }

    @Test
    void findByIdTest() {
        Optional<Product> actual = productsRepositoryJdbc.findById(0L);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(actual.get(), EXPECTED_FIND_BY_ID_PRODUCT);
    }

    @Test
    void updateTest() {
        productsRepositoryJdbc.update(EXPECTED_UPDATED_PRODUCT);
        Optional<Product> actual = productsRepositoryJdbc.findById(0L);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(actual.get(), EXPECTED_UPDATED_PRODUCT);
    }

    @Test
    void saveTest() {
        productsRepositoryJdbc.save(EXPECTED_SAVED_PRODUCT);
        Optional<Product> actual = productsRepositoryJdbc.findById(15L);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(actual.get(), EXPECTED_SAVED_PRODUCT);
    }

    @Test
    void deleteTest() {
        productsRepositoryJdbc.delete(1L);
        Optional<Product> actual = productsRepositoryJdbc.findById(1L);
        Assertions.assertFalse(actual.isPresent());
    }

}
