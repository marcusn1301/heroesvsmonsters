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
import com.mygdx.game.utils.Enums;

import java.util.ArrayList;
import java.util.Random;

public class WaveSystem extends IteratingSystem {
    private ComponentMapper<WaveComponent> waveMapper;
    private MonsterType[] monsterTypes;
    private Engine engine;
    private Vector2 monsterPos;
    private int gameWidth;
    private int gameHeight;
    private int cellHeight;
    private int rowCount;
    private ArrayList<Integer> rows;
    private boolean isSinglePlayer;

    public WaveSystem(Engine engine, boolean isSinglePlayer) {
        super(Family.all(WaveComponent.class).get());
        waveMapper = ComponentMapper.getFor(WaveComponent.class);
        this.engine = engine;
        initializeWaveSystem();
        monsterTypes = MonsterType.values();
        System.out.println("Wave 1 is starting in 5 seconds");
        this.isSinglePlayer = isSinglePlayer;
        monsterTypes = MonsterType.values();
        gameWidth = Gdx.graphics.getWidth() - Gdx.graphics.getWidth()/6;
        gameHeight = Gdx.graphics.getHeight()*2;
        rowCount = 6;
        cellHeight = gameHeight / rowCount;
        rows = new ArrayList<Integer>();
        initRows();
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
        wave.setScore(wave.getScore() + 1);
        wave.setTimeSinceLastSpawn(wave.getTimeSinceLastSpawn() + deltaTime);
        wave.setWaveTimeElapsed(wave.getWaveTimeElapsed() + deltaTime);

        //Begin new wave 5 seconds after all monsters are gone
        if (wave.getWaveTimeElapsed() >= 5f && !wave.isActive()) {
            wave.setActive(true);
            System.out.println("Beginning wave " + wave.getWaveNumber());
            wave.setWaveNumber(wave.getWaveNumber() + 1);
            wave.setWaveTimeElapsed(0f);
            wave.setNumberOfMonsters((wave.getWaveNumber() + 1) * 2 + 1);
            wave.setMonstersToKill((wave.getWaveNumber() + 1) * 2 + 1);
            wave.setMonstersKilled(0);
        }

        //End wave and begin timer when all monsters are killed
        if (wave.getMonstersToKill() == 0 && wave.isActive()) {
            //System.out.println("Ending wave " + wave.getWaveNumber());
            wave.setActive(false);
        }

        if (this.isSinglePlayer && wave.getNumberOfMonsters() > 0) {
            if (wave.getTimeSinceLastSpawn() > 5f) {
                spawnMonster();
                wave.setNumberOfMonsters(wave.getNumberOfMonsters() - 1);
                wave.setTimeSinceLastSpawn(0);
            }
        }

    }

        private void spawnMonster() {
            int monsterType = random.nextInt(monsterTypes.length);
            int randomRow = random.nextInt(rows.size());
            System.out.println("row: " + rows.get(randomRow));
            monsterPos = new Vector2(gameWidth, rows.get(randomRow));
            Entity monster = MonsterFactory.createMonster(monsterTypes[monsterType], monsterPos);
            engine.addEntity(monster);
        }

        private void initRows() {
            for (int i = 0; i < rowCount; i++) {
                rows.add((cellHeight/2) * i);
            }
        }
}
