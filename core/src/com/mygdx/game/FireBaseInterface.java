package com.mygdx.game;

import java.util.List;

public interface FireBaseInterface {
    public void SomeFunction();
    public void FirstFirebaseTest();
    public void SetOnValueChangedListener(String target);
    public void SetValueInDb(String target, Integer value);

    void getDataFromDatabase(String target, OnDataLoadedListener onDataLoadedListener);

    interface OnDataLoadedListener {
        void onDataLoaded(List<Integer> values);
        void onError(Exception exception);
    }
}
