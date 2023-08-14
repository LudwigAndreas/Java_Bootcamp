package edu.school21.chat.repositories;

import edu.school21.chat.models.ChatRoom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import java.sql.*;
import java.util.Optional;
import javax.sql.DataSource;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private static final String SQL_FIND_MESSAGE_BY_ID = "SELECT * FROM messages WHERE id = ?";
    private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String SQL_FIND_CHATROOM_BY_ID = "SELECT * FROM chat_rooms WHERE id = ?";

    private final DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        try {

            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_MESSAGE_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long authorId = resultSet.getLong("author_id");
                Long roomId = resultSet.getLong("room_id");
                PreparedStatement preparedStatementAuthor = connection.prepareStatement(SQL_FIND_USER_BY_ID);
                preparedStatementAuthor.setLong(1, authorId);
                ResultSet resultSetAuthor = preparedStatementAuthor.executeQuery();
                resultSetAuthor.next();
                User author = new User(authorId, resultSetAuthor.getString("login"), resultSetAuthor.getString("password"));
                PreparedStatement preparedStatementRoom = connection.prepareStatement(SQL_FIND_CHATROOM_BY_ID);
                preparedStatementRoom.setLong(1, roomId);
                ResultSet resultSetRoom = preparedStatementRoom.executeQuery();
                resultSetRoom.next();
                ChatRoom room = new ChatRoom(roomId, resultSetRoom.getString("name"), null);
                Message message = new Message(id, author, room, resultSet.getString("message"), resultSet.getTimestamp("datetime"));
                return Optional.of(message);
            }
            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
