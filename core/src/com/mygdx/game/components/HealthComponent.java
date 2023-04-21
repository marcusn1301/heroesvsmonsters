package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;

public class HealthComponent implements Component {
    private int health;
    public HealthComponent(int health) {
        this.health = health;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
