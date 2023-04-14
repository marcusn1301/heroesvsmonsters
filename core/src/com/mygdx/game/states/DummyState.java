package com.mygdx.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class DummyState extends State {
    //Dummy class for GSM

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        ScreenUtils.clear(0,0,1,1);
        sb.begin();
        sb.end();
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void dispose() {

    }
}
