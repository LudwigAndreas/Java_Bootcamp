package edu.school21.chat.repositories;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Room;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import java.sql.*;
import java.util.Optional;
import javax.sql.DataSource;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private static final String SQL_FIND_MESSAGE_BY_ID = "SELECT * FROM messages WHERE id = ?";
    private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String SQL_FIND_CHATROOM_BY_ID = "SELECT * FROM chat_rooms WHERE id = ?";
    private static final String SQL_INSERT_MESSAGE = "INSERT INTO messages (author_id, room_id, message, datetime) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE_MESSAGE = "UPDATE messages SET author_id = ?, room_id = ?, message = ?, datetime = ? WHERE id = ?";

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
                Room room = new Room(roomId, resultSetRoom.getString("name"), null);
                Message message = new Message(id, author, room, resultSet.getString("message"), resultSet.getTimestamp("datetime"));
                return Optional.of(message);
            }
            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Long save(Message message) throws NotSavedSubEntityException {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_MESSAGE, Statement.RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setLong(1, message.getAuthor().getId());
            preparedStatement.setLong(2, message.getRoom().getId());
            preparedStatement.setString(3, message.getText());
            preparedStatement.setTimestamp(4, message.getDateTime());
            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();

            message.setId(resultSet.getLong(1));
            return message.getId();
        } catch (SQLException e) {
            throw new NotSavedSubEntityException("Message not saved");
        }
    }

    @Override
    public Long update(Message message) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_MESSAGE);
            ) {
            if (message.getAuthor().getId() == null)
                statement.setNull(1, Types.BIGINT);
            else
                statement.setLong(1, message.getAuthor().getId());
            if (message.getRoom().getId() == null)
                statement.setNull(2, Types.BIGINT);
            else
                statement.setLong(2, message.getRoom().getId());
            if (message.getDateTime() == null)
                statement.setNull(4, Types.TIMESTAMP);
            else
                statement.setTimestamp(4, message.getDateTime());

            statement.setString(3, message.getText());
            statement.setLong(5, message.getId());
            statement.executeUpdate();
            return message.getId();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
