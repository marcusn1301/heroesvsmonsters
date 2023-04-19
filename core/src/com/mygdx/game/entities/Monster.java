package com.mygdx.game.entities;

import com.badlogic.ashley.core.Entity;
import com.mygdx.game.components.HealthComponent;
import com.mygdx.game.components.MovementSpeedComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.SpriteComponent;

public class Monster extends Entity {
    private SpriteComponent spriteComponent;
    private PositionComponent positionComponent;
    private MovementSpeedComponent movementSpeedComponent;
    private HealthComponent healthComponent;

    public SpriteComponent getSpriteComponent() {
        return spriteComponent;
    }

    public void setSpriteComponent(SpriteComponent spriteComponent) {
        this.spriteComponent = spriteComponent;
    }

    public PositionComponent getPositionComponent() {
        return positionComponent;
    }

    public void setPositionComponent(PositionComponent positionComponent) {
        this.positionComponent = positionComponent;
    }

    public MovementSpeedComponent getMovementSpeedComponent() {
        return movementSpeedComponent;
    }

    public void setMovementSpeedComponent(MovementSpeedComponent movementSpeedComponent) {
        this.movementSpeedComponent = movementSpeedComponent;
    }

    public HealthComponent getHealthComponent() {
        return healthComponent;
    }

    public void setHealthComponent(HealthComponent healthComponent) {
        this.healthComponent = healthComponent;
    }



}
