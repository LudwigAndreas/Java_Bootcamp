package edu.school21.chat.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private Long id;
    private User author;
    private Room room;
    private String text;
    private Timestamp dateTime;

    public Message(Long id, User author, Room room, String text, Timestamp dateTime) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.text = text;
        this.dateTime = dateTime;
    }

    public Message(Long id, User author, Room room, String text, LocalDateTime dateTime) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.text = text;
        this.dateTime = Timestamp.valueOf(dateTime);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return getId().equals(message.getId()) && getAuthor().equals(message.getAuthor()) && getRoom().equals(message.getRoom()) && getText().equals(message.getText()) && getDateTime().equals(message.getDateTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAuthor(), getRoom(), getText(), getDateTime());
    }

    @Override
    public String toString() {
        return "Message : {" + "\n" +
                "\tid=" + id + ",\n" +
                "\tauthor=" + author + ",\n" +
                "\troom=" + room + ",\n" +
                "\ttext=\"" + text + '\"' + ",\n" +
                "\tdateTime=" + dateTime + ",\n" +
                '}';
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = Timestamp.valueOf(dateTime);
    }
}
