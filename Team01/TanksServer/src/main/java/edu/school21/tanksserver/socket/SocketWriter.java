package edu.school21.tanksserver.socket;

import com.google.gson.JsonObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketWriter extends Thread {
    private Socket socket;
    private BufferedWriter writer;
    private StringBuilder data;

    public SocketWriter(Socket socket) {
        this.socket = socket;
        try {
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        data = new StringBuilder();
    }


    @Override
    public void run() {
        try {
            while (true) {
                if (data.length() > 0) {
                    data.append("]");
                    writer.write(data.toString());
                    data.setLength(0);
                    writer.newLine();
                    writer.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendData(JsonObject data) {
        if (this.data.length() > 0) {
            this.data.append(", ").append(data);
        } else {
            this.data.append("\"content: \"[")
                    .append(data);
        }
    }

    public synchronized void sendData(String data) {
        if (this.data.length() > 0) {
            this.data.append(", ").append(data);
        } else {
            this.data.append("\"content\": [")
                    .append(data);
        }
    }
}
