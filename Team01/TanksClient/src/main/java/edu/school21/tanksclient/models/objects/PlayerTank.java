package edu.school21.tanksclient.models.objects;

import edu.school21.tanksclient.models.GameObjectAttributes;
import edu.school21.tanksclient.utils.FloatVector;
import javafx.scene.image.Image;

public class PlayerTank extends Tank {

	enum Direction {
		UP,
		DOWN,
		LEFT,
		RIGHT
	}

	public PlayerTank(FloatVector position, Float angle, Image image) {
		super(position, angle, image);
	}

	public PlayerTank(GameObjectAttributes attrs) {
		super(attrs);
	}

	public void move(Direction dir) {

	}
}
