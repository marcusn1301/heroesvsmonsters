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

public class Game extends ApplicationAdapter {
	private SpriteBatch batch;
	private GameStateManager gsm;

	FireBaseInterface FBIC;


	public Game(FireBaseInterface FBIC) {
		this.FBIC = FBIC;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		//gms = new GameMenuState();
		//gms.create();
		//gsm = GameStateManager.getGsm();
		//gsm.push(new GameMenuState());
		gsm = GameStateManager.getGsm();
		gsm.push(new GameMenuState());
		FBIC.SomeFunction();
		FBIC.FirstFirebaseTest();
		FBIC.SetOnValueChangedListener();
		FBIC.SetValueInDb("message", "Updated message!");
	}

	@Override
	public void render () {
		ScreenUtils.clear(Color.BLUE);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		//gsm.push(new GameMenuState());
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);

		batch.end();
	}



	@Override
	public void dispose () {
		batch.dispose();
	}
}
