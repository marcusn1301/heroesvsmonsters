package com.mygdx.game.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {
    private final Stack<State> states;
    private static final GameStateManager gsm = new GameStateManager();

    private GameStateManager() {
        states = new Stack<>();
    }

    public static GameStateManager getGsm() {
        return gsm;
    }

    public void push(State state) {
        states.push(state);
    }
    public Stack<State> getStates() {
        return states;
    }

    public void pop() {
        states.pop();
    }

    public void set(State state) {
        states.pop();
        states.push(state);
    }

    public void update(float dt) {
        states.peek().update(dt);
    }

    public void render(SpriteBatch sb) {
        states.peek().render(sb);
    }
}
