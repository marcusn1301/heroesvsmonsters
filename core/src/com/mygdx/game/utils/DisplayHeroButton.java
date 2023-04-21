package com.mygdx.game.utils;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.mygdx.game.entities.DisplayHero;
import com.mygdx.game.types.HeroType;

public class DisplayHeroButton {
    Vector2 position;
    float width;
    float height;
    Texture texture;
    HeroType heroType;
    int price;


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public HeroType getHeroType() {
        return heroType;
    }

    public void setHeroType(HeroType heroType) {
        this.heroType = heroType;
    }

    public DisplayHeroButton(DisplayHero displayHero) {
        this.position = displayHero.getPositionComponent().getPosition();
        this.texture = displayHero.getSpriteComponent().getSprite();
        this.heroType = displayHero.getHeroComponent().getHeroType();
        this.height = displayHero.getSpriteComponent().getSprite().getHeight() * 4;
        this.width = displayHero.getSpriteComponent().getSprite().getWidth() * 4;
        this.price = displayHero.getPriceComponent().getPrice();
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

