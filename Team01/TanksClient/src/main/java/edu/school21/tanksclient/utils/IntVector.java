package edu.school21.tanksclient.utils;

public class IntVector implements Vector2<Integer> {
    Integer X;
    Integer Y;

    IntVector(Integer X, Integer Y) {
        setX(X);
        setY(Y);
    }

    @Override
    public Integer getX() {
        return X;
    }

    @Override
    public Integer getY() {
        return Y;
    }

    public void setX(Integer X) {
        this.X = X;
    }

    public void setY(Integer Y) {
        this.Y = Y;
    }
}
