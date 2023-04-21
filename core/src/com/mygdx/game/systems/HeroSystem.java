package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.AttackComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.HeroComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.ProjectileComponent;
import com.mygdx.game.components.SpriteComponent;
import com.mygdx.game.entities.ProjectileFactory;
import com.badlogic.gdx.utils.Logger;

public class HeroSystem extends IteratingSystem {
    private ComponentMapper<AttackComponent> attackMapper;
    private ComponentMapper<CollisionComponent> collisionMapper;
    private ComponentMapper<PositionComponent> positionMapper;
    private ComponentMapper<SpriteComponent> spriteMapper;
    private ComponentMapper<HeroComponent> heroTypeMapper;
    private Engine engine;

    //This system is only responsible for handling the creation of projectiles for all the heroes
    //This is done based on an interval that is set in the AttackComponent
    public HeroSystem(Engine engine) {
        //Retrieves all the hero-entities with the given components
        super(Family.all(AttackComponent.class, HeroComponent.class).get());
        attackMapper = ComponentMapper.getFor(AttackComponent.class);
        positionMapper = ComponentMapper.getFor(PositionComponent.class);
        spriteMapper = ComponentMapper.getFor(SpriteComponent.class);
        heroTypeMapper = ComponentMapper.getFor(HeroComponent.class);
        this.engine = engine;
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        //Components for the hero that is being processed
        AttackComponent attack = attackMapper.get(entity);
        PositionComponent position = positionMapper.get(entity);
        SpriteComponent sprite = spriteMapper.get(entity);
        HeroComponent heroComponent = heroTypeMapper.get(entity);

        //Increase the time elapsed field in the attack component on every update
        attack.setAttackTimeElapsed(attack.getAttackTimeElapsed() + deltaTime);

        //Check if a hero has an active projectile
        boolean projectileExists = false;
        for (Entity e : engine.getEntitiesFor(Family.all(ProjectileComponent.class, PositionComponent.class).get())) {
            if (e.getComponent(ProjectileComponent.class).getSourceEntity() == entity) {
                //System.out.println(e.getComponent(PositionComponent.class).getPosition());
                projectileExists = true;
                break;
            }
        }

        //If a hero does not have an active projectile, it is created and added to the engine
        if (!projectileExists) {
            float posX = position.getPosition().x;
            float posY = position.getPosition().y;
            Entity projectile = ProjectileFactory.createProjectile(heroComponent.getHeroType(), new Vector2(posX, posY), entity);
            engine.addEntity(projectile);
        }

        //If the time elapsed is greater than the given interval, create a new projectile
        if (attack.getAttackTimeElapsed() > attack.getAttackInterval()) {
            attack.setAttackTimeElapsed(0);
        }

        for (Entity e : engine.getEntitiesFor(Family.all(ProjectileComponent.class, PositionComponent.class).get())) {
            if (e.getComponent(ProjectileComponent.class).getSourceEntity() == entity) {
                //System.out.println(e.getComponent(PositionComponent.class).getPosition());
            }
        }
    }
}