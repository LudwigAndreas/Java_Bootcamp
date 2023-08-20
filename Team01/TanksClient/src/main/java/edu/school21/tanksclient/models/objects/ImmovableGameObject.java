package edu.school21.tanksclient.models.objects;

import edu.school21.tanksclient.utils.FloatVector;

import edu.school21.tanksclient.models.GameObjectAttributes;
import javafx.scene.image.Image;

public abstract class ImmovableGameObject extends GameObject {

	public ImmovableGameObject(FloatVector position, Float angle, Image image) {
		super(position, angle, image);
	}

	public ImmovableGameObject(GameObjectAttributes attrs) {
		super(attrs);
	}
}
