package edu.school21.chat.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Room {
    private Long id;
    private String name;
    private User owner;
    private List<Message> messages;

    public Room(Long id, String name, User owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    public Room(long id, String name, User owner, List<Message> messages) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.messages = messages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return getId().equals(room.getId()) && getName().equals(room.getName()) && getOwner().equals(room.getOwner()) && Objects.equals(getMessages(), room.getMessages());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getOwner(), getMessages());
    }

    @Override
    public String toString() {
        return "ChatRoom{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner=" + owner +
                '}';
    }
}
