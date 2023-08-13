package edu.school21.game.logic.controller;

import edu.school21.chase.astar.AStar;
import edu.school21.game.logic.model.GameMap;
import edu.school21.game.logic.model.object.Enemy;
import edu.school21.game.logic.model.object.GameObject;
import edu.school21.game.logic.model.object.Player;
import edu.school21.game.logic.model.object.Direction;

import java.util.List;

public class MovableEntityManager {

    private Player player;
    private List<Enemy> enemies;
    private GameMap map;
    private AStar pathFindingAlgorithm;
    private GameObject[][] printableMap = null;

    public MovableEntityManager(GameMap map) {
        this.map = map;
        this.enemies = map.getEnemies();
        this.player = map.getPlayer();
        this.pathFindingAlgorithm = new AStar();
        this.printableMap = map.getPrintableMap();
    }

    public void movePlayer(Direction direction) {
        switch (direction) {
            case UP:
                if (player.getY() > 0 && map.getMap()[player.getY() - 1][player.getX()].isPassable())
                    player.moveUp();
                break;
            case DOWN:
                if (player.getY() < map.getSize() - 1 && map.getMap()[player.getY() + 1][player.getX()].isPassable())
                    player.moveDown();
                break;
            case LEFT:
                if (player.getX() > 0 && map.getMap()[player.getY()][player.getX() - 1].isPassable())
                    player.moveLeft();
                break;
            case RIGHT:
                if (player.getX() < map.getSize() - 1 && map.getMap()[player.getY()][player.getX() + 1].isPassable())
                    player.moveRight();
                break;
        }
        printableMap = map.getPrintableMap();
    }

    public void moveEnemies() {
        for (Enemy enemy : enemies) {
            getNextMove(enemy);
        }
    }

    public void moveOneEnemy(Enemy enemy) {
        getNextMove(enemy);
    }

    private void getNextMove(Enemy enemy) {
        GameObject next = (GameObject) pathFindingAlgorithm.findPath(printableMap, enemy, player);
        if (next.getX() > enemy.getX())
            enemy.moveRight();
        else if (next.getX() < enemy.getX())
            enemy.moveLeft();
        else if (next.getY() > enemy.getY())
            enemy.moveDown();
        else if (next.getY() < enemy.getY())
            enemy.moveUp();
    }

    public boolean isPlayerKilled() {
        for (Enemy enemy : enemies) {
            if (enemy.getX() == player.getX() && enemy.getY() == player.getY())
                return true;
        }
        return false;
    }
}
