package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;

public class AttackComponent implements Component {
    private float attackInterval;
    private float attackTimeElapsed;
    public AttackComponent(float attackInterval, float attackTimeElapsed) {
        this.attackInterval = attackInterval;
        this.attackTimeElapsed = attackTimeElapsed;

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


    public float getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(float attackDamage) {
        this.attackDamage = attackDamage;
    }

    public float getAttackSpeed() {
        return attackSpeed;
    }

    public void setAttackSpeed(float attackSpeed) {
        this.attackSpeed = attackSpeed;
    }
}
