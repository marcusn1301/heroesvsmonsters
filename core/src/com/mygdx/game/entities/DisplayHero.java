package com.mygdx.game.entities;

import com.badlogic.ashley.core.Entity;
import com.mygdx.game.components.HeroComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.PriceComponent;
import com.mygdx.game.components.SpriteComponent;

public class DisplayHero extends Entity {
    private SpriteComponent spriteComponent;
    private PositionComponent positionComponent;
    private PriceComponent priceComponent;
    private HeroComponent heroComponent;

    public HeroComponent getHeroComponent() {
        return heroComponent;
    }

    public void setHeroComponent(HeroComponent heroComponent) {
        this.heroComponent = heroComponent;
    }


    public SpriteComponent getSpriteComponent() {
        return spriteComponent;
    }

    public void setSpriteComponent(SpriteComponent spriteComponent) {
        this.spriteComponent = spriteComponent;
    }

    public PositionComponent getPositionComponent() {
        return positionComponent;
    }

    public void setPositionComponent(PositionComponent positionComponent) {
        this.positionComponent = positionComponent;
    }

    public PriceComponent getPriceComponent() {
        return priceComponent;
    }

    public void setPriceComponent(PriceComponent priceComponent) {
        this.priceComponent = priceComponent;
    }



}
