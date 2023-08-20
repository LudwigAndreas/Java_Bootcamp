package edu.school21.tanksclient.connection.network;

import com.google.gson.JsonObject;
import edu.school21.tanksclient.exception.NetworkException;

public interface NetworkManager {
    void connect() throws NetworkException;
    boolean isConnected();
    void sendData(String data);
    JsonObject getData();
    boolean hasData();
}
