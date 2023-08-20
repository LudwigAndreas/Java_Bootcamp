package edu.school21.tanksclient.connection.network.socket;

import com.google.gson.JsonObject;

import java.net.Socket;

public class SocketIO {
    SocketReader reader;
    SocketWriter writer;
    Socket socket;

    public SocketIO(Socket socket) {
        this.socket = socket;
        reader = new SocketReader(socket);
        writer = new SocketWriter(socket);
    }

    public SocketReader getReader() {
        return reader;
    }

    public SocketWriter getWriter() {
        return writer;
    }

    public void start() {
        reader.start();
        writer.start();
    }

    public void sendData(JsonObject data) {
        writer.sendData(data);
    }

    public void sendData(String data) {
        writer.sendData(data);
    }

    public JsonObject getData() {
        return reader.getData();
    }

    public boolean hasData() {
        return reader.hasData();
    }
}
