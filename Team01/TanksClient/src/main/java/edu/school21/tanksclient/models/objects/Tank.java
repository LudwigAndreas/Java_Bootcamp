package edu.school21.tanksclient.models.objects;

import edu.school21.tanksclient.utils.FloatVector;

import edu.school21.tanksclient.models.GameObjectAttributes;
import javafx.scene.image.Image;

public abstract class Tank extends MovableGameObject {

	private Integer hp;
	private Integer full_hp;
	private Integer ammo_damage;

	public Tank(FloatVector position, Float angle, Image image) {
		super(position, angle, image);
		setTankSpecificFields();
	}

	public Tank(GameObjectAttributes attrs) {
		super(attrs);
		setTankSpecificFields();
	}

	private void setTankSpecificFields() {
		this.full_hp = 100;
		this.hp = this.full_hp;
		this.ammo_damage = 5;
		this.setSpeed(20f);	
	}

	public Integer getFullHp() {
		return full_hp;
	}

	public Integer getAmmoDamage() {
		return ammo_damage;
	}

	public Integer getHp() {
		return hp;
	}

	public void deductHp(Integer damage) {
		hp -= damage;
		if (hp < 0) hp = 0;
	}

	public boolean isAlive() {
		return hp > 0;
	}
}
