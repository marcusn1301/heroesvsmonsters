package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.mygdx.game.types.HeroType;

public class HeroComponent implements Component {
    private HeroType heroType;

    public HeroComponent(HeroType heroType) {
        this.heroType = heroType;
    }

    public HeroType getHeroType() {
        return heroType;
    }

    public void setHeroType(HeroType heroType) {
        this.heroType = heroType;
    }

}
