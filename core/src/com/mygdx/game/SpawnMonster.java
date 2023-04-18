package com.mygdx.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.entities.Monster;
import com.mygdx.game.entities.MonsterFactory;
import com.mygdx.game.types.MonsterType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpawnMonster {
    private MonsterFactory monsterFactory;
    private float spawnTimer;
    private float spawnInterval;
    private int level;
    private int row;
    private int col;
    private List<Monster> monsters;
    private final Array<MonsterType> monsterTypes = new Array<>(MonsterType.values());


    public SpawnMonster(int row, int col, int level) {
        this.level = level;
        this.spawnTimer = 0f;
        this.spawnInterval = 2f - (float) Math.log(level);
        this.row = row;
        this.col = col;
        monsters = new ArrayList<>();
    }

    public void update(float delta) {
        spawnTimer += delta;
        if (spawnTimer >= spawnInterval) {
            spawnMonster();
            spawnTimer = 0f;
        }
    }

    private void spawnMonster() {
        Vector2 position = new Vector2(2, 3);
        monsterFactory.createMonster(MonsterType.MAGNETO, position);


        // Get a random monster type from the available types
        MonsterType monsterType = monsterTypes.random();

        // Set the position of the monster on the right edge of the screen
        Vector2 monsterPosition = new Vector2(row - 1, MathUtils.random(0, col - 1));

        // Create the monster using the MonsterFactory
        Monster monster = MonsterFactory.createMonster(monsterType, monsterPosition);
    }
}
