package edu.school21.game.logic.model.object;

import com.diogonunes.jcdp.color.api.Ansi;
import edu.school21.chase.utils.Cell;
import edu.school21.game.logic.model.GameObjectAttributes;

import java.util.Objects;

public class GameObject implements Cell<Integer> {
    protected int x;
    protected int y;
    protected Ansi.BColor color;
    protected char symbol;
    protected boolean passable = true;


    public GameObject(int x, int y, Ansi.BColor color, char symbol) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.symbol = symbol;
    }

    public GameObject(int x, int y, GameObjectAttributes attributes) {
        this.x = x;
        this.y = y;
        this.color = attributes.getColor();
        this.symbol = attributes.getSymbol();
    }

    public Integer getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Ansi.BColor getColor() {
        return color;
    }

    public void setColor(Ansi.BColor color) {
        this.color = color;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameObject that = (GameObject) o;
        return getX() == that.getX() && getY() == that.getY() && getSymbol() == that.getSymbol() && getColor() == that.getColor();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY(), getColor(), getSymbol());
    }

    @Override
    public String toString() {
        return "GameObject{" +
                "x=" + x +
                ", y=" + y +
                ", color=" + color +
                ", symbol=" + symbol +
                '}';
    }

    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isPassable() {
        return passable;
    }

    @Override
    public void setPassable(boolean b) {
        this.passable = b;
    }
}
