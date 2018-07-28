package com.apcsfinal.ludus;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.content.Intent;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(MainActivity.this, SessionListView.class));
        }
        else
        {
            startActivity(new Intent(MainActivity.this, LoginPage.class));
        }







    }
}
