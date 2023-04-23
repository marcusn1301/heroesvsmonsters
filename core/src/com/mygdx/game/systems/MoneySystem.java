package com.mygdx.game.systems;

public class MoneySystem {
    private int money;

    public MoneySystem(int initialMoney) {
        this.money = initialMoney;
    }

    public void addMoney(int amount) {
        money += amount;
    }

    public boolean removeMoney(int amount) {
        if (money >= amount) {
            money -= amount;
            return true;
        } else {
            return false;
        }
    }

    public int getMoney() {
        return money;
    }
}

