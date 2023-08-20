package edu.school21.tanksclient.connection.network;

import com.google.gson.JsonObject;
import edu.school21.tanksclient.connection.network.socket.SocketIO;
import edu.school21.tanksclient.exception.NetworkException;
//import exception.NetworkException;

import java.io.IOException;
import java.net.Socket;

public class NetworkManagerImpl implements NetworkManager {
    private SocketIO socketIO;
    private String address;
    private int port;
    private boolean isConnected = false;

    public NetworkManagerImpl(String address, int port) {
        this.address = address;
        this.port = port;
    }

    @Override
    public void connect() throws NetworkException {
        try {
            socketIO = new SocketIO(new Socket(address, port));
            isConnected = true;
        } catch (IOException e) {
            throw new NetworkException("Error while connecting to server", e);
        }
    }

    @Override
    public boolean isConnected() {
        return false;
    }


    @Override
    public void sendData(String data) {

    }

    @Override
    public JsonObject getData() {
        return null;
    }

    @Override
    public boolean hasData() {
        return false;
    }
}
