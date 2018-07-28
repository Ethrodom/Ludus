package com.apcsfinal.ludus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.TextView;
import com.google.firebase.auth.*;
import com.google.firebase.database.*;

import java.io.Serializable;

public class ProfileCard extends AppCompatActivity implements Serializable{

    private CardView profile;
    private TextView name;
    private TextView email;
    private TextView phone;
    private TextView hours;
    private TextView rating;
    private TextView type;
    private TextView subjects;

    String uid;
    DatabaseReference database;
    DatabaseReference userRef;
    User user;

    Tutor tutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_card);

        //profile CardView setup
        profile = (CardView) findViewById(R.id.cardView);
        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        phone = (TextView) findViewById(R.id.phone);
        hours = (TextView) findViewById(R.id.hours);
        rating = (TextView) findViewById(R.id.rating);
        type = (TextView) findViewById(R.id.type);
        subjects = (TextView) findViewById(R.id.subjects);


        tutor = (Tutor)getIntent().getSerializableExtra("tutorClicked");


        //Firebase Setup
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase myData = FirebaseDatabase.getInstance();
        final DatabaseReference dataUser = myData.getReference().child("Users").child(mAuth.getCurrentUser().getUid()); //database reference connects with firebase

        // Read from the database
        dataUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                //String readType = dataSnapshot.child("Type").getValue(String.class);
                //String readType = dataSnapshot.child("Users").child(uid).child("Type").getValue(String.class);
                //User user = dataSnapshot.getValue(User.class);

                if(dataSnapshot.child("type").getValue(String.class).equals("Tutor"))//if(readType.equals("Tutor"))
                    user = dataSnapshot.getValue(Tutor.class);
                else
                    user = dataSnapshot.getValue(Student.class);

                name.setText(user.getName());
                email.setText(user.getEmail());
                phone.setText(user.getPhone());
                type.setText(user.getType());

                if(user.getType().equals("Tutor")) //old check: type.getText().equals("Tutor")
                {
                    //user = (Tutor)user;
                    hours.setText(String.format(java.util.Locale.US, "%.2f", ((Tutor) user).getHours()));
                    rating.setText(String.format(java.util.Locale.US, "%.2f", ((Tutor) user).getRating()));
                    subjects.setText(((Tutor) user).getClasses());
                }

                //Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
