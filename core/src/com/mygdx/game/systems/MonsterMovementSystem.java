package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.MonsterComponent;
import com.mygdx.game.components.PositionComponent;

public class MonsterMovementSystem extends IteratingSystem {
    private ComponentMapper<MonsterComponent> monsterMapper;
    private ComponentMapper<PositionComponent> positionMapper;
    private ComponentMapper<CollisionComponent> collisionMapper;
    private Engine engine;

    public MonsterMovementSystem(Engine engine) {
        super(Family.all(MonsterComponent.class, PositionComponent.class).get());
        monsterMapper = ComponentMapper.getFor(MonsterComponent.class);
        positionMapper = ComponentMapper.getFor(PositionComponent.class);
        collisionMapper = ComponentMapper.getFor(CollisionComponent.class);
        this.engine = engine;

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MonsterComponent monster = monsterMapper.get(entity);
        PositionComponent monsterPos = positionMapper.get(entity);
        CollisionComponent collision = collisionMapper.get(entity);

        //Make the monster move to the left
        monsterPos.setPosition(monsterPos.getPosition().add(monster.getMovementSpeed() * -1, 0));
        collision.setHitbox(collision.getHitbox().setPosition(monsterPos.getPosition()));

        //Remove monsters that are out of bounds
        if (monsterPos.getPosition().x <= 0 + Gdx.graphics.getWidth() / 8) {
            entity.removeAll();
            engine.removeEntity(entity);
        }


    }
}
