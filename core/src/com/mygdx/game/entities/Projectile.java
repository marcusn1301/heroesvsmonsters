package com.mygdx.game.entities;

import com.badlogic.ashley.core.Entity;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.ProjectileComponent;
import com.mygdx.game.components.SpriteComponent;

public class Projectile extends Entity {
    private CollisionComponent collisionComponent;
    private SpriteComponent spriteComponent;
    private ProjectileComponent projectileComponent;

    public PositionComponent getPositionComponent() {
        return positionComponent;
    }

    public void setPositionComponent(PositionComponent positionComponent) {
        this.positionComponent = positionComponent;
    }

    private PositionComponent positionComponent;
    public ProjectileComponent getProjectileComponent() {
        return projectileComponent;
    }

    public void setProjectileComponent(ProjectileComponent projectileComponent) {
        this.projectileComponent = projectileComponent;
    }

    public SpriteComponent getSpriteComponent() {
        return spriteComponent;
    }

    public void setSpriteComponent(SpriteComponent spriteComponent) {
        this.spriteComponent = spriteComponent;
    }

    public CollisionComponent getCollisionComponent() {
        return collisionComponent;
    }

    public void setCollisionComponent(CollisionComponent collisionComponent) {
        this.collisionComponent = collisionComponent;
    }

}
