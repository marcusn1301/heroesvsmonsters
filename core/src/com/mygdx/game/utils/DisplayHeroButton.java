package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.mygdx.game.entities.DisplayHero;
import com.mygdx.game.types.HeroType;

public class DisplayHeroButton {
    Vector2 position;
    Texture texture;
    HeroType heroType;
    Button button;

    public DisplayHeroButton(DisplayHero displayHero) {
        this.position = displayHero.getPositionComponent().getPosition();
        this.texture = displayHero.getSpriteComponent().getSprite();
        this.heroType = displayHero.get
        this.button = new Button();

    }

    public void setButton(Button button) {
        this.button = button;
    }
    public Button getButton() {
        return button;
    }


    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
