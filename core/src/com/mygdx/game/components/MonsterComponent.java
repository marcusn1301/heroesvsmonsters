package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.mygdx.game.types.MonsterType;

public class MonsterComponent implements Component {
    public MonsterComponent(MonsterType monsterType) {
        this.monsterType = monsterType;
    }

    public MonsterType getMonsterType() {
        return monsterType;
    }

    public void setMonsterType(MonsterType monsterType) {
        this.monsterType = monsterType;
    }

    private MonsterType monsterType;
}
