package com.mygdx.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.types.MonsterType;

public class WaveComponent implements Component {
    private float timeSinceLastSpawn;
    private int numberOfMonsters;
    private boolean isActive;
    private int waveNumber;
    private float spawnTimer;
    private float waveTimeElapsed;

    public WaveComponent(int numberOfMonsters, int waveNumber) {
        this.numberOfMonsters = numberOfMonsters;
        this.waveNumber = waveNumber;
        this.timeSinceLastSpawn = 0f;
        this.isActive = false;
        this.spawnTimer = 5f;
        this.waveTimeElapsed = 0f;
    }

    public float getSpawnTimer() {
        return spawnTimer;
    }

    public void setSpawnTimer(float spawnTimer) {
        this.spawnTimer = spawnTimer;
    }

    public int getNumberOfMonsters() {
        return numberOfMonsters;
    }

    public void setNumberOfMonsters(int numberOfMonsters) {
        this.numberOfMonsters = numberOfMonsters;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getWaveNumber() {
        return waveNumber;
    }

    public void setWaveNumber(int waveNumber) {
        this.waveNumber = waveNumber;
    }

    public float getTimeSinceLastSpawn() {
        return timeSinceLastSpawn;
    }

    public void setTimeSinceLastSpawn(float timeSinceLastSpawn) {
        this.timeSinceLastSpawn = timeSinceLastSpawn;
    }

    public float getWaveTimeElapsed() {
        return waveTimeElapsed;
    }

    public void setWaveTimeElapsed(float waveTimeElapsed) {
        this.waveTimeElapsed = waveTimeElapsed;
    }
}