package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Rectangle;

public class CollisionComponent implements Component {
    private Rectangle hitbox;
    public CollisionComponent(Rectangle hitbox) {
        this.hitbox = hitbox;
    }
    public Rectangle getHitbox() {
        return hitbox;
    }

    public void setHitbox(Rectangle hitbox) {
        this.hitbox = hitbox;
    }

}
