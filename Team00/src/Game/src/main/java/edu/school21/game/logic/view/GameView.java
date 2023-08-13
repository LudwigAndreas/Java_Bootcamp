package edu.school21.game.logic.view;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import edu.school21.game.logic.model.GameMap;
import edu.school21.game.logic.model.object.GameObject;

public class GameView {
    private ColoredPrinter cp = null;

    public GameView() {
        cp = new ColoredPrinter.Builder(1, false).build();
    }

    public GameView(ColoredPrinter cp) {
        if (cp != null)
            this.cp = cp;
        else
            this.cp = new ColoredPrinter.Builder(1, false).build();
    }

    public void printMap(GameMap map) {
        GameObject[][] printableMap = map.getPrintableMap();
        for (int x = 0; x < printableMap.length; x++) {
            for (int y = 0; y < printableMap[x].length; y++) {
                    cp.print(printableMap[x][y].getSymbol(), Ansi.Attribute.NONE, Ansi.FColor.BLACK, printableMap[x][y].getColor());
            }
//            cp.println(""); // it isn't working
            System.out.println();
        }
    }
}
