package com.apcsfinal.ludus;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class TutorDisplay extends BaseNavigationActivity {

    private RecyclerView tutorList;
    private DatabaseReference dataRef;
    private FirebaseRecyclerAdapter<Tutor, TutorDisplay.TutorViewHolder> TutorAdapator;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_tutor_display, frameLayout);
        setTitle("Tutors");

        //Establish Firebase connection
        //FirebaseDatabase myData = FirebaseDatabase.getInstance();
        dataRef = FirebaseDatabase.getInstance().getReference().child("Users");
        dataRef.keepSynced(true);

        tutorList = (RecyclerView) findViewById(R.id.tutorList);
        tutorList.setHasFixedSize(true);
        tutorList.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference personsRef = FirebaseDatabase.getInstance().getReference().child("Users");
        Query personsQuery = personsRef.orderByChild("type").startAt("Tutor").endAt("Tutor");
        FirebaseRecyclerOptions Options = new FirebaseRecyclerOptions.Builder<Tutor>().setQuery(personsQuery, Tutor.class).build();


        TutorAdapator = new FirebaseRecyclerAdapter<Tutor, TutorViewHolder>(Options) {
            @Override
            //sets the inputs in specified spots or AKA binds them to the card
            protected void onBindViewHolder(@NonNull TutorDisplay.TutorViewHolder holder, int position, @NonNull final Tutor model) {
                holder.setTutor(model.getName());
                holder.setRating(model.getRating());

                /*holder.btProfile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Convert the tutor into a parcel to send to the profile activity
                        Intent intent = new Intent(TutorDisplay.this, ProfileCard.class);
                        intent.putExtra("tutor", (Parcelable)model);
                        startActivity(intent);
                        finish();
                    }
                });*/
                /*holder.btMessage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(TutorDisplay.this, ChatActivity.class));
                    }
                });*/
            }

            @NonNull
            @Override
            //creates a new view or Card holder
            public TutorDisplay.TutorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                //creates a new view which is inherited from the parent view
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tutor_card, parent, false);

                return new TutorDisplay.TutorViewHolder(view);
            }
        };

        //Start the Database and view
        //firebaseRecyclerAdapter.startListening();
        //create the Recycler view
        tutorList.setAdapter(TutorAdapator);
    }

    //Card view creator setting all inputs
    public static class TutorViewHolder extends RecyclerView.ViewHolder
    {
        //create new view
        View mView;

        Button btProfile;
        Button btMessage;
        Button btRate;

        public TutorViewHolder(View itemView)
        {
            super(itemView);
            mView = itemView;

            btProfile = (Button) mView.findViewById(R.id.profileButton);
            btMessage = (Button) mView.findViewById(R.id.messageButton);
            btRate = (Button) mView.findViewById(R.id.rateButton);
        }

        public void setTutor(String name)
        {
            TextView tutorName = (TextView) mView.findViewById(R.id.tutorName);
            tutorName.setText(name);
        }

        public void setRating(float rating)
        {
            RatingBar star_rating = (RatingBar) mView.findViewById(R.id.tutorRatingBar);
            star_rating.setRating(rating);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        // to check current activity in the navigation drawer
        navigationView.getMenu().getItem(0).setChecked(true);
    }


    @Override
    protected void onStart()
    {
        super.onStart();

        TutorAdapator.startListening();
    }
}
