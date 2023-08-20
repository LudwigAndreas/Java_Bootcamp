package edu.school21.tanksclient.models.objects;

import edu.school21.tanksclient.utils.FloatVector;

import edu.school21.tanksclient.models.GameObjectAttributes;

import javafx.scene.image.Image;

public abstract class GameObject {
	private FloatVector position;
	private Float angle;
	private Image sprite;

	public GameObject(FloatVector position, Float angle, Image image) {
		this.setPosition(position);
		this.setAngle(angle);
		this.setImage(image);
	}

	public GameObject(GameObjectAttributes attrs) {
		this.setPosition(attrs.getPosition());
		this.setAngle(attrs.getAngle());
		this.setImage(attrs.getImage());
	}

	public void setPosition(FloatVector fv) {
		this.position = fv;
	}

	public FloatVector getPosition() {
		return position;
	}

	public void setAngle(Float f) {
		this.angle = f;
	}

	public Float getAngle() {
		return angle;
	}

	public void setImage(Image i) {
		this.sprite = i;
	}

	public Image getImage() { 
		return sprite;
	}
}
