package edu.school21.tanksclient.stable;

import edu.school21.tanksclient.sprite.Sprite;

/**
 * @author billyu
 * tanks/missiles can hide under grass
 */
public class Grass extends Stable {
	
	public Grass() {
		super();
	}

	@Override
	protected void setImageFile() {
		imageFile = "grass.gif";
	}

	@Override
	protected void dealWithCollision(Sprite s) {
	}
}
