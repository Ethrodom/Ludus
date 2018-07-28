package com.apcsfinal.ludus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class ChooseSub extends AppCompatActivity {

    CheckBox chMath;
    CheckBox chSci;
    CheckBox chSocial;
    CheckBox chWorl;
    Button btCont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_sub);
        chMath = (CheckBox) findViewById(R.id.chMath);
        chSci = (CheckBox) findViewById(R.id.chSci);
        chSocial = (CheckBox) findViewById(R.id.chSocial);
        chWorl = (CheckBox) findViewById(R.id.chWL);
        btCont = (Button) findViewById(R.id.btContinue);
        pickSubs();
    }

    public void pickSubs()
    {


        btCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chMath.isChecked()) {
                    RegisterTutorActivity.btMath.setVisibility(View.VISIBLE);
                }
                else
                    RegisterTutorActivity.btMath.setVisibility(View.GONE);
                if(chSci.isChecked()) {
                    RegisterTutorActivity.btScience.setVisibility(View.VISIBLE);
                }
                else
                    RegisterTutorActivity.btScience.setVisibility(View.GONE);

                if(chSocial.isChecked()) {
                    RegisterTutorActivity.btSocial.setVisibility(View.VISIBLE);
                }
                else
                    RegisterTutorActivity.btSocial.setVisibility(View.GONE);

                if(chWorl.isChecked()) {
                    RegisterTutorActivity.btWorld.setVisibility(View.VISIBLE);
                }
                else
                    RegisterTutorActivity.btWorld.setVisibility(View.GONE);

                finish();

            }
        });

    }

}
