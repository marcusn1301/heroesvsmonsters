package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.ProjectileComponent;

public class ProjectileMovementSystem extends IteratingSystem {
    private ComponentMapper<ProjectileComponent> projectileMapper;
    private ComponentMapper<CollisionComponent> collisionMapper;
    private ComponentMapper<PositionComponent> positionMapper;
    private Engine engine;

    //The system is only responsible for handling the position of the projectiles.
    public ProjectileMovementSystem(Engine engine) {
        //Retrieves all the projectiles and set the components
        super(Family.one(ProjectileComponent.class).get());
        projectileMapper = ComponentMapper.getFor(ProjectileComponent.class);
        collisionMapper = ComponentMapper.getFor(CollisionComponent.class);
        positionMapper = ComponentMapper.getFor(PositionComponent.class);
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ProjectileComponent projectileComponent = projectileMapper.get(entity);
        CollisionComponent collisionComponent = collisionMapper.get(entity);
        PositionComponent positionComponent = positionMapper.get(entity);

        //If a projectile is out of bounds, remove it from the engine
        if (positionComponent.getPosition().x > Gdx.graphics.getWidth() - Gdx.graphics.getWidth()/4f) {
            entity.removeAll();
            engine.removeEntity(entity);
        }

        //Increment the horizontal position of the projectile based on its velocity
        //and update hitbox position
        float speed = projectileComponent.getVelocity();
        positionComponent.setPosition(positionComponent.getPosition().add(speed*deltaTime, 0));
        collisionComponent.setHitbox(collisionComponent.getHitbox().setPosition(positionComponent.getPosition()));

    }
}
