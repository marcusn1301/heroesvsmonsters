package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.components.AttackComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.HealthComponent;
import com.mygdx.game.components.PositionComponent;

public class CollisionSystem extends IteratingSystem {
    private ComponentMapper<HealthComponent> healthMapper;
    private ComponentMapper<CollisionComponent> collisionMapper;

    public CollisionSystem() {
        super(Family.all(AttackComponent.class, PositionComponent.class, HealthComponent.class, CollisionComponent.class).get());
        healthMapper = ComponentMapper.getFor(HealthComponent.class);
        collisionMapper = ComponentMapper.getFor(CollisionComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}
