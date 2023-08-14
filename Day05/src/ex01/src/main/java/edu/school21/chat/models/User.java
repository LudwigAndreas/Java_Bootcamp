package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class User {
    private Long id;
    private String login;
    private String password;
    private List<ChatRoom> createdChatRooms;
    private List<ChatRoom> socializes;

    public User(Long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ChatRoom> getCreatedChatRooms() {
        return createdChatRooms;
    }

    public void setCreatedChatRooms(List<ChatRoom> createdChatRooms) {
        this.createdChatRooms = createdChatRooms;
    }

    public List<ChatRoom> getSocializes() {
        return socializes;
    }

    public void setSocializes(List<ChatRoom> socializes) {
        this.socializes = socializes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId().equals(user.getId()) && getLogin().equals(user.getLogin()) && getPassword().equals(user.getPassword()) && Objects.equals(getCreatedChatRooms(), user.getCreatedChatRooms()) && Objects.equals(getSocializes(), user.getSocializes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLogin(), getPassword(), getCreatedChatRooms(), getSocializes());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", number createdChatRooms=" + createdChatRooms +
                ", number socializes=" + socializes +
                '}';
    }
}
