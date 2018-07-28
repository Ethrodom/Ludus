package com.apcsfinal.ludus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PickUserActivity extends AppCompatActivity {
    //Button btTutor, btStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_user);


        Button btTutor = (Button) findViewById(R.id.btTutor);
        Button btStudent = (Button) findViewById(R.id.btStudent);

        btTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PickUserActivity.this, RegisterTutorActivity.class));
            }
        });

        btStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PickUserActivity.this, RegisterStudentActivity.class));
            }
        });
    }
}
