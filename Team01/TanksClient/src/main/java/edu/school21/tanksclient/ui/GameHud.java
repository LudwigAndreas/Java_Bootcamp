package edu.school21.tanksclient.ui;

import edu.school21.tanksclient.game.Game;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * @author billyu
 * in game info display
 */
public class GameHud {
	private Text timeHud;
	private Text livesHud;
	private Text levelHud;
	private Game game;
	
	public GameHud(Game game) {
		this.game = game;
	}
	
	public Node initHud() {
		livesHud = new Text();
		configureGameHud(livesHud);
		timeHud = new Text();
		configureGameHud(timeHud);
		levelHud = new Text();
		configureGameHud(levelHud);
		HBox box = new HBox();
		box.getChildren().addAll(livesHud, timeHud, levelHud);
		box.setSpacing(200);
		BorderPane.setAlignment(box, Pos.CENTER);
		return box;
	}
	
	public void updateHud() {
		updateLivesHud();
		updateTimeHud();
		updateLevelHud();
	}
	
	public double getHeight() {
		return livesHud.getLayoutBounds().getHeight();
	}
	
	private void configureGameHud(Text hud) {
		hud.setFont(new Font(20));
		hud.setFill(Color.WHITE);
	}
	
	public void updateLivesHud() {
		livesHud.setText(String.format("Lives: %d", game.getLives()));
	}
	
	public void updateTimeHud() {
		timeHud.setText("Time: " + (Game.GAME_TIME_SECONDS - (System.nanoTime() - game.getStartTime()) / 1000000000L));
	}
	
	public void updateLevelHud() {
		levelHud.setText("Level: " + game.getCurrentLevel());
	}
}
