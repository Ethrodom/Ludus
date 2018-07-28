package com.apcsfinal.ludus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.support.v7.app.AlertDialog;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Session List View sets up the Sessions activity
 * Creates the List of sessions by grabbing from the
 * Database
 * Also helps Create a Session
 */
public class SessionListView extends BaseNavigationActivity {

    //Variables for session creation
    //int day, month, year, hour, minutes;
    //String dateFormat, timeFormat;
    //public EditText Location;
    //EditText numStu;
    //EditText duration;
    //DatePicker date;
    //TimePicker time;
    //Date dt;
    //Button setTime;
    Button btcreateSess;
    //Ui creation of the list view
    private RecyclerView sessList;
    public DatabaseReference dataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_session_list_view, frameLayout);
        setTitle("Sessions");

        //Create session
        btcreateSess = (Button) findViewById(R.id.btCreateSess);
        //RecyclerView creation
        sessList = (RecyclerView) findViewById(R.id.sessList);
        sessList.setHasFixedSize(true);
        sessList.setLayoutManager(new LinearLayoutManager(this));

        //Create database connection to the session objects
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseDatabase myData = FirebaseDatabase.getInstance();
        //database reference connects with firebase
        dataRef = myData.getReference().child("Sessions");
        final DatabaseReference dataUser = myData.getReference().child("Users").child(mAuth.getCurrentUser().getUid());


        // if Button is clicked start process
        btcreateSess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SessionListView.this, CreateSession.class));

                //Create a pop up alert on creating Session

                /*final AlertDialog.Builder bSession = new AlertDialog.Builder(SessionListView.this);
                final View view = LayoutInflater.from(SessionListView.this).inflate(R.layout.dialogbox_createsess, null);
                bSession.setTitle("Create Session");
                bSession.setView(view);
                final Dialog dialog , dialog2;
                bSession.setCancelable(false);
                bSession.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getBaseContext(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                    }
                });

                bSession.setPositiveButton("Done", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        //capture all input
                        Location = (EditText) view.findViewById(R.id.edLocation);
                        numStu = (EditText) view.findViewById(R.id.edNumStu);
                        duration = (EditText) view.findViewById(R.id.edTime);
                        date = (DatePicker) view.findViewById(R.id.date);
                        time = (TimePicker) view.findViewById(R.id.time);
                        setTime = (Button) view.findViewById(R.id.btTime);

                        setTime.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                final Calendar c = Calendar.getInstance();
                                int hour = c.get(Calendar.HOUR_OF_DAY);
                                int minute = c.get(Calendar.MINUTE);


                                TimePickerDialog timePickerDialog = new TimePickerDialog(bSession.getContext(),
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

                        day = date.getDayOfMonth();
                        month = date.getMonth();
                        year = date.getYear();

                        hour = time.getHour();
                        minutes = time.getMinute();

                        dateFormat = Integer.toString(day) + "/" + Integer.toString(month) + "/" + Integer.toString(year);
                        //timeFormat = Integer.toString(hour) + ":" + Integer.toString(minutes) + " PM";
                        //When user clicks done on the popup

                        dataUser.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String stdurt = duration.getText().toString();
                                String capacity = numStu.getText().toString();
                                String location = Location.getText().toString().trim();

                                int durt = Integer.parseInt(stdurt);
                                int cap = Integer.parseInt(capacity);
                                Tutor tut = dataSnapshot.getValue(Tutor.class);
                                //create session object with inputted values
                                Session session = new Session(tut.getName(), dateFormat, timeFormat, location, durt, cap);

                                //CREATE A CONNECTION AND REFERENCE
                                FirebaseDatabase.getInstance().getReference("Sessions").push().setValue(session).
                                        addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    Toast.makeText(SessionListView.this, "Session Created", Toast.LENGTH_LONG
                                                    ).show();
                                                }
                                            }
                                        });

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Toast.makeText(SessionListView.this, "Sorry the Connection to server failed", Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                });
                dialog = bSession.create();
                dialog.show();
                //View mView = getLayoutInflater().inflate(R.layout.dialogbox_createsess, null);*/
            }
        });



    }


    @Override
    protected void onResume() {
        super.onResume();
        // to check current activity in the navigation drawer
        navigationView.getMenu().getItem(0).setChecked(true);
    }



    //Card view creator setting all inputs
    public static class SessionViewHolder extends RecyclerView.ViewHolder
    {
        //create new view
        View mView;
        public SessionViewHolder(View itemView)
        {
            super(itemView);
            mView = itemView;
        }

        public void setTutor(String name)
        {
            TextView post_tutor = (TextView) mView.findViewById(R.id.txTutor);
            post_tutor.setText("Tutor: " + name);
        }

        public void setTime(String time, String date)
        {
            TextView set_desc = (TextView) mView.findViewById(R.id.txTime);
            set_desc.setText("Date/Time: " + time + " , " + date);
        }
        public void setLocation(String location)
        {
            TextView set_loca = (TextView) mView.findViewById(R.id.txLocation);
            set_loca.setText("Where: " + location);
        }


    }



    @Override
    protected void onStart()
    {
        super.onStart();

        //Creates a wrapped object that holds the dataReference and the session class
        FirebaseRecyclerOptions<Session> options = new FirebaseRecyclerOptions.Builder<Session>().setQuery(dataRef, Session.class).build();

        //Starts to get info from firebase
        FirebaseRecyclerAdapter<Session, SessionListView.SessionViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Session, SessionViewHolder>(options) {
                    @Override
                    //sets the inputs in specified spots or AKA binds them to the card
                    protected void onBindViewHolder(@NonNull SessionViewHolder holder, int position, @NonNull Session model) {
                        holder.setTutor(model.getTutor());
                        holder.setTime(model.getTime(), model.getDate());
                        holder.setLocation(model.getLocation());
                    }

                    @NonNull
                    @Override
                    //creates a new view or Card holder
                    public SessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        //creates a new view which is inherited from the parent view
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);

                        return new SessionViewHolder(view);
                    }
                };

        //Start the Database and view
        firebaseRecyclerAdapter.startListening();
        //create the Recycler view
        sessList.setAdapter(firebaseRecyclerAdapter);
    }
}
