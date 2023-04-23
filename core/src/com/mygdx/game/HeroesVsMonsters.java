package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.states.GameMenuState;
import com.mygdx.game.states.GameStateManager;
import com.mygdx.game.states.LeaderboardState;

public class HeroesVsMonsters extends ApplicationAdapter {
	private GameStateManager gsm;
	//FireBaseInterface FBIC;

	private static SpriteBatch sb;
	//FireBaseInterface FBIC;

	public HeroesVsMonsters(FireBaseInterface FBIC) {
		FirebaseManager.initialize(FBIC);

		//this.FBIC = FBIC;
	}

	@Override
	public void create () {
		sb = new SpriteBatch();
		gsm = GameStateManager.getGsm();
		/*
		FBIC.SomeFunction();
		FBIC.FirstFirebaseTest();
		FBIC.SetOnValueChangedListener();
		FBIC.SetValueInDb("message", "Updated message!");
		*/
		gsm = GameStateManager.getGsm();
		gsm.push(new GameMenuState());
	}

	@Override
	public void render () {
		ScreenUtils.clear(Color.BLACK);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(sb);
	}

	@Override
	public void dispose () {
		sb.dispose();
	}

	public static SpriteBatch getSb() {
		return sb;
	}
}