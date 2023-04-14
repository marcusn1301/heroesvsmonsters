package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameMenuState extends ApplicationAdapter {
	SpriteBatch batch;
	Texture startButton;
	Texture lobbyButton;
	int width;
	int height;

	FireBaseInterface FBIC;


	public GameMenuState(FireBaseInterface FBIC) {
		this.FBIC = FBIC;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		startButton = new Texture("Start-button.png");
		lobbyButton = new Texture("Lobby-button.png");
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
		batch.draw(startButton, (width/2) - (height/2), height/4, startButton.getWidth()/2, startButton.getHeight()/2);
		batch.draw(lobbyButton, (width/2) - (height/2), height/6, lobbyButton.getWidth()/2, lobbyButton.getHeight()/2);

		batch.end();

		if (Gdx.input.isTouched()) {
			int x = Gdx.input.getX();
			int y = Gdx.input.getY();
			if (isStartButtonClicked(x, y)) {
				startGame();
			} else if (isLobbyButtonClicked(x, y)) {
				openLobby();
			}
		}
	}
	private boolean isStartButtonClicked(int x, int y) {
		int buttonX = (width/2) - (height/2);
		int buttonY = height/4;
		int buttonWidth = startButton.getWidth()/2;
		int buttonHeight = startButton.getHeight()/2;
		return (x >= buttonX && x <= buttonX + buttonWidth && y >= buttonY && y <= buttonY + buttonHeight);
	}

	private boolean isLobbyButtonClicked(int x, int y) {
		int buttonX = (width/2) - (height/2);
		int buttonY = height/6;
		int buttonWidth = lobbyButton.getWidth()/2;
		int buttonHeight = lobbyButton.getHeight()/2;
		System.out.println(buttonWidth);
		System.out.println(buttonHeight);

		return (x >= buttonX && x <= buttonX + buttonWidth && y >= buttonY && y <= buttonY + buttonHeight);
	}

	private void startGame() {
		// TODO: Add code to start the game
		System.out.println("Game started");
		System.out.println("--");

	}

	private void openLobby() {
		// TODO: Add code to open the lobby
		System.out.println("Lobby entered");

	}

	@Override
	public void dispose () {
		batch.dispose();
		startButton.dispose();
	}
}
