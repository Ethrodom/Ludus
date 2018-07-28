package com.apcsfinal.ludus;

import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class RegisterTutorActivity extends AppCompatActivity {

    public static Button btMath, btSocial, btScience, btWorld, btFin, btNext;

    private String name, email, ID, password, UserName, phone;

    private EditText editName, editUname, editPass, editPhone, editId, editEmail;

    String[] mathClass, sciClass, scoClass, worlClass;
    boolean[] mathCheck, sciCheck, scoCheck, worlCheck;
    ArrayList<Integer> mathItems = new ArrayList<>();
    ArrayList<Integer> sciItems = new ArrayList<>();
    ArrayList<Integer> scoItems = new ArrayList<>();
    ArrayList<Integer> worlItems = new ArrayList<>();
    String[] mathSelected, sciSelected, scoSelected, worlSelected;
    HashMap<String, List<String>> subjects = new HashMap<String, List<String>>();
    List<String> mt = new ArrayList<String>();
    List<String> sco = new ArrayList<String>();
    List<String> sci = new ArrayList<String>();
    List<String> wrl = new ArrayList<String>();



    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_tutor);
        mAuth = FirebaseAuth.getInstance();
        editId = findViewById(R.id.editStid);
        editName = findViewById(R.id.editName);
        editUname = findViewById(R.id.editUsername);
        editPass = findViewById(R.id.editPassword);
        editPhone = findViewById(R.id.editPhone);
        editEmail = findViewById(R.id.editEmail);
        verifyInput();

        btNext = (Button) findViewById(R.id.btNext);
        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterTutorActivity.this, ChooseSub.class));
                signUpTutor();
            }
        });


    }


    public void verifyInput()
    {
        name = editName.getText().toString().trim();
        email = editEmail.getText().toString().trim();
        password = editPass.getText().toString().trim();
        phone = editPhone.getText().toString().trim();
        ID = editId.getText().toString().trim();
        UserName = editUname.getText().toString().trim();

        if (name.isEmpty()) {
            editName.setError("Oops! We need to know your name first.");
            editName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editEmail.setError("Oops! We need your email address first.");
            editEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("It seems that your email is already in use. Try a different one.");
            editEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editPass.setError("Oops! We need you to enter a password first.");
            editPass.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editPass.setError("Your password must be at least 6 characters long!");
            editPass.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            editPhone.setError("Oops! We need your phone number first.");
            editPhone.requestFocus();
            return;
        }

        if (phone.length() != 10) {
            editPhone.setError("Make sure that you entered a valid phone number!");
            editPhone.requestFocus();
            return;
        }
    }

    public void signUpTutor()
    {
        verifyInput();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            pickClasses();

                        } else {
                            Toast.makeText(RegisterTutorActivity.this, "Couldn't Create Account, Try Later",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public void pickClasses()
    {
        setContentView(R.layout.activity_choose_tutor);
        btScience = (Button) findViewById(R.id.btScience);
        btMath = (Button) findViewById(R.id.btMath);
        btSocial = (Button) findViewById(R.id.btSocial);
        btWorld = (Button) findViewById(R.id.btWorld);
        btFin = (Button) findViewById(R.id.btFin);
        btScience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getScience();
                subjects.put("Science", sci);

            }
        });
        btMath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMath();
                subjects.put("Math", mt);
            }
        });

        btWorld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWorld();
                subjects.put("WorldLanguage", wrl);

            }
        });
        btSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSocial();
                subjects.put("SocialStudies", sco);

            }
        });

        btFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Tutor tutor = new Tutor(ID, name, email, phone, password, subjects, mAuth.getCurrentUser().getUid());

                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance()
                        .getCurrentUser().getUid()).setValue(tutor).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(RegisterTutorActivity.this, SessionListView.class));
                            Toast.makeText(RegisterTutorActivity.this, "Account Created", Toast.LENGTH_LONG
                            ).show();
                        } else {
                            Toast.makeText(RegisterTutorActivity.this, "Couldn't Create Account, Try Later",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });
    }


    public void getScience()
    {


        sciClass = getResources().getStringArray(R.array.Science);
        sciCheck = new boolean[sciClass.length];

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("Different Science Classes");
        mBuilder.setMultiChoiceItems(sciClass, sciCheck, new OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked)
                {
                    if(!sciItems.contains(which))
                    {
                        sciItems.add(which);
                    }
                    else
                    if(sciItems.contains(which))
                    {
                        sciItems.remove(which);
                    }
                }
            }
        });
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sciSelected = new String[sciItems.size()];
                for(int i = 0; i < sciSelected.length; i++)
                {
                    sci.add(sciClass[sciItems.get(i)]);
                }

            }
        });
        mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        mBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for(int i = 0; i < sciCheck.length; i++)
                {
                    sciCheck[i] = false;
                    sciItems.clear();
                    sci.clear();
                }
            }
        });
        AlertDialog alert = mBuilder.create();
        alert.show();

    }


    public void getWorld()
    {


        worlClass = getResources().getStringArray(R.array.WorldLanguage);
        worlCheck = new boolean[worlClass.length];

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("Different World Language Classes");
        mBuilder.setMultiChoiceItems(worlClass, worlCheck, new OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked)
                {
                    if(!worlItems.contains(which))
                    {
                        worlItems.add(which);
                    }
                    else
                    if(worlItems.contains(which))
                    {
                        worlItems.remove(which);
                    }
                }
            }
        });
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                worlSelected = new String[worlItems.size()];
                for(int i = 0; i < worlSelected.length; i++)
                {
                    wrl.add(worlClass[worlItems.get(i)]);
                }

            }
        });
        mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        mBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for(int i = 0; i < worlCheck.length; i++)
                {
                    worlCheck[i] = false;
                    worlItems.clear();
                    wrl.clear();
                }
            }
        });

        AlertDialog alert = mBuilder.create();
        alert.show();

    }


    public void getSocial()
    {


        scoClass = getResources().getStringArray(R.array.SocialStudies);
        scoCheck = new boolean[scoClass.length];

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("Different Social Studies Classes");
        mBuilder.setMultiChoiceItems(scoClass, sciCheck, new OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked)
                {
                    if(!scoItems.contains(which))
                    {
                        scoItems.add(which);
                    }
                    else
                    if(sciItems.contains(which))
                    {
                        scoItems.remove(which);
                    }
                }
            }
        });
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scoSelected = new String[scoItems.size()];
                for(int i = 0; i < scoSelected.length; i++)
                {
                    sco.add(scoClass[scoItems.get(i)]);
                }

            }
        });
        mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        mBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for(int i = 0; i < scoCheck.length; i++)
                {
                    scoCheck[i] = false;
                    scoItems.clear();
                    sco.clear();
                }
            }
        });
        AlertDialog alert = mBuilder.create();
        alert.show();

    }

    public void getMath()
    {

        mathClass = getResources().getStringArray(R.array.Math);
        mathCheck = new boolean[mathClass.length];

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        mBuilder.setTitle("Different Math Classes");
        mBuilder.setMultiChoiceItems(mathClass, mathCheck, new OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if(isChecked)
                {
                    if(!mathItems.contains(which))
                    {
                        mathItems.add(which);
                    }
                    else
                    if(mathItems.contains(which))
                    {
                        mathItems.remove(which);
                    }
                }
            }
        });
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mathSelected = new String[mathItems.size()];
                for(int i = 0; i < mathSelected.length; i++)
                {
                    mt.add(mathClass[mathItems.get(i)]);
                }

            }
        });
        mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        mBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for(int i = 0; i < mathCheck.length; i++)
                {
                    mathCheck[i] = false;
                    mathItems.clear();
                    mt.clear();
                }
            }
        });
        AlertDialog alert = mBuilder.create();
        alert.show();
    }
}
