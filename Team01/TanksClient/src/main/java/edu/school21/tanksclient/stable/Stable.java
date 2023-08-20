package edu.school21.tanksclient.stable;

import edu.school21.tanksclient.game.Game;
import edu.school21.tanksclient.sprite.Sprite;

/**
 * @author billyu
 * elements that do not move during the game
 */
public abstract class Stable extends Sprite {
	protected static String imageFile;
	
	public Stable() {
		setImageFile();
		setImage(imageFile);
		BITMASK = Game.STABLE_MASK;
	}
	
	protected abstract void setImageFile();
}