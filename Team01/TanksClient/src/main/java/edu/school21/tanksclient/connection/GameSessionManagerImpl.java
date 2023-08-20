package edu.school21.tanksclient.connection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.school21.tanksclient.connection.network.ConnectionInit;
import edu.school21.tanksclient.connection.network.NetworkManager;
import edu.school21.tanksclient.exception.NetworkException;
//import edu.school21.tanksclient.exception.NetworkException;

public class GameSessionManagerImpl implements GameSessionManager {
    NetworkManager networkManager;
    Gson gson = new GsonBuilder().create();

    public GameSessionManagerImpl(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    @Override
    public void createSession(String username) throws NetworkException {
        if (networkManager.isConnected()) {
            networkManager.connect();
            ConnectionInit connectionInit = new ConnectionInit(username);
            networkManager.sendData(gson.toJson(connectionInit));
        }
        else {

        }
    }

    @Override
    public void joinSession(String username, String passcode) {

    }

    @Override
    public void leaveSession() {

    }
}
