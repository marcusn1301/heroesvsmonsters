package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;

public class PriceComponent implements Component {
    private int price;
    public PriceComponent(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
