package com.tm.cdc.calcuttadrycleanersuser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    TextView signUpText;
    EditText phoneNumText;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signUpText = (TextView) findViewById(R.id.signUpTextview);
        phoneNumText = (EditText) findViewById(R.id.PhoneeditText);
        loginButton = (Button) findViewById(R.id.sendButton);
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));

            }
        });

        loginAuth();


    }

    private void loginAuth()
    {

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String phoneNum = phoneNumText.getText().toString();
                if(phoneNum.isEmpty())
                {
                    phoneNumText.setError("Enter the number!");
                    phoneNumText.requestFocus();
                    return;
                }
                Intent intent = new Intent(getBaseContext(), LoginAuthActivity.class);
                intent.putExtra("PHONE_NUM", phoneNum);
                startActivity(intent);
            }
        });

    }





















}
