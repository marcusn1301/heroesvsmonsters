package com.mygdx.game.entities;

import com.badlogic.ashley.core.Entity;
import com.mygdx.game.components.AttackDamageComponent;
import com.mygdx.game.components.AttackSpeedComponent;
import com.mygdx.game.components.HealthComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.SpriteComponent;

public class Hero extends Entity {
    private SpriteComponent spriteComponent;
    private PositionComponent positionComponent;
    private AttackSpeedComponent attackSpeedComponent;
    private AttackDamageComponent attackDamageComponent;
    private HealthComponent healthComponent;

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

    public AttackSpeedComponent getAttackSpeedComponent() {
        return attackSpeedComponent;
    }

    public void setAttackSpeedComponent(AttackSpeedComponent attackSpeedComponent) {
        this.attackSpeedComponent = attackSpeedComponent;
    }

    public AttackDamageComponent getAttackDamageComponent() {
        return attackDamageComponent;
    }

    public void setAttackDamageComponent(AttackDamageComponent attackDamageComponent) {
        this.attackDamageComponent = attackDamageComponent;
    }

    public HealthComponent getHealthComponent() {
        return healthComponent;
    }

    public void setHealthComponent(HealthComponent healthComponent) {
        this.healthComponent = healthComponent;
    }



}