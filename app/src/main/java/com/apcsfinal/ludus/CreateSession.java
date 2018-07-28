package com.apcsfinal.ludus;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.app.TimePickerDialog;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class CreateSession extends AppCompatActivity {

    int day, month, myear, hour, minutes;
    String dateFormat, timeFormat;

    TimePickerDialog timeDialog;
    DatePickerDialog dateDialog;
    EditText location, numStu, duration;
    Button setTime, setDate, done, cancel;

    Calendar newDate;

    DatabaseReference dataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session);

        //Create database connection to the session objects
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase myData = FirebaseDatabase.getInstance();
        //database reference connects with firebase
        dataRef = myData.getReference().child("Sessions");
        final DatabaseReference dataUser = myData.getReference().child("Users").child(mAuth.getCurrentUser().getUid());

        setTime = (Button) findViewById(R.id.btTime);
        setDate = (Button) findViewById(R.id.btDate);
        done = (Button) findViewById(R.id.btDone);
        cancel = (Button) findViewById(R.id.btCancel);
        duration = (EditText) findViewById(R.id.edDuration);
        numStu = (EditText) findViewById(R.id.edNumStu);
        location = (EditText) findViewById(R.id.edLocation);





        setDate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               newDate = Calendar.getInstance();
               day = newDate.get(Calendar.DAY_OF_MONTH);
               month = newDate.get(Calendar.MONTH);
               myear = newDate.get(Calendar.YEAR);

               dateDialog = new DatePickerDialog(CreateSession.this, new DatePickerDialog.OnDateSetListener() {
                   public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                       dateFormat = Integer.toString(dayOfMonth) + "/" + Integer.toString(monthOfYear) + "/" + Integer.toString(year);

                   }

               }, day, month, myear);
               dateDialog.show();


           }
       });



        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);


                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateSession.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                timeFormat = hourOfDay + ":" + minute;
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataUser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String stdurt = duration.getText().toString();
                        String capacity = numStu.getText().toString();
                        String Location = location.getText().toString().trim();

                        int durt = Integer.parseInt(stdurt);
                        int cap = Integer.parseInt(capacity);
                        Tutor tut = dataSnapshot.getValue(Tutor.class);
                        //create session object with inputted values
                        Session session = new Session(tut.getName(), dateFormat, timeFormat, Location, durt, cap);

                        //CREATE A CONNECTION AND REFERENCE
                        FirebaseDatabase.getInstance().getReference("Sessions").push().setValue(session).
                                addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(CreateSession.this, "Session Created", Toast.LENGTH_LONG
                                            ).show();
                                            finish();
                                        }
                                    }
                                });

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(CreateSession.this, "Sorry the Connection to server failed", Toast.LENGTH_LONG).show();
                    }

                });
            }

        });


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();;
            }
        });




    }
}
