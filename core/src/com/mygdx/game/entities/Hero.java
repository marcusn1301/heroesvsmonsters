package com.mygdx.game.entities;

import com.badlogic.ashley.core.Entity;
import com.mygdx.game.components.AttackDamageComponent;
import com.mygdx.game.components.AttackSpeedComponent;
import com.mygdx.game.components.HealthComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.SpriteComponent;

public class Hero extends Entity {
    public SpriteComponent sprite;
    public PositionComponent position;
    public AttackDamageComponent attackDamage;
    public AttackSpeedComponent attackSpeed;
    public HealthComponent health;
}