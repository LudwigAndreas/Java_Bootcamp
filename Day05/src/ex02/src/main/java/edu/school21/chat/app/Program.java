package edu.school21.chat.app;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.Room;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class Program {

    public static DataSource getDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();

        hikariDataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/chatroom_app");
        hikariDataSource.setUsername("postgres");
        hikariDataSource.setPassword("");

        return hikariDataSource;
    }

    public static void main(String[] args) {
        User creator = new User(3L, "user", "password", new ArrayList<>(), new ArrayList<>());
        User author = creator;
        Room room = new Room(2L, "room", creator, new ArrayList<>());
        Message message = new Message(null, author, room, "Hello!", LocalDateTime.now());
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(getDataSource());
        messagesRepository.save(message);
        System.out.println(message.getId());
    }
}
