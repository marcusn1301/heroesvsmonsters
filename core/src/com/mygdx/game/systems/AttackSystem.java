package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.components.AttackComponent;
import com.mygdx.game.components.PositionComponent;

public class AttackSystem extends IteratingSystem {

    private ComponentMapper<AttackComponent> attackMapper;

    public AttackSystem() {
        super(Family.all(AttackComponent.class, PositionComponent.class).get());
        attackMapper = ComponentMapper.getFor(AttackComponent.class);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        AttackComponent attackComponent = attackMapper.get(entity);

        attackComponent.setAttackTimeElapsed(attackComponent.getAttackTimeElapsed() + deltaTime);

        if (attackComponent.getAttackTimeElapsed() >= attackComponent.getAttackInterval()) {
            // Attack logic goes here
            attackComponent.setAttackTimeElapsed(0);
        }
    }
}