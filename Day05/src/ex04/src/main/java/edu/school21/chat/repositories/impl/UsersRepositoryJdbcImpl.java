package edu.school21.chat.repositories.impl;

import edu.school21.chat.exceptions.DatabaseException;
import edu.school21.chat.models.Room;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.UsersRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private static final String SQL_FIND_ALL_USERS = "SELECT * FROM users LIMIT ? OFFSET ?";
    private static final String SQL_FIND_ALL_USERS_WITH_ROOMS = "" +
            "WITH user_pagination AS (\n" +
            "    SELECT\n" +
            "        id,\n" +
            "        login,\n" +
            "        password,\n" +
            "        ROW_NUMBER() OVER (ORDER BY id) AS row_num\n" +
            "    FROM users\n" +
            "),\n" +
            "     UserChats AS (\n" +
            "         SELECT\n" +
            "             u.id as user_id,\n" +
            "             c.id as chat_id,\n" +
            "             c.name,\n" +
            "             c.owner_id\n" +
            "         FROM user_pagination u\n" +
            "                  LEFT JOIN chat_rooms c ON u.id = c.owner_id\n" +
            "     ),\n" +
            "     UserParticipatingChats AS (\n" +
            "         SELECT\n" +
            "             u.id as user_id,\n" +
            "             c.id as chat_id,\n" +
            "             c.name,\n" +
            "             c.owner_id\n" +
            "         FROM user_pagination u\n" +
            "                  JOIN messages m ON u.id = m.author_id\n" +
            "                  JOIN chat_rooms c ON m.room_id = c.id\n" +
            "     )\n" +
            "SELECT\n" +
            "    up.id,\n" +
            "    up.login,\n" +
            "    up.password,\n" +
            "    ARRAY_AGG(DISTINCT uc.chat_id || ':' || uc.name || ':' || uc.owner_id) AS created_chats,\n" +
            "    ARRAY_AGG(DISTINCT upc.chat_id || ':' || upc.name || ':' || upc.owner_id) AS participating_chats\n" +
            "FROM user_pagination up\n" +
            "         LEFT JOIN UserChats uc ON up.id = uc.user_id\n" +
            "         LEFT JOIN UserParticipatingChats upc ON up.id = upc.user_id\n" +
            "WHERE up.row_num BETWEEN ? AND ?\n" +
            "GROUP BY up.id, up.login, up.password;";

    private final DataSource dataSource;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> findAll(int page, int size) {
        List<User> users = new ArrayList<>();
        if (page < 1) {
            throw new DatabaseException("Page must be greater than 0");
        }
        if (size < 1) {
            throw new DatabaseException("Size must be greater than 0");
        }

        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_USERS_WITH_ROOMS);
            ) {
            int startRow = (page - 1) * size + 1;
            int endRow = page * size;
            statement.setInt(1, startRow);
            statement.setInt(2, endRow);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));

                String createdChatsStr = resultSet.getString("created_chats");
                String participatingChatsStr = resultSet.getString("participating_chats");

                user.setCreatedChatRooms(parseChatArray(createdChatsStr));
                user.setSocializes(parseChatArray(participatingChatsStr));

                users.add(user);
            }

        } catch (SQLException e) {
//            throw new DataBaseException("Can't find all users");
            e.printStackTrace();
        }
        return users;
    }

    private List<Room> parseChatArray(String chatArrayStr) {
        List<Room> chatList = new ArrayList<>();
        String[] chatArray = chatArrayStr.replaceAll("[{}\"]", "").split(",");

        for (String chatInfo : chatArray) {
            String[] chatParts = chatInfo.split(":");
            if (chatParts.length == 3) {
                long chatId = Long.parseLong(chatParts[0]);
                String chatName = chatParts[1];
                long ownerId = Long.parseLong(chatParts[2]);
                Room chat = new Room(chatId, chatName, new User(ownerId));
                chatList.add(chat);
            }
        }

        return chatList;
    }

    private List<Room> parseChatArray(String chatArrayStr, User owner) {
        List<Room> chatList = new ArrayList<>();
        String[] chatArray = chatArrayStr.replaceAll("[{}\"]", "").split(",");

        for (String chatInfo : chatArray) {
            String[] chatParts = chatInfo.split(":");
            if (chatParts.length == 3) {
                long chatId = Long.parseLong(chatParts[0]);
                String chatName = chatParts[1];
                Room chat = new Room(chatId, chatName, owner);
                chatList.add(chat);
            }
        }

        return chatList;
    }
}
