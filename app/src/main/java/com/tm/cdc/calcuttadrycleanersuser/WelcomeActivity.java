package com.tm.cdc.calcuttadrycleanersuser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class WelcomeActivity extends AppCompatActivity {

    Button getStartButton;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        getStartButton = (Button) findViewById(R.id.getStartedButton);

        mAuth = FirebaseAuth.getInstance();

        getStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(WelcomeActivity.this, WelcomeSlideActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if(mAuth.getCurrentUser() != null)
        {
            startActivity(new Intent(WelcomeActivity.this, LoginInfoCheckerActivity.class));
            finish();
        }
    }
}
