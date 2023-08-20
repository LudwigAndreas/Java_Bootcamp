package edu.school21.tanksclient.models.objects;

import edu.school21.tanksclient.utils.FloatVector;

import edu.school21.tanksclient.models.GameObjectAttributes;
import javafx.scene.image.Image;

public class EnemyTank extends Tank {

	public EnemyTank(FloatVector position, Float angle, Image image) {
		super(position, angle, image);
	}

	public EnemyTank(GameObjectAttributes attrs) {
		super(attrs);
	}
}
