package com.mygdx.game;

public class FirebaseManager {
    private static FirebaseManager instance;
    private FireBaseInterface firebaseInterface;

    private FirebaseManager(FireBaseInterface firebaseInterface) {
        this.firebaseInterface = firebaseInterface;
    }

    public static void initialize(FireBaseInterface firebaseInterface) {
        if (instance == null) {
            instance = new FirebaseManager(firebaseInterface);
        }
    }

    public static FirebaseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("FirebaseManager not initialized");
        }
        return instance;
    }

    public FireBaseInterface getFirebaseInterface() {
        return firebaseInterface;
    }
}

