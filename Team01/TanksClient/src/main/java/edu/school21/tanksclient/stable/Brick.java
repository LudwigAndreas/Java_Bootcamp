package edu.school21.tanksclient.stable;

import edu.school21.tanksclient.game.Game;
import edu.school21.tanksclient.sprite.Sprite;

/**
 * @author billyu
 * brick
 * can be destroyed by missile
 */
public final class Brick extends Stable {
	
	public Brick() {
		super();
	}

	protected void dealWithCollision(Sprite s) {
		switch (s.getBITMASK()) {
			case Game.PLAYER_MISSILE_MASK:
			case Game.ENEMY_MISSILE_MASK:
				health--;
		}
	}

	@Override
	protected void setImageFile() {
		imageFile = "brick.gif";
	}
}
