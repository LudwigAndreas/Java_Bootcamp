package edu.school21.tanksserver.connection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import edu.school21.tanksserver.exception.JoinException;
import edu.school21.tanksserver.model.ConnectionInit;
import edu.school21.tanksserver.model.Room;
import edu.school21.tanksserver.model.Session;
import edu.school21.tanksserver.model.User;
import edu.school21.tanksserver.service.GameService;
import edu.school21.tanksserver.service.RoomService;
import edu.school21.tanksserver.socket.SocketIO;

import java.net.Socket;

public class ConnectionHandler extends Thread {
    protected Socket socket;
    protected RoomService roomService;
    protected GameService gameService;
    protected SocketIO socketIO;
    private Gson gson = new GsonBuilder().create();


    public ConnectionHandler(Socket accept, RoomService roomService, GameService gameService) {
        this.socket = accept;
        this.roomService = roomService;
        this.gameService = gameService;
        this.socketIO = new SocketIO(socket);
    }

    @Override
    public void run() {
        try {
            ConnectionInit connectionInit = gson.fromJson(socketIO.getData(), ConnectionInit.class);
            User user = authenticate(connectionInit.getUsername());
            System.out.println("User " + user.getUsername() + " authenticated");
            Room room = enterRoom(user, connectionInit);

        } catch (JsonSyntaxException e){
            System.out.println("Error while parsing json");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private User authenticate(String username) {
        return new User(username);
    }

    private Room enterRoom(User user, ConnectionInit connection) throws JoinException {
        if (connection.getType() == ConnectionInit.Type.CREATE) {
            Room room = roomService.createRoom(user);
            System.out.println("User " + user.getUsername() + " created room " + room.getName() + " with passcode " + room.getPasscode());
            return room;
        }
        else if (connection.getType() == ConnectionInit.Type.JOIN) {
            try {
                Room room = roomService.joinRoom(user, connection.getPasscode());
                System.out.println("User " + user.getUsername() + " entered room " + room.getName());
                return room;
            } catch (JoinException e) {
                socketIO.sendData(gson.toJson(new Session()));
                throw e;
            }
        } else {
            throw new IllegalArgumentException("Unknown connection type");
        }
    }
}
