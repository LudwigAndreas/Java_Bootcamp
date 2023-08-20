package edu.school21.tanksclient.models;

import edu.school21.tanksclient.utils.FloatVector;

import javafx.scene.image.Image;

public class GameObjectAttributes {
    private FloatVector position;
    private Float angle;
    private Image sprite;

    public GameObjectAttributes(FloatVector position, Float angle, Image image) {
        this.setPosition(position);
        this.setAngle(angle);
        this.setImage(image);
    }

    public FloatVector getPosition() {
        return position;
    }

    public void setPosition(FloatVector fv) {
        this.position = fv;
    }

    public Float getAngle() {
        return angle;
    }

    public void setAngle(Float angle) {
        this.angle = angle;
    }

    public Image getImage() {
        return sprite;
    }

    public void setImage(Image image) {
        this.sprite = image;
    }
}
