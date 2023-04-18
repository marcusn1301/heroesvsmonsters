package com.mygdx.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.components.AttackComponent;
import com.mygdx.game.components.HeroComponent;

public class HeroSystem extends IteratingSystem {
    private float timeSinceLastAttack;
    private float attackInterval;

    public HeroSystem() {
        super(Family.all(HeroComponent.class, AttackComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        timeSinceLastAttack += deltaTime;

        if (timeSinceLastAttack >= attackInterval) {
            // Reset the timer
            timeSinceLastAttack = 0f;

            // Call the attack method on the hero entity
            entity.getComponent(HeroComponent.class).attack();
        }
    }
}