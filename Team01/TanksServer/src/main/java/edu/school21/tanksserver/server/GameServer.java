package edu.school21.tanksserver.server;

import edu.school21.tanksserver.connection.ConnectionHandler;
import edu.school21.tanksserver.exception.ConnectionException;
import edu.school21.tanksserver.service.GameService;
import edu.school21.tanksserver.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

@Component
public class GameServer implements Server {

    protected Integer port;
    protected String host;

    protected ServerSocket serverSocket;
    protected boolean isStopped;
    List<ConnectionHandler> handlers;

    protected RoomService roomService;
    protected GameService gameService;

    @Autowired
    public GameServer(RoomService roomService, GameService gameService) {
        this.roomService = roomService;
        this.gameService = gameService;
    }

    @Override
    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
            while (!isStopped) {
                handleConnection(serverSocket.accept());
            }
        } catch (IOException e) {
            System.out.println("Error while handling connection");
        }
    }

    protected void handleConnection(Socket socket) {
        try {

            System.out.println("New connection: " + socket.getInetAddress() + ":" + socket.getPort());
            ConnectionHandler handler = new ConnectionHandler(socket, roomService, gameService);
            handlers.add(handler);
            handler.start();
        } catch (ConnectionException e) {
            System.out.println("Error while creating handler");
        }
    }

}
