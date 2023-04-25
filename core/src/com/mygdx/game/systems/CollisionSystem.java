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

public class

CollisionSystem extends IteratingSystem {
    private ComponentMapper<CollisionComponent> collisionMapper;
    private ComponentMapper<ProjectileComponent> projectileMapper;
    private ComponentMapper<HeroComponent> heroMapper;
    private ComponentMapper<WaveComponent> waveMapper;
    private ComponentMapper<HealthComponent> healthMapper;
    private ImmutableArray<Entity> monsters;
    private Engine engine;
    private MoneySystem moneySystem = MoneySystem.getInstance();


    public CollisionSystem(Engine engine) {
        super(Family.all(PositionComponent.class, CollisionComponent.class).get());
        collisionMapper = ComponentMapper.getFor(CollisionComponent.class);
        projectileMapper = ComponentMapper.getFor(ProjectileComponent.class);
        heroMapper = ComponentMapper.getFor(HeroComponent.class);
        waveMapper = ComponentMapper.getFor(WaveComponent.class);
        healthMapper = ComponentMapper.getFor(HealthComponent.class);
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CollisionComponent collision = collisionMapper.get(entity);

        //Check if projectile has collided with a monster
        if (projectileMapper.has(entity)) {
            for (Entity monster : engine.getEntitiesFor(Family.all(MonsterComponent.class, CollisionComponent.class).get())) {
                CollisionComponent monsterCollision = collisionMapper.get(monster);
                HealthComponent monsterHealth = healthMapper.get(monster);
                if (collision.getHitbox().overlaps(monsterCollision.getHitbox())) {
                    //Remove projectile on collision
                    engine.removeEntity(entity);
                    if (monsterHealth.getHealth() - 1 <= 0) {
                        // Add 100 coins when a monster dies
                        moneySystem.addMoney(150);
                        engine.removeEntity(monster);
                        Entity waveEntity = engine.getEntitiesFor(Family.all(WaveComponent.class).get()).first();
                        WaveComponent waveComponent = waveMapper.get(waveEntity);
                        //Increase the kill amount
                        waveComponent.setMonstersKilled(waveComponent.getMonstersKilled() + 1);
                        //Increase the score
                        waveComponent.setScore(waveComponent.getScore() + 500);
                        break;
                    } else {
                        monsterHealth.setHealth(monsterHealth.getHealth() - 1);
                    }
                }
            }
        }

        //Check if a hero entity is colliding with a monster
        if (heroMapper.has(entity)) {
            for (Entity monster : engine.getEntitiesFor(Family.all(MonsterComponent.class, CollisionComponent.class).get())) {
                CollisionComponent monsterCollision = collisionMapper.get(monster);
                if (collision.getHitbox().overlaps(monsterCollision.getHitbox())) {
                    engine.removeEntity(entity);
                    engine.removeSystem(engine.getSystem(HeroSystem.class));
                }
            }
        }
    }
}
