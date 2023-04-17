package com.mygdx.game;

import com.mygdx.game.entities.MonsterFactory;

import java.util.Random;

public class MonsterSpawner {
    private Board board;
    private MonsterFactory monsterFactory;
    private float spawnTimer;
    private float spawnInterval;
    private int

    public MonsterSpawner(Board board, int level) {
        this.board = board;
        this.level = level;
        this.spawnTimer = 0f;
        this.spawnInterval = 2f - (float) Math.log(level);
    }

    public void update(float delta) {
        spawnTimer += delta;
        if (spawnTimer >= spawnInterval) {
            spawnMonster();
            spawnTimer = 0f;
        }
    }

    private void spawnMonster() {
        int numRows = board.getNumRows();
        int numCols = board.getNumCols();
        int row = new Random().nextInt(numRows);
        int col = numCols - 1;
        int type = new Random().nextInt(6); // There are 6 types of monsters
        board.setCell(row, col, type);
    }

}
