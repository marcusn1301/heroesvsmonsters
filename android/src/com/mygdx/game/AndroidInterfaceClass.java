package com.mygdx.game;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AndroidInterfaceClass implements FireBaseInterface {
    FirebaseDatabase database;
    DatabaseReference myRef;

    public AndroidInterfaceClass() {
        database = FirebaseDatabase.getInstance();
        //myRef = database.getReference("highScores");
    }

    @Override
    public void SomeFunction() {
        System.out.println("some function");
    }

    @Override
    public void FirstFirebaseTest() {
       /* if (myRef != null) {
            myRef.setValue("hello world");
        } else {
            System.out.println("dbRef was not set. Could not write to database.");
        }*/
    }

    @Override
    public void SetOnValueChangedListener(String target) {
        System.out.println("Yes I am running");
        myRef = database.getReference(target);

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                List<Integer> values = new ArrayList<>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Integer value = childSnapshot.getValue(Integer.class);
                    values.add(value);
                }
                Log.d(TAG, "Values are: " + values);
                System.out.println("Values are: " + values);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
                System.out.println("FAT Error");
            }
        });
    }


    @Override
    public void SetValueInDb(String target, Integer value) {
        DatabaseReference targetRef = database.getReference(target);

        // Read current maximum key from the database
        targetRef.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long newKey = 1; // Default key if there are no keys yet
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    // Get the current maximum key and increment it
                    newKey = Long.parseLong(childSnapshot.getKey()) + 1;
                }
                // Set the new value with the incremented key
                targetRef.child(String.valueOf(newKey)).setValue(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error :(");
            }
        });
    }

    @Override
    public void getDataFromDatabase(String target, OnDataLoadedListener onDataLoadedListener) {
        DatabaseReference targetRef = database.getReference(target);

        targetRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Integer> values = new ArrayList<>();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Integer value = childSnapshot.getValue(Integer.class);
                    values.add(value);
                }
                onDataLoadedListener.onDataLoaded(values);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onDataLoadedListener.onError(error.toException());
            }
        });
    }


}
