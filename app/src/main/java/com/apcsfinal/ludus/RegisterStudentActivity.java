package com.apcsfinal.ludus;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;

public class RegisterStudentActivity extends AppCompatActivity {

    private EditText editName, editUname, editPass, editPhone, editId, editEmail;
    private Button btSignUp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);

        editId = findViewById(R.id.editStid);
        editName = findViewById(R.id.editName);
        editUname = findViewById(R.id.editUsername);
        editPass = findViewById(R.id.editPassword);
        editPhone = findViewById(R.id.editPhone);
        editEmail = findViewById(R.id.editEmail);


        mAuth = FirebaseAuth.getInstance();

        btSignUp = (Button) findViewById(R.id.btSignUp);
        btSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpStudent();
            }
        });

    }

    public void signUpStudent()
    {
        final String name = editName.getText().toString().trim();
        final String email = editEmail.getText().toString().trim();
        final String password = editPass.getText().toString().trim();
        final String phone = editPhone.getText().toString().trim();
        final String ID = editId.getText().toString().trim();
        final String UserName = editUname.getText().toString().trim();

        if (name.isEmpty()) {
            editName.setError("Oops we need your name");
            editName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editEmail.setError("Oops we need your email");
            editEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Your email is already in-use");
            editEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editPass.setError("Oops your forgot your Password");
            editPass.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editPass.setError("Your Password needs to be bigger then 7 characters");
            editPass.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            editPhone.setError("We need a Phone Number");
            editPhone.requestFocus();
            return;
        }

        if (phone.length() != 10) {
            editPhone.setError("Make sure you have a valid Phone Number");
            editPhone.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Student student = new Student(ID, name, email, phone, password);
                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance()
                                    .getCurrentUser().getUid()).setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(RegisterStudentActivity.this, SessionListView.class));
                                        Toast.makeText(RegisterStudentActivity.this, "Account Created", Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Toast.makeText(RegisterStudentActivity.this, "Couldn't Create Account, Try Later", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(RegisterStudentActivity.this, "Couldn't Create Account, Try Later", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}
