package edu.school21.tanksclient.connection;

import edu.school21.tanksclient.exception.NetworkException;

public interface GameSessionManager {
    void createSession(String username) throws NetworkException;
    void joinSession(String username, String passcode);
    void leaveSession();
}
