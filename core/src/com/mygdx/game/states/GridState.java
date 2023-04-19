package com.mygdx.game.states;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GridState extends State {
    private GameStateManager gsm;

    public GridState() {
        super();
        gsm = GameStateManager.getGsm();
    }

    public void create() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch batch) {
        ScreenUtils.clear(Color.PINK);
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void dispose() {

    }
}
