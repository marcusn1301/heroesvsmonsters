package com.mygdx.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class State {

    protected State(){}

    public abstract void update(float dt);
    public abstract void render(SpriteBatch batch);
    public abstract void handleInput();

    public abstract void dispose();
}
