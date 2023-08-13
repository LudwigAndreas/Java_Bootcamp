package edu.school21.game.logic.model.object;

import com.diogonunes.jcdp.color.api.Ansi;
import edu.school21.game.logic.model.GameObjectAttributes;

public class Player extends GameObject implements Movable {
    public Player(int x, int y, Ansi.BColor color, char symbol) {
        super(x, y, color, symbol);
    }

    public Player(int x, int y, GameObjectAttributes attributes) {
        super(x, y, attributes);
    }

    @Override
    public void moveUp() {
        if (this.y > 0)
            this.y--;
    }

    @Override
    public void moveDown() {
            this.y++;
    }

    @Override
    public void moveLeft() {
        if (this.x > 0)
            this.x--;
    }

    @Override
    public void moveRight() {
            this.x++;
    }

    @Override
    public boolean isPassable() {
        return false;
    }
}
