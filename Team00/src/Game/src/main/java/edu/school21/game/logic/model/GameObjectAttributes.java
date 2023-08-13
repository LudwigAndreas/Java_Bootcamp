package edu.school21.game.logic.model;

import com.diogonunes.jcdp.color.api.Ansi;

public class GameObjectAttributes {
    private char symbol;
    private Ansi.BColor color;

    public GameObjectAttributes(char symbol, Ansi.BColor color) {
        this.symbol = symbol;
        this.color = color;
    }

    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public Ansi.BColor getColor() {
        return color;
    }

    public void setColor(Ansi.BColor color) {
        this.color = color;
    }
}
