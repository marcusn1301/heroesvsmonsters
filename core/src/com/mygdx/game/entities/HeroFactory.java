package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.components.AttackDamageComponent;
import com.mygdx.game.components.AttackSpeedComponent;
import com.mygdx.game.components.PositionComponent;
import com.mygdx.game.components.PriceComponent;
import com.mygdx.game.components.SpriteComponent;
import com.mygdx.game.types.HeroType;

public class HeroFactory {
    //Creates a hero that is placed on the board
    public static Hero createHero(HeroType heroType, Vector2 boardPosition) {
        Hero hero = new Hero();
        hero.sprite = new SpriteComponent(getHeroSprite(heroType));
        hero.position = new PositionComponent(boardPosition);
        hero.attackSpeed = new AttackSpeedComponent(getHeroAttackSpeed(heroType));
        hero.attackDamage = new AttackDamageComponent(getHeroAttackDamage(heroType));
        return hero;
    }

    //Create a display version of the hero on the left-hand side of the screen
    public static DisplayHero createDisplayHero(HeroType heroType) {
        DisplayHero displayHero = new DisplayHero();
        displayHero.sprite = new SpriteComponent(getHeroSprite(heroType));
        displayHero.position = new PositionComponent(getHeroStartingPosition(heroType));
        displayHero.price = new PriceComponent(getHeroPrice(heroType));
        return displayHero;
    }
    
    private static TextureRegion getHeroSprite(HeroType heroType) {
        //TODO endre path her
        Texture texture = null;
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

    private static Vector2 getHeroStartingPosition(HeroType heroType) {
        // Position for the display heroes
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

    private static float getHeroAttackSpeed(HeroType heroType) {
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

    private static float getHeroAttackDamage(HeroType heroType) {
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

    private static int getHeroPrice(HeroType heroType) {
        switch (heroType) {
            case SUPERMAN:
                return 400;
            case IRONMAN:
                return 350;
            case HULK:
                return 500;
            case SPIDERMAN:
                return 200;
            case THOR:
                return 300;
            case CAPTAIN_AMERICA:
                return 650;
            default:
                return 0;
        }
    }
}
