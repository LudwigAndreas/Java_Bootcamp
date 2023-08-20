package edu.school21.tanksclient.connection.network;

public class ConnectionInit {

    public enum Type {
        CREATE,
        JOIN
    }

    public ConnectionInit(String username) {
        this.username = username;
        this.type = Type.CREATE;
    }

    public ConnectionInit(String username, String passcode) {
        this.username = username;
        this.type = Type.JOIN;
    }

    private String username;
    private Type type;
    private String passcode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }
}
