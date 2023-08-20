package edu.school21.tanksclient.models.objects;

import edu.school21.tanksclient.models.GameObjectAttributes;
import edu.school21.tanksclient.utils.FloatVector;
import javafx.scene.image.Image;

public class PlayerTank extends Tank {

	private Float rotate_speed = 1f;

	public enum Direction {
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

	public void rotate(Float angle) {
		setAngle((getAngle() + angle + 360) % 360);
		FloatVector mov = new FloatVector(
			new Float(Math.cos(getAngle())), 
			new Float(Math.sin(getAngle()))
		);
		setMovement(mov);
	}

	public void move(Direction dir) {
		switch (dir) {
			case UP:
				FloatVector pos = new FloatVector(
					getPosition().getX() + getMovement().getX(), 
					getPosition().getY() + getMovement().getY()
				);
				setPosition(pos);
				break;
			case DOWN:
				FloatVector pos = new FloatVector(
					getPosition().getX() - getMovement().getX(), 
					getPosition().getY() - getMovement().getY()
				);
				setPosition(pos);
				break;
			case LEFT:
				rotate(rotate_speed);
				break;
			case RIGHT:
				rotate(-rotate_speed);
				break;
		}
	}

	public void fireMissile() {
		
	}
}
