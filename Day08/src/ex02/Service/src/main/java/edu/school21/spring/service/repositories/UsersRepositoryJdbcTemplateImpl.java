package edu.school21.spring.service.repositories;

import edu.school21.spring.service.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Component("usersRepositoryTemplate")
public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {

    private final JdbcTemplate jdbcTemplate;

    public UsersRepositoryJdbcTemplateImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional findById(Long id) {
        String SQL = "SELECT * FROM users WHERE id = " + id;
        return Optional.ofNullable(jdbcTemplate.query(SQL, new UserMapper()));
    }

    @Override
    public List findAll() {
        return jdbcTemplate.query("SELECT * FROM users", new UserMapper());
    }

    @Override
    public void save(User entity) {
        String SQL = "INSERT INTO users(email, password) VALUES (?, ?)";
        jdbcTemplate.update(SQL, entity.getEmail(), entity.getPassword());
    }

    @Override
    public void update(User entity) {
        String SQL = "UPDATE users SET email = ?, password = ? WHERE id = ?";
        jdbcTemplate.update(SQL, entity.getEmail(), entity.getPassword(), entity.getId());
    }

    @Override
    public void delete(Long id) {
        String SQL = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(SQL, id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String SQL = "SELECT * FROM users WHERE email = " + email;
        return Optional.of(jdbcTemplate.query(SQL, new UserMapper()).stream().findFirst().orElse(null));
    }
}
