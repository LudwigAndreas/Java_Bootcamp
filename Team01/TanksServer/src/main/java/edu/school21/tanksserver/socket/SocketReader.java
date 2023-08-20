package edu.school21.tanksserver.socket;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketReader extends Thread {
    private Socket socket;
    private BufferedReader reader;
    private String data;
    private Gson gson = new GsonBuilder().create();
    private boolean hasData = false;

    public SocketReader(Socket socket) {
        this.socket = socket;
        try {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String receivedData;
            while ((receivedData = reader.readLine()) != null) {
                System.out.println("Received data: " + receivedData);
                synchronized (this) {
                    data = receivedData;
                    hasData = true;
                    notify();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized JsonObject getData() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        hasData = false;
        return gson.fromJson(data, JsonObject.class);
    }

    public synchronized boolean hasData() {
        return hasData;
    }
}
