package com.mygdx.game.states;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameViewState extends State {
    private GameStateManager gsm;

    public GameViewState() {

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
}
