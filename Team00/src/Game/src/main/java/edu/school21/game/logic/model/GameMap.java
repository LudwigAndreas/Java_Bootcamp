package edu.school21.game.logic.model;

import edu.school21.game.logic.model.object.Enemy;
import edu.school21.game.logic.model.object.GameObject;
import edu.school21.game.logic.model.object.Goal;
import edu.school21.game.logic.model.object.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class GameMap {
    private GameObject[][] map;
    private int size;
    private int enemiesCount;
    private int wallsCount;
    GameObjectFactory factory;
    private Player player;
    private Goal goal;
    private List<Enemy> enemies;

    public GameMap(int size, int enemiesCount, int wallsCount) {
        this.size = size;
        this.enemiesCount = enemiesCount;
        this.wallsCount = wallsCount;
        if (enemiesCount + wallsCount + 2 >= size * size)
            throw new IllegalArgumentException("Too many objects for this map size");
        this.map = new GameObject[size][size];
        this.enemies = new ArrayList<>();
        this.factory = new GameObjectFactory();
        generateMap();
    }

    public GameMap(int size, int enemiesCount, int wallsCount, Properties properties) {
        this.size = size;
        this.enemiesCount = enemiesCount;
        this.wallsCount = wallsCount;
        if (enemiesCount + wallsCount + 2 >= size * size)
            throw new IllegalArgumentException("Too many objects for this map size");
        this.map = new GameObject[size][size];
        this.enemies = new ArrayList<>();
        this.factory = new GameObjectFactory(properties);
        generateMap();
    }

    public void initializeMap() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[i][j] == null)
                    map[i][j] = factory.createEmpty(i, j);
            }
        }
    }

    private void generateMap() {
        Random random = new Random();
        int x;
        int y;

        initializeMap();

        for (int i = 0; i < wallsCount; i++) {
            do {
                x = random.nextInt(size);
                y = random.nextInt(size);
            } while (!map[y][x].isPassable());
            map[y][x] = factory.createWall(x, y);
        }


        do {
            x = random.nextInt(size);
            y = random.nextInt(size);
        } while (!map[y][x].isPassable());
        player = (Player) factory.createPlayer(x, y);

        do {
            x = random.nextInt(size);
            y = random.nextInt(size);
        } while (!map[y][x].isPassable()
                || (x == player.getX() && y == player.getY()));
        goal = (Goal) factory.createGoal(x, y);

        for (int i = 0; i < enemiesCount; i++) {
            do {
                x = random.nextInt(size);
                y = random.nextInt(size);
                boolean alreadyExists = false;
                for (Enemy enemy : enemies) {
                    if (x == enemy.getX() && y == enemy.getY()) {
                        x = 0;
                        y = 0;
                        alreadyExists = true;
                    }
                }
                if (!alreadyExists && map[y][x].isPassable()
                        && (x != player.getX() || y != player.getY())
                        && (x != goal.getX() || y != goal.getY())) {
                    break;
                }
            } while (true);
            enemies.add((Enemy) factory.createEnemy(x, y));
        }

//        TODO: check that player and goal are reachable
    }

    public GameObject[][] getMap() {
        return map;
    }

    public int getSize() {
        return size;
    }

    public int getEnemiesCount() {
        return enemiesCount;
    }

    public int getWallsCount() {
        return wallsCount;
    }

    public void setMap(GameObject[][] map) {
        this.map = map;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Player getPlayer() {
        return player;
    }

    public Goal getGoal() {
        return goal;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public GameObject[][] getPrintableMap() {
        GameObject[][] printableMap = new GameObject[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(map[i], 0, printableMap[i], 0, size);
        }
        printableMap[player.getY()][player.getX()] = player;
        printableMap[goal.getY()][goal.getX()] = goal;
        for (Enemy enemy : enemies) {
            printableMap[enemy.getY()][enemy.getX()] = enemy;
        }
        return printableMap;
    }
}
