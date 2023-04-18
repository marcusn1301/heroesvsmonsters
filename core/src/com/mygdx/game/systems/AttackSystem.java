package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.AttackComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.ProjectileComponent;
import com.mygdx.game.components.SpriteComponent;
import com.mygdx.game.components.VelocityComponent;

public class AttackSystem extends IteratingSystem {
    private ComponentMapper<AttackComponent> attackMapper;
    private ComponentMapper<CollisionComponent> collisionMapper;
    private ComponentMapper<PositionComponent> positionMapper;
    private ComponentMapper<SpriteComponent> spriteMapper;
    private Engine engine;
    private Entity projectile;


    public AttackSystem() {
        super(Family.all(AttackComponent.class, PositionComponent.class, CollisionComponent.class, SpriteComponent.class).get());
        attackMapper = ComponentMapper.getFor(AttackComponent.class);
        collisionMapper = ComponentMapper.getFor(CollisionComponent.class);
        positionMapper = ComponentMapper.getFor(PositionComponent.class);
        spriteMapper = ComponentMapper.getFor(SpriteComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AttackComponent attack = attackMapper.get(entity);
        CollisionComponent collision = collisionMapper.get(entity);
        PositionComponent position = positionMapper.get(entity);
        SpriteComponent sprite = spriteMapper.get(entity);

        attack.setAttackTimeElapsed(attack.getAttackTimeElapsed() + deltaTime);

        if (attack.getAttackTimeElapsed() >= attack.getAttackInterval()) {
            // Attack logic goes here
            attack.setAttackTimeElapsed(0);

            // Check if there is a projectile already
            projectile = null;
            for (Entity e : engine.getEntitiesFor(Family.all(ProjectileComponent.class, PositionComponent.class).get())) {
                if (e.getComponent(ProjectileComponent.class).getSourceEntity() == entity) {
                    projectile = e;
                    break;
                }
            }
            if (projectile == null) {
                // Create new projectile entity
                //TODO
                projectile = new Entity();
                projectile.add(new ProjectileComponent(true, new Vector2(0,0), 10, entity));
                projectile.add(new PositionComponent(new Vector2(0,0));
                projectile.add(new CollisionComponent(collision.width, collision.height));
                projectile.add(new DamageComponent(damage.damageValue, entity));
                engine.addEntity(projectile);
            } else {
                // Reset projectile position
                PositionComponent projectilePosition = projectile.getComponent(PositionComponent.class);
                projectilePosition.setPosition(new Vector2(0,0));
            }
        }
    }
}