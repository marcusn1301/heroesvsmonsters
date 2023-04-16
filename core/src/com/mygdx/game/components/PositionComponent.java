package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class PositionComponent implements Component {
    private Vector2 position;
    public PositionComponent(Vector2 startingPosition) {
        this.position = startingPosition;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
}
