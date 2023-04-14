package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.states.GameMenuState;
import com.mygdx.game.states.GameStateManager;

public class HeroesVsMonsters extends ApplicationAdapter {
	SpriteBatch batch;
	private GameStateManager gsm;
	//FireBaseInterface FBIC;


	public HeroesVsMonsters(FireBaseInterface FBIC) {
		//this.FBIC = FBIC;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		gsm = GameStateManager.getGsm();
		gsm.push(new GameMenuState());
		/*FBIC.SomeFunction();
		FBIC.FirstFirebaseTest();
		FBIC.SetOnValueChangedListener();
		FBIC.SetValueInDb("message", "Updated message!");*/
	}

	@Override
	public void render () {
		ScreenUtils.clear(Color.BLACK);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}

	@Override
	public void dispose () {
		batch.dispose();
	}

}