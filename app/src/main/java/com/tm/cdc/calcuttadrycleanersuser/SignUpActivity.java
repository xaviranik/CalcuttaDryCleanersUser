package com.tm.cdc.calcuttadrycleanersuser;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.Group;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {

    EditText phoneNumText;
    EditText codeText;
    ProgressBar mProgressBar;
    Button mSendButton;

    Group codeGroup;
    Group errorGroup;

    FirebaseAuth mAuth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    String mVerificationId;
    PhoneAuthProvider.ForceResendingToken mResendToken;

    int buttonType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        phoneNumText = (EditText) findViewById(R.id.PhoneeditText);
        codeText = (EditText) findViewById(R.id.codeEditText);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBarSignUp);
        codeGroup = (Group) findViewById(R.id.CodeGroup);
        mSendButton = (Button)findViewById(R.id.sendButton);
        errorGroup = (Group) findViewById(R.id.ErrorGroup);



        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                errorGroup.setVisibility(View.INVISIBLE);

                if(buttonType == 0)
                {
                    mProgressBar.setVisibility(View.VISIBLE);
                    phoneNumText.setEnabled(false);
                    mSendButton.setEnabled(false);


                    String phoneNum = "+880" + phoneNumText.getText().toString();

                    if(phoneNumText.getText().toString().isEmpty())
                    {
                        phoneNumText.setError("Enter Phone number here!");
                        phoneNumText.requestFocus();
                        mProgressBar.setVisibility(View.INVISIBLE);
                        phoneNumText.setEnabled(true);
                        mSendButton.setEnabled(true);
                        return;
                    }

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNum,60, TimeUnit.SECONDS, SignUpActivity.this, mCallback);
                }
                else
                {
                    mProgressBar.setVisibility(View.VISIBLE);

                    String code = codeText.getText().toString();

                    if(code.isEmpty())
                    {
                        codeText.setError("Enter code here!");
                        codeText.requestFocus();
                        mProgressBar.setVisibility(View.INVISIBLE);
                        return;
                    }

                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
                    signInWithPhoneAuthCredential(credential);
                }


            }
        });

        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential)
            {
                mProgressBar.setVisibility(View.INVISIBLE);
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e)
            {
                retryVerification();
                Toast.makeText(getApplicationContext(), e.getMessage() , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token)
            {

                mVerificationId = verificationId;
                mResendToken = token;

                codeGroup.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.INVISIBLE);
                mSendButton.setText("Verify Code");

                mSendButton.setEnabled(true);

                buttonType = 1;
            }
        };

    }

    private void retryVerification()
    {
        errorGroup.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        phoneNumText.setEnabled(true);
        mSendButton.setEnabled(true);
        mSendButton.setText("Resend Code");
        buttonType = 0;
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {
        try
        {
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                errorGroup.setVisibility(View.INVISIBLE);
                                mProgressBar.setVisibility(View.INVISIBLE);
                                startActivity(new Intent(SignUpActivity.this, ProfileActivity.class));
                                finish();
                            }
                            else
                            {
                                errorGroup.setVisibility(View.VISIBLE);
                                mProgressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }















}






















