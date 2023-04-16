package com.mygdx.game.entities;

import com.mygdx.game.components.AttackDamageComponent;
import com.mygdx.game.components.AttackSpeedComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.SpriteComponent;
import com.mygdx.game.types.HeroType;

public class MonsterFactory {
    public static Monster createMonster(MonsterType monsterType) {
        Monster monster = new Monster();
        hero.sprite = new SpriteComponent(getSpriteForHero(heroType));
        hero.position = new PositionComponent(getStartingPositionForHero(heroType));
        hero.attackSpeed = new AttackSpeedComponent(getAttackSpeedForHero(heroType));
        hero.attackDamage = new AttackDamageComponent(getAttackDamageForHero(heroType));
        return hero;
    }
}
