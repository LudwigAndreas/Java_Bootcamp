package edu.school21.game.logic.model.object;

import com.diogonunes.jcdp.color.api.Ansi;
import edu.school21.game.logic.model.GameObjectAttributes;

public class Empty extends GameObject {
    public Empty(int x, int y, Ansi.BColor color, char symbol) {
        super(x, y, color, symbol);
    }

    public Empty(int x, int y, GameObjectAttributes attributes) {
        super(x, y, attributes);
    }

    @Override
    public boolean isPassable() {
        return true;
    }
}
