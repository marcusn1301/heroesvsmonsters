package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;

public class HealthComponent implements Component {
    private float health;
    public HealthComponent(float health) {
        this.health = health;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }
}
