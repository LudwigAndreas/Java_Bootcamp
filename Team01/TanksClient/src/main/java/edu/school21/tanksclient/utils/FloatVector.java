package edu.school21.tanksclient.utils;

public class FloatVector implements Vector2<Float> {
    Float X;
    Float Y;

    FloatVector(Float X, Float Y) {
        setX(X);
        setY(Y);
    }

    @Override
    public Float getX() {
        return X;
    }

    @Override
    public Float getY() {
        return Y;
    }

    public void setX(Float X) {
        this.X = X;
    }

    public void setY(Float Y) {
        this.Y = Y;
    }
}
