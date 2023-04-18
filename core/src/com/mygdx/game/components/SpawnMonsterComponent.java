package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;

public class SpawnMonsterComponent implements Component {
    public float spawnTimer;
    public float spawnInterval;
    public int level;
    public int row;
    public int col;
}
