package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;

public class AttackComponent implements Component {
    private float damage;
    private float attackInterval;
    private float attackTimeElapsed;

    public AttackComponent(float attackInterval, float attackTimeElapsed, float damage) {
        this.attackInterval = attackInterval;
        this.attackTimeElapsed = attackTimeElapsed;
        this.damage = damage;

    }
    public float getDamage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }


    public float getAttackTimeElapsed() {
        return attackTimeElapsed;
    }

    public void setAttackTimeElapsed(float attackTimeElapsed) {
        this.attackTimeElapsed = attackTimeElapsed;
    }

    public float getAttackInterval() {
        return attackInterval;
    }
    public void setAttackInterval(float attackInterval) {
        this.attackInterval = attackInterval;
    }
}
