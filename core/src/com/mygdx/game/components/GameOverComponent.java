package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;

public class GameOverComponent implements Component {

    private boolean isGameOver;

    public GameOverComponent() {
        this.isGameOver = false;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }
}
