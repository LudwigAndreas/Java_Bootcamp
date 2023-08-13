package edu.school21.game.logic.model.object;

import com.diogonunes.jcdp.color.api.Ansi;
import edu.school21.game.logic.model.GameObjectAttributes;
import edu.school21.game.logic.model.object.GameObject;

public class Wall extends GameObject {
    public Wall(int x, int y, Ansi.BColor color, char symbol) {
        super(x, y, color, symbol);
    }

    public Wall(int x, int y, GameObjectAttributes attributes) {
        super(x, y, attributes);
    }

    @Override
    public boolean isPassable() {
        return false;
    }
}
