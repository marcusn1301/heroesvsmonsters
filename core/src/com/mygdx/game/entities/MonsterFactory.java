package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.AttackDamageComponent;
import com.mygdx.game.components.MovementSpeedComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.SpriteComponent;
import com.mygdx.game.types.HeroType;
import com.mygdx.game.types.MonsterType;

public class MonsterFactory {
    public static Monster createMonster(MonsterType monsterType, Vector2 boardPosition) {
        Monster monster = new Monster();
        monster.setSpriteComponent(new SpriteComponent(getMonsterSprite(monsterType)));
        monster.setPositionComponent(new PositionComponent(boardPosition));
        monster.setMovementSpeedComponent(new MovementSpeedComponent(getMonsterMovementSpeed(monsterType)));
        monster.setAttackDamageComponent(new AttackDamageComponent(getMonsterAttackDamage(monsterType)));
        return monster;
    }

    private static Texture getMonsterSprite(MonsterType monsterType) {
        //TODO endre path her
        Texture texture = null;
        switch (monsterType) {
            case MAGNETO:
                return new Texture(Gdx.files.internal("characterIcon3.png"));
            case JUGGERNAUT:
                return new Texture(Gdx.files.internal("characterIcon1.png"));
            case VENOM:
                return new Texture(Gdx.files.internal("Monster_3.png"));
            case HOBGOBLIN:
                return new Texture(Gdx.files.internal("Monster_2.png"));
            case GOBLIN_GLIDER:
                return new Texture(Gdx.files.internal("Monster_1.png"));
            case MYSTIQUE:
                return new Texture(Gdx.files.internal("characterIcon6.png"));
            default:
                return null;
        }
    }

    private static float getMonsterMovementSpeed(MonsterType monsterType) {
        switch (monsterType) {
            case MAGNETO:
                return 2f;
            case JUGGERNAUT:
                return 4f;
            case VENOM:
                return 1f;
            case HOBGOBLIN:
                return 2.5f;
            case GOBLIN_GLIDER:
                return 1.5f;
            case MYSTIQUE:
                return 5f;
            default:
                return 0;
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

}
