package edu.school21.game.logic.controller;

import com.diogonunes.jcdp.color.ColoredPrinter;
import com.diogonunes.jcdp.color.api.Ansi;
import edu.school21.game.logic.model.GameMap;
import edu.school21.game.logic.model.object.Direction;
import edu.school21.game.logic.model.object.Goal;
import edu.school21.game.logic.model.object.Player;
import edu.school21.game.logic.view.GameView;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

public class Game {
    private Player player;
    private Goal goal;
    private GameMap map;
    private GameView view;
    private ColoredPrinter cp;
    private MovableEntityManager entityManager;
    private String profile;

    public Game(String profile, Properties properties, ColoredPrinter cp, int size, int enemiesCount, int wallsCount) {
        this.profile = profile;
        map = new GameMap(size, enemiesCount, wallsCount, properties);
        player = map.getPlayer();
        goal = map.getGoal();
        view = new GameView(cp);
        this.cp = cp;
        this.entityManager = new MovableEntityManager(map);
    }

    public GameMap generateMap(int size, int enemiesCount, int wallsCount) {
        this.map = new GameMap(size, enemiesCount, wallsCount);
        return map;
    }

    public GameMap generateMap(int size, int enemiesCount, int wallsCount, Properties properties) {
        this.map = new GameMap(size, enemiesCount, wallsCount, properties);
        return map;
    }

    public void run() {
        view.printMap(map);
        while (true) {
            try {
                ConsoleManipulator.clearConsole();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            char input = ConsoleManipulator.readControls();
            switch (input) {
                case 'w':
                    entityManager.movePlayer(Direction.UP);
                    break;
                case 's':
                    entityManager.movePlayer(Direction.DOWN);
                    break;
                case 'a':
                    entityManager.movePlayer(Direction.LEFT);
                    break;
                case 'd':
                    entityManager.movePlayer(Direction.RIGHT);
                    break;
                case '9':
                    loose();
            }
            if (profile.toUpperCase().equals("DEV")) {
                for (int i = 0; i < map.getEnemies().size(); i++) {
                    entityManager.moveOneEnemy(map.getEnemies().get(i));
                    char confirm = ConsoleManipulator.readControls();
                    if (confirm == '9') {
                        loose();
                    } else if (confirm == '8') {
                        continue;
                    }
                    view.printMap(map);
                }
            } else {
                entityManager.moveEnemies();
                if (entityManager.isPlayerKilled()) {
                    loose();
                }
                if (isWin()) {
                    System.out.println();
                    cp.print("You win!", Ansi.Attribute.BOLD, Ansi.FColor.WHITE, Ansi.BColor.GREEN);
                    break;
                }
                view.printMap(map);
            }
        }
    }

    private void loose() {
        System.out.println();
        cp.print("You lose!", Ansi.Attribute.BOLD, Ansi.FColor.WHITE, Ansi.BColor.RED);
        System.exit(0);
    }

    private boolean isWin() {
        return player.getX() == goal.getX() && player.getY() == goal.getY();
    }
}
