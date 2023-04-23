package com.mygdx.game.systems;

import static com.badlogic.gdx.math.MathUtils.random;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.WaveComponent;
import com.mygdx.game.entities.MonsterFactory;
import com.mygdx.game.types.MonsterType;

import java.util.Random;

public class WaveSystem extends IteratingSystem {
    private ComponentMapper<WaveComponent> waveMapper;
    private MonsterType[] monsterTypes;
    private Engine engine;
    private float[] rows = {Gdx.graphics.getHeight()/4, Gdx.graphics.getHeight()/5, Gdx.graphics.getHeight()/3, 0};

    public WaveSystem(Engine engine) {
        super(Family.all(WaveComponent.class).get());
        waveMapper = ComponentMapper.getFor(WaveComponent.class);
        this.engine = engine;
        initializeWaveSystem();
        monsterTypes = MonsterType.values();
        System.out.println("Wave 1 is starting in 5 seconds");
    }

    private void initializeWaveSystem() {
        //Initializes the first wave
        Entity waveEntity = new Entity();
        WaveComponent waveComponent = new WaveComponent(4, 1);
        waveEntity.add(waveComponent);
        engine.addEntity(waveEntity);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        WaveComponent wave = waveMapper.get(entity);

        //Wave duration is incremented
        wave.setWaveTimeElapsed(wave.getWaveTimeElapsed() + deltaTime);
        wave.setTimeSinceLastSpawn(wave.getTimeSinceLastSpawn() + deltaTime);

        //Activate the current wave
        if (wave.getWaveTimeElapsed() >= 5f && !wave.isActive()) {
            wave.setActive(true);
            System.out.println("Beginning wave " + wave.getWaveNumber());
            spawnMonster(wave);
            wave.setTimeSinceLastSpawn(0f);
        }

        //Begin a new wave after the given time
        if (wave.getWaveTimeElapsed() >= 100f) {
            //System.out.println("Ending wave " + wave.getWaveNumber());
            //wave.setActive(false);
            wave.setWaveNumber(wave.getWaveNumber() + 1);
            wave.setWaveTimeElapsed(0f);
            wave.setNumberOfMonsters(wave.getWaveNumber() + 2);
        }

        //Spawn monsters every 10 seconds
        if (wave.getTimeSinceLastSpawn() >= 5f && wave.isActive()) {
            spawnMonster(wave);
            wave.setTimeSinceLastSpawn(0f);
        }
    }

    private MonsterType getRandomType() {
        Random rand = new Random();
        return monsterTypes[rand.nextInt(monsterTypes.length)];
    }

    private void spawnMonster(WaveComponent wave) {
        wave.setTimeSinceLastSpawn(0f);
        //TODO monster should not be placed inside a cell
        Entity monster = MonsterFactory.createMonster(getRandomType(), new Vector2(1500f, getRandomRow()));

        engine.addEntity(monster);
    }

    private float getRandomRow() {
        return rows[random.nextInt(rows.length)];
    }
}
