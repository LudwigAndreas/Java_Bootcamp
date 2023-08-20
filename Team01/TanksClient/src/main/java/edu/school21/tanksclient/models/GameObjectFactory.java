package edu.school21.tanksclient.models;

import edu.school21.tanksclient.models.objects.GameObject;
import edu.school21.tanksclient.models.objects.PlayerTank;
import edu.school21.tanksclient.models.objects.EnemyTank;
import edu.school21.tanksclient.models.objects.Missile;
import edu.school21.tanksclient.utils.FloatVector;

// import edu.school21.game.logic.model.object.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

import javafx.scene.image.Image;

public class GameObjectFactory {
	private Map<GameObjectType, Image> images = new HashMap<>();

	public enum GameObjectType {
		PLAYER,
		ENEMY,
		MISSILE
	}

	public GameObjectFactory() {
		images.put(GameObjectType.PLAYER,  new Image(getClass().getClassLoader().getResourceAsStream("green-tank-up.gif")));
		images.put(GameObjectType.ENEMY,   new Image(getClass().getClassLoader().getResourceAsStream("red-tank-up.gif")));
		images.put(GameObjectType.MISSILE, new Image(getClass().getClassLoader().getResourceAsStream("missile.gif")));
	}
//
//    public GameObjectFactory(Properties properties) {
    //    if (properties.containsKey("enemy.char") && properties.getProperty("enemy.char").length() == 1)
    //        enemyAttributes.setSymbol(properties.getProperty("enemy.char").charAt(0));
    //    if (properties.containsKey("enemy.color") && properties.getProperty("enemy.color").length() > 0)
    //        enemyAttributes.setColor(Ansi.BColor.valueOf(properties.getProperty("enemy.color")));
    //    if (properties.containsKey("wall.char") && properties.getProperty("wall.char").length() == 1)
    //        wallAttributes.setSymbol(properties.getProperty("wall.char").charAt(0));
    //    if (properties.containsKey("wall.color") && properties.getProperty("wall.color").length() > 0)
    //        wallAttributes.setColor(Ansi.BColor.valueOf(properties.getProperty("wall.color")));
    //    if (properties.containsKey("goal.char") && properties.getProperty("goal.char").length() == 1)
    //        goalAttributes.setSymbol(properties.getProperty("goal.char").charAt(0));
    //    if (properties.containsKey("goal.color") && properties.getProperty("goal.color").length() > 0)
    //        goalAttributes.setColor(Ansi.BColor.valueOf(properties.getProperty("goal.color")));
    //    if (properties.containsKey("player.char") && properties.getProperty("player.char").length() == 1)
    //        playerAttributes.setSymbol(properties.getProperty("player.char").charAt(0));
    //    if (properties.containsKey("player.color") && properties.getProperty("player.color").length() > 0)
    //        playerAttributes.setColor(Ansi.BColor.valueOf(properties.getProperty("player.color")));
    //    if (properties.containsKey("empty.char") && properties.getProperty("empty.char").length() == 1)
    //        emptyAttributes.setSymbol(properties.getProperty("empty.char").charAt(0));
    //    if (properties.containsKey("empty.color") && properties.getProperty("empty.color").length() > 0)
    //        emptyAttributes.setColor(Ansi.BColor.valueOf(properties.getProperty("empty.color")));
//    }

   GameObject createObject(GameObjectType type, FloatVector fv, Float angle) {
       switch (type) {
           case PLAYER:
               return new PlayerTank(fv, angle, images.get(type));
           case ENEMY:
               return new EnemyTank(fv, angle, images.get(type));
           case MISSILE:
               return new Missile(fv, angle, images.get(type));
           default:
               return null;
       }
   }
//
//    GameObject createObject(GameObjectType type, int x, int y, Ansi.BColor color, char symbol) {
//        switch (type) {
//            case WALL:
//                return new Wall(x, y, color, symbol);
//            case GOAL:
//                return new Goal(x, y, color, symbol);
//            case PLAYER:
//                return new Player(x, y, color, symbol);
//            case EMPTY:
//                return new Empty(x, y, color, symbol);
//            case ENEMY:
//                return new Enemy(x, y, color, symbol);
//            default:
//                return null;
//        }
//    }

   public GameObject createMissile(FloatVector pos, Float angle) {
       return createObject(GameObjectType.MISSILE, pos, angle);
   }

   public GameObject createPlayer(FloatVector pos, Float angle) {
       return createObject(GameObjectType.PLAYER, pos, angle);
   }

   public GameObject createEnemy(FloatVector pos, Float angle) {
       return createObject(GameObjectType.ENEMY, pos, angle);
   }
//
//    public GameObjectAttributes getWallAttributes() {
//        return wallAttributes;
//    }
//
//    public void setWallAttributes(GameObjectAttributes wallAttributes) {
//        this.wallAttributes = wallAttributes;
//    }
//
//    public GameObjectAttributes getGoalAttributes() {
//        return goalAttributes;
//    }
//
//    public void setGoalAttributes(GameObjectAttributes goalAttributes) {
//        this.goalAttributes = goalAttributes;
//    }
//
//    public GameObjectAttributes getPlayerAttributes() {
//        return playerAttributes;
//    }
//
//    public void setPlayerAttributes(GameObjectAttributes playerAttributes) {
//        this.playerAttributes = playerAttributes;
//    }
//
//    public GameObjectAttributes getEnemyAttributes() {
//        return enemyAttributes;
//    }
//
//    public void setEnemyAttributes(GameObjectAttributes enemyAttributes) {
//        this.enemyAttributes = enemyAttributes;
//    }
//
//    public GameObjectAttributes getEmptyAttributes() {
//        return emptyAttributes;
//    }
//
//    public void setEmptyAttributes(GameObjectAttributes emptyAttributes) {
//        this.emptyAttributes = emptyAttributes;
//    }
}
