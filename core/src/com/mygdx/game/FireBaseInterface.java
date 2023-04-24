package com.mygdx.game;

import java.util.ArrayList;
import java.util.Map;

public interface FireBaseInterface {
    public void SomeFunction();
    public void FirstFirebaseTest();
    public void SetOnValueChangedListener(String target);
    public void SetValueInDb(String target, String name, Integer value);

    void getDataFromDatabase(String target, OnDataLoadedListener onDataLoadedListener);

    interface OnDataLoadedListener {
        void onDataLoaded(ArrayList<Map<String, Object>> values);
        void onError(Exception exception);
    }
}
