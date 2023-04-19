package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.ProjectileComponent;

public class ProjectileMovementSystem extends IteratingSystem {
    private ComponentMapper<ProjectileComponent> projectileMapper;
    private ComponentMapper<CollisionComponent> collisionMapper;
    private ComponentMapper<PositionComponent> positionMapper;

    private Engine engine;


    public ProjectileMovementSystem() {
        super(Family.all(ProjectileComponent.class, CollisionComponent.class).get());
        projectileMapper = ComponentMapper.getFor(ProjectileComponent.class);
        collisionMapper = ComponentMapper.getFor(CollisionComponent.class);
        positionMapper = ComponentMapper.getFor(PositionComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ProjectileComponent projectileComponent = projectileMapper.get(entity);
        CollisionComponent collisionComponent = collisionMapper.get(entity);
        PositionComponent positionComponent = positionMapper.get(entity);

        //TODO: Check if projectile is out of bounds
        float posY = positionComponent.getPosition().y;
        float posX = positionComponent.getPosition().x;
        float speed = projectileComponent.getVelocity();
        float newPosX = posX + speed;
        positionComponent.setPosition(new Vector2(newPosX, posY));
    }
}
