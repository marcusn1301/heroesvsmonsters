package com.mygdx.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.AttackComponent;
import com.mygdx.game.components.CollisionComponent;
import com.mygdx.game.components.HealthComponent;
import com.mygdx.game.components.MonsterComponent;
import com.mygdx.game.components.MovementSpeedComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.PriceComponent;
import com.mygdx.game.components.SpriteComponent;
import com.mygdx.game.types.HeroType;
import com.mygdx.game.types.MonsterType;

public class MonsterFactory {
    public static Entity createMonster(MonsterType monsterType, Vector2 boardPosition) {
        Entity monster = new Entity();
        monster.add(new SpriteComponent(getMonsterSprite(monsterType)));
        monster.add(new PositionComponent(boardPosition));
        monster.add(new MonsterComponent(monsterType, getMonsterMovementSpeed(monsterType)));
        monster.add(new CollisionComponent(new Rectangle(boardPosition.x, boardPosition.y, Gdx.graphics.getWidth()/30f, Gdx.graphics.getHeight()/10f)));
        monster.add(new HealthComponent(2));
        return monster;
    }

    public static Entity createDisplayMonster(MonsterType monsterType, Vector2 boardPosition) {
        Entity monster = new Entity();
        monster.add(new SpriteComponent(getMonsterSprite(monsterType)));
        monster.add(new PositionComponent(boardPosition));
        monster.add(new MonsterComponent(monsterType, getMonsterMovementSpeed(monsterType)));
        monster.add(new PriceComponent(getMonsterPrice(monsterType)));
        return monster;
    }


    private static Texture getMonsterSprite(MonsterType monsterType) {
        //TODO legg til riktig bilde
        switch (monsterType) {
            case MAGNETO:
                return new Texture(Gdx.files.internal("images/Monster_4.png"));
            case JUGGERNAUT:
                return new Texture(Gdx.files.internal("images/Monster_5.png"));
            case VENOM:
                return new Texture(Gdx.files.internal("images/Monster_1.png"));
            case HOBGOBLIN:
                return new Texture(Gdx.files.internal("images/Monster_3.png"));
            case GOBLIN_GLIDER:
                return new Texture(Gdx.files.internal("images/Monster_2.png"));
            case MYSTIQUE:
                return new Texture(Gdx.files.internal("images/Monster_6.png"));
            default:
                return null;
        }
    }

    private static float getMonsterMovementSpeed(MonsterType monsterType) {
        switch (monsterType) {
            case MAGNETO:
                return 16f;
            case JUGGERNAUT:
                return 13f;
            case VENOM:
                return 14f;
            case HOBGOBLIN:
                return 12f;
            case GOBLIN_GLIDER:
                return 10f;
            case MYSTIQUE:
                return 15f;
            default:
                return 10f;
        }
    }

    private static float getMonsterAttackDamage(MonsterType monsterType) {
        switch (monsterType) {
            case MAGNETO:
                return 20f;
            case JUGGERNAUT:
                return 15f;
            case VENOM:
                return 30f;
            case HOBGOBLIN:
                return 10f;
            case GOBLIN_GLIDER:
                return 35f;
            case MYSTIQUE:
                return 50f;
            default:
                return 0;
        }
    }

    private static int getMonsterPrice(MonsterType monsterType) {
        switch (monsterType) {
            case MAGNETO:
                return 50;
            case JUGGERNAUT:
                return 100;
            case VENOM:
                return 250;
            case HOBGOBLIN:
                return 300;
            case GOBLIN_GLIDER:
                return 400;
            case MYSTIQUE:
                return 650;
            default:
                return 0;
        }
    }

}
