package com.mygdx.game.systems;

import com.mygdx.game.MoneyObserver;

import java.util.ArrayList;
import java.util.List;

public class MoneySystem {
    private static MoneySystem instance;
    private int money;
    private final int initialMoney = 1000; // Declare initialMoney as a field
    private MoneyChangeListener moneyChangeListener;

    private MoneySystem(int initialMoney) {
        this.money = initialMoney;
    }

    public static MoneySystem getInstance() {
        if (instance == null) {
            instance = new MoneySystem(1000); // You can set your initial money value here
        }
        return instance;
    }

    // Add this method to reset the money to the initial amount
    public void resetMoney() {
        money = initialMoney;
        if (moneyChangeListener != null) {
            moneyChangeListener.onMoneyChanged();
        }
    }

    public void setMoneyChangeListener(MoneyChangeListener moneyChangeListener) {
        this.moneyChangeListener = moneyChangeListener;
    }

    public void addMoney(int amount) {
        money += amount;
        if (moneyChangeListener != null) {
            moneyChangeListener.onMoneyChanged();
        }
    }

    public void setMoney(int amount) {
        money += amount;
    }

    public boolean removeMoney(int amount) {
        if (money >= amount) {
            money -= amount;
            if (moneyChangeListener != null) {
                moneyChangeListener.onMoneyChanged();
            }
            return true;
        } else {
            return false;
        }
    }

    public int getMoney() {
        return money;
    }

    public interface MoneyChangeListener {
        void onMoneyChanged();
    }
}
