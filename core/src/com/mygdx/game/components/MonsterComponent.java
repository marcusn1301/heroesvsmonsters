package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.mygdx.game.types.MonsterType;

public class MonsterComponent implements Component {
    private MonsterType monsterType;
    private float movementSpeed;
    public MonsterComponent(MonsterType monsterType, float movementSpeed) {
        this.monsterType = monsterType;
        this.movementSpeed = movementSpeed;
    }

    public MonsterType getMonsterType() {
        return monsterType;
    }

    public void setMonsterType(MonsterType monsterType) {
        this.monsterType = monsterType;
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(float movementSpeed) {
        this.movementSpeed = movementSpeed;
    }
}
