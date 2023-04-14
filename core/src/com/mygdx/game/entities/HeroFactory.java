package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.AttackDamageComponent;
import com.mygdx.game.components.AttackSpeedComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.SpriteComponent;
import com.mygdx.game.entities.Hero;
import com.mygdx.game.types.HeroType;

public class HeroFactory {
    public static Hero createHero(HeroType heroType) {
        Hero hero = new Hero();
        hero.sprite = new SpriteComponent(getSpriteForHero(heroType));
        hero.position = new PositionComponent(getStartingPositionForHero(heroType));
        hero.attackSpeed = new AttackSpeedComponent(getAttackSpeedForHero(heroType));
        hero.attackDamage = new AttackDamageComponent(getAttackDamageForHero(heroType));
        return hero;
    }

    private static TextureRegion getSpriteForHero(HeroType heroType) {
        //TODO endre path her
        switch (heroType) {
            case SUPERMAN:
                return new TextureRegion(new Texture(Gdx.files.internal("superman.png")));
            case IRONMAN:
                return new TextureRegion(new Texture(Gdx.files.internal("ironman.png")));
            case HULK:
                return new TextureRegion(new Texture(Gdx.files.internal("hulk.png")));
            case SPIDERMAN:
                return new TextureRegion(new Texture(Gdx.files.internal("spiderman.png")));
            case THOR:
                return new TextureRegion(new Texture(Gdx.files.internal("thor.png")));
            case CAPTAIN_AMERICA:
                return new TextureRegion(new Texture(Gdx.files.internal("captain_america.png")));
            default:
                return null;
        }
    }

    private static Vector2 getStartingPositionForHero(HeroType heroType) {
        // TODO Endre på dette. Position kommer ikke til å være
        // TODO hard-kodet
        switch (heroType) {
            case SUPERMAN:
                return new Vector2(50, 100);
            case IRONMAN:
                return new Vector2(50, 150);
            case HULK:
                return new Vector2(50, 200);
            case SPIDERMAN:
                return new Vector2(50, 250);
            case THOR:
                return new Vector2(50, 300);
            case CAPTAIN_AMERICA:
                return new Vector2(50, 350);
            default:
                return null;
        }
    }

    private static float getAttackSpeedForHero(HeroType heroType) {
        // TODO: Implement this method to return the appropriate attack speed for the hero type.
        switch (heroType) {
            case SUPERMAN:
                return 2f;
            case IRONMAN:
                return 4f;
            case HULK:
                return 1f;
            case SPIDERMAN:
                return 2.5f;
            case THOR:
                return 1.5f;
            case CAPTAIN_AMERICA:
                return 5f;
            default:
                return 0;
        }
    }

    private static float getAttackDamageForHero(HeroType heroType) {
        switch (heroType) {
            case SUPERMAN:
                return 20f;
            case IRONMAN:
                return 15f;
            case HULK:
                return 30f;
            case SPIDERMAN:
                return 10f;
            case THOR:
                return 35f;
            case CAPTAIN_AMERICA:
                return 50f;
            default:
                return 0;
        }
    }
}
