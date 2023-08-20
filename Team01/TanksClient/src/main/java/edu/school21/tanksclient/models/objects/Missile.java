package edu.school21.tanksclient.models.objects;

import edu.school21.tanksclient.utils.FloatVector;

import edu.school21.tanksclient.models.GameObjectAttributes;
import javafx.scene.image.Image;

public class Missile extends MovableGameObject {
	private Float damage;
	// private static Image

	public Missile(FloatVector position, Float angle, Image image, Float damage) {
		super(position, angle, image);
		setMissileSpecificFields(damage);
	}

	public Missile(GameObjectAttributes attrs, Float damage) {
		super(attrs);
		setMissileSpecificFields(damage);
	}

	private void setMissileSpecificFields(Float damage) {
		this.setDamage(damage);
		this.setSpeed(50f);
	}

	public Float getDamage() {
		return damage;
	}

	public void setDamage(Float f) {
		this.damage = f;
	}
}
