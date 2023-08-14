package edu.school21.chat.app;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import javax.sql.DataSource;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class Program {

    public static DataSource getDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();

        hikariDataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/chatroom_app");
        hikariDataSource.setUsername("postgres");
        hikariDataSource.setPassword("");

        return hikariDataSource;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Optional<Message> message;
        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(getDataSource());

        System.out.println("Enter a message ID ");
        System.out.print("-> ");
        try {
            Long id = scanner.nextLong();
            message = messagesRepository.findById(id);
        } catch (InputMismatchException e) {
            System.out.println("Invalid ID");
            return;
        }
        if (message.isPresent()) {
            System.out.println(message.get());
        } else {
            System.out.println("Message not found");
        }
    }
}
