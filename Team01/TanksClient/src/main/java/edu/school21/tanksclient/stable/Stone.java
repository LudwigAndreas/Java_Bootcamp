package edu.school21.tanksclient.stable;

import edu.school21.tanksclient.sprite.Sprite;

/**
 * @author billyu
 * cannot be destroyed whatsoever
 */
public final class Stone extends Stable {
	
	public Stone() {
		super();
	}

	@Override
	protected void setImageFile() {
		imageFile = "stone.gif";
	}

	@Override
	protected void dealWithCollision(Sprite s) {
	}
}