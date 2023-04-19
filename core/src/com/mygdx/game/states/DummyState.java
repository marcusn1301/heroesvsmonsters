package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

public class DummyState extends State {
    //Dummy class for GSM

    private GameStateManager gsm = GameStateManager.getGsm();

    private ArrayList<Float> bg = new ArrayList<>(Arrays.asList(0f,0f,1f,1f));

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        ScreenUtils.clear(Color.CORAL);
        sb.begin();
        sb.end();
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            bg.set(2, 0f);
            bg.set(1, 1f);
            gsm.push(new SettingsState(SettingsState.SettingsBackground.CITY));
        }
    }

    @Override
    public void dispose() {

    }
}
