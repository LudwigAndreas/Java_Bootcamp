package edu.school21.tanksserver.socket;

import com.google.gson.JsonObject;

import java.net.Socket;

public class SocketIO {
    SocketReader reader;
    SocketWriter writer;

    public SocketIO(Socket socket) {
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
