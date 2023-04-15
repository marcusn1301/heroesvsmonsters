package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;

public class SpriteComponent implements Component {
    private Texture sprite;
    public SpriteComponent(Texture sprite) {
        this.sprite = sprite;
    }

    public Texture getSprite() {
        return sprite;
    }

    public void setSprite(Texture sprite) {
        this.sprite = sprite;
    }
}
