package com.apcsfinal.ludus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SearchResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
     /*
        for (DataSnapshot user : dataSnapshot.getChildren()) {
            Log.d(TAG, "PARENT: " + user.getKey());
            Log.d(TAG, "Name: " + user.child("name").getValue());
        }
     */
    }
}
