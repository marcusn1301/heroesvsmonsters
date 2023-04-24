package com.mygdx.game;


import java.util.List;

public class CoreInterfaceClass implements FireBaseInterface {

    @Override
    public void SomeFunction() {
        System.out.println("wubbaaaadubaaa");
    }

    @Override
    public void FirstFirebaseTest() {

    }

    @Override
    public void SetOnValueChangedListener(String target) {

    }

    @Override
    public void SetValueInDb(String target, String name, Integer value) {

    }


    @Override
    public void getDataFromDatabase(String target, OnDataLoadedListener onDataLoadedListener) {

    }


}
