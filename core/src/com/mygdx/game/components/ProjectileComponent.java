package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

public class ProjectileComponent implements Component {
    private float velocity;
    private Entity sourceEntity;


    public ProjectileComponent(boolean isActive, float velocity, Entity sourceEntity) {
        this.velocity = velocity;
        this.sourceEntity = sourceEntity;
    }

    public Entity getSourceEntity() {
        return sourceEntity;
    }

    public void setSourceEntity(Entity sourceEntity) {
        this.sourceEntity = sourceEntity;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }
}
