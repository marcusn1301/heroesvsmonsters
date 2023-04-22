package com.mygdx.game.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.mygdx.game.components.AttackComponent;
import com.mygdx.game.components.MonsterComponent;

public class MonsterSystem extends IteratingSystem {
    private ComponentMapper<MonsterComponent> monsterMapper;
    private ComponentMapper<AttackComponent> attackMapper;
    private Engine engine;
    public MonsterSystem(Engine engine) {
        super(Family.all(MonsterComponent.class, AttackComponent.class).get());
        monsterMapper = ComponentMapper.getFor(MonsterComponent.class);
        attackMapper = ComponentMapper.getFor(AttackComponent.class);
        this.engine = engine;

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MonsterComponent monster = monsterMapper.get(entity);
        AttackComponent attack = attackMapper.get
    }
}
