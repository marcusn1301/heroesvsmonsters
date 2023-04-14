package com.mygdx.game;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AndroidInterfaceClass implements FireBaseInterface {
    FirebaseDatabase database;
    DatabaseReference myRef;

    public AndroidInterfaceClass() {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("message");
    }

    @Override
    public void SomeFunction() {
        System.out.println("some function");
    }

    @Override
    public void FirstFirebaseTest() {
        if (myRef != null) {
            myRef.setValue("hello world");
        } else {
            System.out.println("dbRef was not set. Could not write to database.");
        }
    }

    @Override
    public void SetOnValueChangedListener() {
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

    @Override
    public void SetValueInDb(String target, String value) {
        myRef = database.getReference(target);
        myRef.setValue(value);
    }
}
