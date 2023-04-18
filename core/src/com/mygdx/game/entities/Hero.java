package com.mygdx.game.entities;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.mygdx.game.components.AttackComponent;
import com.mygdx.game.components.HealthComponent;
import com.mygdx.game.components.HeroComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.SpriteComponent;

public class Hero extends Entity {
    private SpriteComponent spriteComponent;
    private PositionComponent positionComponent;
    private AttackComponent attackComponent;
    private HealthComponent healthComponent;


    private HeroComponent typeComponent;
    private Engine engine;
    public HeroComponent getTypeComponent() {
        return typeComponent;
    }

    public void setTypeComponent(HeroComponent typeComponent) {
        this.typeComponent = typeComponent;
    }

    public void attack() {
        AttackComponent attack = getComponent(AttackComponent.class);
        if (attack.getAttackTimeElapsed() >= attack.getAttackInterval()) {
            // Create projectile and assign source entity as this
            Entity projectile = ProjectileFactory.createProjectile(typeComponent.getHeroType(), positionComponent.getPosition(), 5, this);
            engine.addEntity(projectile);
            attack.setAttackTimeElapsed(0);
        }
    }

    public SpriteComponent getSpriteComponent() {
        return spriteComponent;
    }

    public void setSpriteComponent(SpriteComponent spriteComponent) {
        this.spriteComponent = spriteComponent;
    }

    public PositionComponent getPositionComponent() {
        return positionComponent;
    }

    public void setPositionComponent(PositionComponent positionComponent) {
        this.positionComponent = positionComponent;
    }

    public AttackComponent getAttackComponent() {
        return attackComponent;
    }

    public void setAttackComponent(AttackComponent attackComponent) {
        this.attackComponent = attackComponent;
    }

    public HealthComponent getHealthComponent() {
        return healthComponent;
    }

    public void setHealthComponent(HealthComponent healthComponent) {
        this.healthComponent = healthComponent;
    }
}