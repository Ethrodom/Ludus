package com.apcsfinal.ludus;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.support.v7.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import android.app.*;
import android.widget.EditText;
import android.content.DialogInterface;
import android.text.InputType;
import android.content.Context;
import android.widget.TimePicker;
import android.widget.Toast;

public class Settings extends BaseNavigationActivity {

    //Buttons
    private Button chEmail;
    private Button chPassword;
    private Button delAccount;
    private Button signOut;

    //CardView + its info
    private CardView profile;
    private TextView name;
    private TextView email;
    private TextView phone;
    private TextView hours;
    private TextView rating;
    private TextView type;
    private TextView subjects;

    //Firebase stuff
    //FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    //new input entries
    private String newEmail;
    private String newPassword;
    private String confirm;

    //EditText's
    private EditText editEmail;
    private EditText editPassword;
    private EditText removeAccount;

    private static final String TAG = "Settings.java";

    //more Firebase stuff
    String uid;
    DatabaseReference database;
    DatabaseReference userRef;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_settings, frameLayout); //sets what is displayed
        setTitle("Settings");
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        //final FirebaseUser user  = mAuth.getCurrentUser();
        //uid = mAuth.getCurrentUser().getUid();
        //database = FirebaseDatabase.getInstance().getReference();
        //userRef = database.child("Users").child(uid);

        FirebaseDatabase myData = FirebaseDatabase.getInstance();
        //database reference connects with firebase
        database = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference dataUser = myData.getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        final FirebaseUser userAuth = FirebaseAuth.getInstance().getCurrentUser();//reads data from the app user

        //.child("Users").child(uid);

        //profile CardView at top
            profile = (CardView) findViewById(R.id.cardView);
            name = (TextView) findViewById(R.id.name);
            email = (TextView) findViewById(R.id.email);
            phone = (TextView) findViewById(R.id.phone);
            hours = (TextView) findViewById(R.id.hours);
            rating = (TextView) findViewById(R.id.rating);
            type = (TextView) findViewById(R.id.type);
            subjects = (TextView) findViewById(R.id.subjects);

        // Read from the database
            dataUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    //String readType = dataSnapshot.child("Type").getValue(String.class);
                    //String readType = dataSnapshot.child("Users").child(uid).child("Type").getValue(String.class);
                    //User user = dataSnapshot.getValue(User.class);

                    if(dataSnapshot.child("type").getValue(String.class).equals("Tutor"))//if(readType.equals("Tutor"))  if user is a Tutor
                        user = dataSnapshot.getValue(Tutor.class);
                    else
                        user = dataSnapshot.getValue(Student.class);    //if user is a Student

                    //User Fields
                    name.setText(user.getName());
                    email.setText(user.getEmail());
                    phone.setText(user.getPhone());
                    type.setText(user.getType());

                    //Tutor Fields
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


        //Buttons for changing data in Firebase
            chEmail = (Button) findViewById(R.id.chEmail);
            chPassword = (Button) findViewById(R.id.chPass);
            delAccount = (Button) findViewById(R.id.deleteAcc);
            signOut = (Button) findViewById(R.id.signOut);

            setupFirebaseListener();

            //when "Change Password" is pressed
            chEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                    builder.setTitle("Change Email");

                    // Set up the input
                    final EditText input = new EditText(Settings.this);
                    // Specify the type of input expected
                    input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    builder.setView(input);

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            newEmail = input.getText().toString();
                            userAuth.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        database.child("Users").child(mAuth.getCurrentUser().getUid()).child("email").setValue(newEmail);//stores value in database
                                    }
                                }
                            });

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    Dialog dialog = builder.create();
                    dialog.show();
                                    /*
                                    final android.support.v7.app.AlertDialog.Builder changeEmail = new android.support.v7.app.AlertDialog.Builder(Settings.this);
                                    final View view = LayoutInflater.from(Settings.this).inflate(R.layout.change_email_alert, null);
                                    changeEmail.setTitle("Change Email");
                                    changeEmail.setView(view);
                                    final Dialog dialog;
                                    changeEmail.setCancelable(false);

                                    changeEmail.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    changeEmail.setPositiveButton("Done", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //capture all input
                                            editEmail = (EditText) view.findViewById(R.id.edEmail);
                                            newEmail = editEmail.getText().toString();
                                        }
                                    });

                                    dialog = changeEmail.create();
                                    dialog.show();
                                    //View mView = getLayoutInflater().inflate(R.layout.dialogbox_createsess, null);
                                    database.child("Users").child(uid).child("email").setValue(newEmail);
                                    */
                }
            });

            //when "Change Password" is pressed
            chPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                    builder.setTitle("Enter new password");

                    // Set up the input
                    final EditText input = new EditText(Settings.this);
                    // Specify the type of input expected
                    input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    builder.setView(input);

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            newPassword = input.getText().toString();
                            userAuth.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        database.child("Users").child(mAuth.getCurrentUser().getUid()).child("password").setValue(newPassword);
                                    }
                                }
                            });

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    Dialog dialog = builder.create();
                    dialog.show();

                                    /*
                                    AlertDialog.Builder changeEmail = new AlertDialog.Builder(Settings.this);
                                    final View view = LayoutInflater.from(Settings.this).inflate(R.layout.change_email_alert, null);
                                    changeEmail.setTitle("Change Email");
                                    changeEmail.setView(view);
                                    final Dialog dialog;
                                    changeEmail.setCancelable(false);

                                    changeEmail.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    changeEmail.setPositiveButton("Done", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //capture all input
                                            editEmail = (EditText) view.findViewById(R.id.edEmail);
                                            newEmail = editEmail.getText().toString();
                                        }
                                    });

                                    dialog = changeEmail.create();
                                    dialog.show();
                                    //View mView = getLayoutInflater().inflate(R.layout.dialogbox_createsess, null);
                                    database.child("Users").child(uid).child("password").setValue(newPassword);
                                    */
                }
            });

            //when "Delete Account" is pressed
            delAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                    builder.setTitle("Enter \"Delete\" to delete account");

                    // Set up the input
                    final EditText input = new EditText(Settings.this);
                    // Specify the type of input expected
                    input.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    builder.setView(input);

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            confirm = input.getText().toString();
                            if(confirm.equals("Delete")) {
                                //database.child("Users").child(mAuth.getCurrentUser().getUid()).removeValue();
                                userAuth.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            startActivity(new Intent(Settings.this, LoginPage.class));
                                            finish();
                                        }
                                    }
                                });

                            }

                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    Dialog dialog = builder.create();
                    dialog.show();
                }
            });

            //when "Sign Out" is pressed
            signOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: attempting to sign out the user.");
                    FirebaseAuth.getInstance().signOut();
                }
            });

    }

    /**
     * prepares FirebaseListener to sign out
     */
    private void setupFirebaseListener() {
        Log.d(TAG, "setupFirebaseListener: setting up the auth state listener.");
        mAuthStateListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null)
                {
                    Log.d(TAG, "onAuthStateChanged: signed_in: " + user.getUid());
                }
                else
                {
                    Log.d(TAG, "onAuthStateChanged: signed_out: ");
                    Toast.makeText(Settings.this, "Signed out", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Settings.this, LoginPage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);   //clears past activities/views
                    startActivity(intent);  //go to login activity
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mAuthStateListener != null)
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // to check current activity in the navigation drawer
        navigationView.getMenu().getItem(0).setChecked(true);
    }

}
