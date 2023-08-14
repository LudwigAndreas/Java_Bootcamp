package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class ChatRoom {
    private Long id;
    private String name;
    private User owner;
    private List<Message> messages;

    public ChatRoom(Long id, String name, User owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    public Long getId() {
        return id;
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
        ChatRoom chatRoom = (ChatRoom) o;
        return getId().equals(chatRoom.getId()) && getName().equals(chatRoom.getName()) && getOwner().equals(chatRoom.getOwner()) && Objects.equals(getMessages(), chatRoom.getMessages());
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
                ", owner=" + owner.getLogin() +
                '}';
    }
}
