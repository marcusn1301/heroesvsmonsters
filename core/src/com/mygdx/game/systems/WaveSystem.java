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
        if (wave.getWaveTimeElapsed() >= 3f && !wave.isActive()) {
            wave.setActive(true);
            System.out.println("Beginning wave " + wave.getWaveNumber());
        }

        //Begin a new wave after the given time
        if (wave.getNumberOfMonsters() <= 0) {
            //System.out.println("Ending wave " + wave.getWaveNumber());
            wave.setActive(false);
            wave.setWaveNumber(wave.getWaveNumber() + 1);
            wave.setWaveTimeElapsed(0f);
            wave.setNumberOfMonsters(wave.getWaveNumber() + 2);
        }
    }
}
