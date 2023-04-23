package com.mygdx.game;

// This class is a singleton that manages the Firebase interface and ensures that there is only one
//instance of the interface across the entire application.
public class FirebaseManager {
    private static FirebaseManager instance;
    private FireBaseInterface firebaseInterface;

    private FirebaseManager(FireBaseInterface firebaseInterface) {
        this.firebaseInterface = firebaseInterface;
    }

    // Initializes the FirebaseManager singleton.
    public static void initialize(FireBaseInterface firebaseInterface) {
        if (instance == null) {
            instance = new FirebaseManager(firebaseInterface);
        }
    }

    // Returns the single instance of the FirebaseManager class.
    public static FirebaseManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("FirebaseManager is not initialized");
        }
        return instance;
    }

    // This method returns the FirebaseInterface instance
    //which can be used to access the methods to interact with Firebase services.
    public FireBaseInterface getFirebaseInterface() {
        return firebaseInterface;
    }
}

