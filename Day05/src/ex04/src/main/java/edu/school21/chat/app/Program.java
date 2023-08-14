package edu.school21.chat.app;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Room;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.UsersRepository;
import edu.school21.chat.repositories.impl.UsersRepositoryJdbcImpl;

import javax.sql.DataSource;
import java.util.List;

public class Program {

    public static DataSource getDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();

        hikariDataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/chatroom_app");
        hikariDataSource.setUsername("postgres");
        hikariDataSource.setPassword("");

        return hikariDataSource;
    }

    public static void main(String[] args) {

        UsersRepository usersRepository = new UsersRepositoryJdbcImpl(getDataSource());

        int page = 2;
        int size = 3;

        List<User> users = usersRepository.findAll(page, size);
        System.out.println("Users list size: " + users.size());

        for (User user : users) {
            System.out.println("User ID: " + user.getId());
            System.out.println("\tLogin: " + user.getLogin());
            System.out.println("\tPassword: " + user.getPassword());

            System.out.println("\tCreated Chatrooms:");
            for (Room chatroom : user.getCreatedChatRooms()) {
                System.out.println("\t\tChatroom ID: " + chatroom.getId());
                System.out.println("\t\tChatroom Name: " + chatroom.getName());
            }

            System.out.println("\tParticipating Chatrooms:");
            for (Room chatroom : user.getSocializes()) {
                System.out.println("\t\tChatroom ID: " + chatroom.getId());
                System.out.println("\t\tChatroom Name: " + chatroom.getName());
            }

            System.out.println();
        }
    }
}
