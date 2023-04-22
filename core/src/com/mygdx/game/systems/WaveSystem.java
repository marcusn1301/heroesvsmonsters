package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.components.WaveComponent;
import com.mygdx.game.entities.MonsterFactory;

public class WaveSystem extends IteratingSystem {
    private ComponentMapper<WaveComponent> waveMapper;
    private Engine engine;

    public WaveSystem(Engine engine) {
        super(Family.all(WaveComponent.class).get());
        waveMapper = ComponentMapper.getFor(WaveComponent.class);
        this.engine = engine;
        initializeWaveSystem();
        System.out.println("Wave 1 is starting in 5 seconds");
    }

    private void initializeWaveSystem() {
        //Initializes the first wave
        Entity waveEntity = new Entity();
        WaveComponent waveComponent = new WaveComponent(1, 1);
        waveEntity.add(waveComponent);
        engine.addEntity(waveEntity);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        WaveComponent wave = waveMapper.get(entity);

        //Wave duration is incremented
        wave.setWaveTimeElapsed(wave.getWaveTimeElapsed() + deltaTime);

        //Activate the current wave
        if (wave.getWaveTimeElapsed() >= 5f && !wave.isActive()) {
            wave.setActive(true);
            System.out.println("Beginning wave " + wave.getWaveNumber());
        }

        //Begin a new wave after the given time
        if (wave.getWaveTimeElapsed() >= 10f) {
            System.out.println("Ending wave " + wave.getWaveNumber());
            wave.setActive(false);
            wave.setWaveNumber(wave.getWaveNumber() + 1);
            wave.setWaveTimeElapsed(0f);
            wave.setNumberOfMonsters(wave.getWaveNumber() + 2);
        }
    }
}
