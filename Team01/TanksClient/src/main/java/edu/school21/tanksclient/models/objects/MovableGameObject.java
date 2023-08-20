package edu.school21.tanksclient.models.objects;

import edu.school21.tanksclient.utils.FloatVector;

import edu.school21.tanksclient.models.GameObjectAttributes;
import javafx.scene.image.Image;

public abstract class MovableGameObject extends GameObject {
	private FloatVector movement;
	private Float speed;

	public MovableGameObject(FloatVector position, Float angle, Image image) {
		super(position, angle, image);
	}

	public MovableGameObject(GameObjectAttributes attrs) {
		super(attrs);
	}

	public void setMovement(FloatVector fv) {
		this.movement = fv;
	}

	public FloatVector getMovement() {
		return movement;
	}

	public Float getSpeed() {
		return speed;
	}

	protected void setSpeed(Float speed) { 
		this.speed = speed;
	}
}
