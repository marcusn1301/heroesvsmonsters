package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;

public class AttackSpeedComponent implements Component {
    private float attackSpeed;
    public AttackSpeedComponent(float attackSpeed) {
        this.attackSpeed = attackSpeed;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(float attackSpeed) {
        this.attackSpeed = attackSpeed;
    }
}
