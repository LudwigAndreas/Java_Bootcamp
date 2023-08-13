package edu.school21.chase.utils;

public interface Cell<T> extends Vector2<T> {
    boolean isPassable();
    void setPassable(boolean passable);
}
