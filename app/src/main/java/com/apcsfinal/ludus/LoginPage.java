package com.apcsfinal.ludus;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginPage extends AppCompatActivity {

    public Button btLogin, btSignup;
    public EditText edemail, edpassword;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        mAuth = FirebaseAuth.getInstance();

        edemail = (EditText) findViewById(R.id.edEmail) ;
        edpassword = (EditText) findViewById(R.id.edPass);

        btLogin = (Button) findViewById(R.id.btLogin);
        btSignup = (Button) findViewById(R.id.btSignUp);

        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, PickUserActivity.class));
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edemail.getText().toString().trim();
                String password = edpassword.getText().toString().trim();

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            startActivity(new Intent(LoginPage.this, SessionListView.class));
                        }
                        else
                            Toast.makeText(LoginPage.this, "Apologies. It seems that we can't log you in.", Toast.LENGTH_LONG).show();

                    }
                });

            }
        });

    }
}
