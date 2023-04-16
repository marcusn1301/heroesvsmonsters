package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;

public class MovementSpeedComponent implements Component {
    private float movementSpeed;
    public MovementSpeedComponent(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }
    public float getMovementSpeed() {
        return movementSpeed;
    }
    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
     }
}
