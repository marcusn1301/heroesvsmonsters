package com.mygdx.game.states;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Board;

public class StartState extends State implements InputProcessor {
    SpriteBatch batch;
    Board board;


    public StartState() {
        initialize();
    }

    public void initialize() {
        batch = new SpriteBatch();
        //board = new Board(6, 9, engine);

        Texture texture = new Texture("characterIcon1.png");
        Texture texture1 = new Texture("characterIcon2.png");

        board.setTexture(0, 0, texture);
        board.setTexture(0, 1, texture);
        board.setTexture(0, 2, texture);
        board.setTexture(0, 3, texture);
        board.setTexture(0, 4, texture);
        board.setTexture(0, 5, texture);
        board.setTexture(0, 6, texture);
        board.setTexture(0, 7, texture);
        board.setTexture(0, 8, texture);

        board.setTexture(1, 2, texture);
        board.setTexture(2, 2, texture);
        board.setTexture(3, 2, texture);
        board.setTexture(4, 2, texture);
        board.setTexture(5, 2, texture);

        board.setTexture(1, 3, texture1);
        board.setTexture(2, 3, texture1);
        board.setTexture(3, 3, texture1);
        board.setTexture(4, 3, texture1);
        board.setTexture(5, 3, texture1);


        board.setTexture(4, 5, texture1);



    }
    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch batch) {
        ScreenUtils.clear(Color.DARK_GRAY);
        batch.begin();

        int rows = board.getRows();
        int cols = board.getCols();

        // Retrieve the textures array from the board
        Texture[][] textures = board.getTextures();

        //Render board
        board.render(batch);


        batch.end();

    }

    @Override
    public void handleInput() {

    }

    @Override
    public void dispose() {

    }

    /*
     * Methods for movement-functionality
     */

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
