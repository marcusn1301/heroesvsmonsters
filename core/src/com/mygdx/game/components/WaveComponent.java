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
    private int monstersKilled;
    private int monstersToKill;
    private int score;

    public WaveComponent(int numberOfMonsters, int waveNumber) {
        this.numberOfMonsters = numberOfMonsters;
        this.waveNumber = waveNumber;
        this.timeSinceLastSpawn = 0f;
        this.isActive = false;
        this.spawnTimer = 5f;
        this.waveTimeElapsed = 0f;
        this.monstersKilled = 0;
        this.monstersToKill = numberOfMonsters;
        this.score = 0;
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
    public int getMonstersKilled() {
        return monstersKilled;
    }

    public void setMonstersKilled(int monstersKilled) {
        this.monstersKilled = monstersKilled;
    }

    public int getMonstersToKill() {
        return monstersToKill;
    }

    public void setMonstersToKill(int monstersToKill) {
        this.monstersToKill = monstersToKill;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
