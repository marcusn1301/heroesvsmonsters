package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;

public class AttackDamageComponent implements Component {
    private float attackDamage;
    public AttackDamageComponent(float attackDamage) {
        this.attackDamage = attackDamage;
    }

    public float getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(float attackDamage) {
        this.attackDamage = attackDamage;
    }
}
