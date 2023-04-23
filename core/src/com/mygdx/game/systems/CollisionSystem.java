package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.mygdx.game.components.AttackComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.HealthComponent;
import com.mygdx.game.components.HeroComponent;
import com.mygdx.game.components.MonsterComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.ProjectileComponent;
import com.mygdx.game.components.SpriteComponent;
import com.mygdx.game.components.WaveComponent;

public class CollisionSystem extends IteratingSystem {
    private ComponentMapper<HealthComponent> healthMapper;
    private ComponentMapper<PositionComponent> positionMapper;
    private ComponentMapper<CollisionComponent> collisionMapper;
    private ComponentMapper<MonsterComponent> monsterMapper;
    private ComponentMapper<ProjectileComponent> projectileMapper;
    private ComponentMapper<HeroComponent> heroMapper;
    private ComponentMapper<WaveComponent> waveMapper;
    private ImmutableArray<Entity> monsters;
    private Engine engine;

    public CollisionSystem(Engine engine) {
        super(Family.all(PositionComponent.class, CollisionComponent.class).get());
        positionMapper = ComponentMapper.getFor(PositionComponent.class);
        collisionMapper = ComponentMapper.getFor(CollisionComponent.class);
        monsterMapper = ComponentMapper.getFor(MonsterComponent.class);
        projectileMapper = ComponentMapper.getFor(ProjectileComponent.class);
        heroMapper = ComponentMapper.getFor(HeroComponent.class);
        waveMapper = ComponentMapper.getFor(WaveComponent.class);
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        PositionComponent position = positionMapper.get(entity);
        CollisionComponent collision = collisionMapper.get(entity);
        ProjectileComponent projectile = projectileMapper.get(entity);
        HeroComponent hero = heroMapper.get(entity);

        //Check if projectile has collided with a monster
        if (projectileMapper.has(entity)) {
            for (Entity monster : engine.getEntitiesFor(Family.all(MonsterComponent.class, CollisionComponent.class).get())) {
                CollisionComponent monsterCollision = collisionMapper.get(monster);
                if (collision.getHitbox().overlaps(monsterCollision.getHitbox())) {
                    engine.removeEntity(entity);
                    engine.removeEntity(monster);
                    Entity waveEntity = engine.getEntitiesFor(Family.all(WaveComponent.class).get()).first();
                    WaveComponent waveComponent = waveMapper.get(waveEntity);
                    waveComponent.setMonstersKilled(waveComponent.getMonstersKilled() + 1);
                    break;
                }
            }
        }

        //Check if a hero entity is colliding with a monster
        if (heroMapper.has(entity)) {
            for (Entity monster : engine.getEntitiesFor(Family.all(MonsterComponent.class, CollisionComponent.class).get())) {
                CollisionComponent monsterCollision = collisionMapper.get(monster);
                if (collision.getHitbox().overlaps(monsterCollision.getHitbox())) {
                    engine.removeEntity(entity);
                    engine.removeEntity(monster);
                    //game over
                    engine.removeSystem(engine.getSystem(HeroSystem.class));
                }
            }
        }
    }
}
