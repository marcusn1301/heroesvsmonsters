package com.mygdx.game.entities;

import com.badlogic.ashley.core.Entity;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.PriceComponent;
import com.mygdx.game.components.SpriteComponent;

public class DisplayHero extends Entity {
    public SpriteComponent sprite;
    public PositionComponent position;
    public PriceComponent price;
}
