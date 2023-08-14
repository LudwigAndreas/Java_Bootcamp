package edu.school21.chat.app;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.Room;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import javax.sql.DataSource;
import java.sql.Timestamp;
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
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(getDataSource());
        Optional<Message> messageOptional = messagesRepository.findById(7L);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setText("Bye");
            message.setDateTime((Timestamp) null);
            messagesRepository.update(message);
        }
    }
}
