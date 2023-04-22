package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.mygdx.game.types.HeroType;

public class HeroComponent implements Component {
    private HeroType heroType;
    private boolean projectileActive;

    public boolean isProjectileActive() {
        return projectileActive;
    }

    public void setProjectileActive(boolean projectileActive) {
        this.projectileActive = projectileActive;
    }


    public HeroComponent(HeroType heroType) {
        this.heroType = heroType;
        this.projectileActive = false;
    }

    public HeroType getHeroType() {
        return heroType;
    }

    public void setHeroType(HeroType heroType) {
        this.heroType = heroType;
    }

}
