package edu.school21.tanksserver.model;

import edu.school21.tanksserver.generators.PasscodeGenerator;

import java.util.Collection;
import java.util.Objects;

public class Room {
    private User host;
    private User guest;
    private String name;
    private String passcode;

    public Room(User user1) {
        this.host = user1;
        this.name = user1.getUsername() + "'s room";
        this.passcode = String.valueOf(PasscodeGenerator.nextInt());
    }

    public User getHost() {
        return host;
    }

    public User getGuest() {
        return guest;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }

    public String getName() {
        return name;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void updatePasscode() {
        this.passcode = String.valueOf((int) (Math.random() * 10000));
    }

    public boolean isFull() {
        return guest != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return getHost().equals(room.getHost()) && Objects.equals(getGuest(), room.getGuest());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHost(), getGuest());
    }

    @Override
    public String toString() {
        return "Room{" +
                "host=" + host +
                ", guest=" + guest +
                ", name='" + name + '\'' +
                ", passcode='" + passcode + '\'' +
                '}';
    }
}
