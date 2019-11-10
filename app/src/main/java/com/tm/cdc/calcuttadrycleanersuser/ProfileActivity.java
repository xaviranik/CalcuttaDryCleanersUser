package com.tm.cdc.calcuttadrycleanersuser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

public class ProfileActivity extends AppCompatActivity {

    EditText userNameText;
    EditText address_1;
    EditText address_2;

    Button nextButton;
    ProgressBar progressBar;


    FirebaseUser currentUser;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userNameText = (EditText) findViewById(R.id.nameEditText);
        address_1 = (EditText) findViewById(R.id.addressText_1);
        address_2 = (EditText) findViewById(R.id.addressText_2);
        nextButton = (Button) findViewById(R.id.nextButton);
        progressBar = (ProgressBar) findViewById(R.id.mProgressbar2);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                saveUserInfo();
            }
        });
    }

    private void saveUserInfo()
    {
        nextButton.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        String userName, address, phoneNumber;
        userName = userNameText.getText().toString();
        address = address_1.getText().toString() + "---" + address_2.getText().toString();
        phoneNumber = currentUser.getPhoneNumber();

        UserInfo user = new UserInfo(userName,address,phoneNumber);

        db.document("UserInfo/" + currentUser.getPhoneNumber()).set(user, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid)
            {
                Toast.makeText(getApplicationContext(), "Profile Updated!", Toast.LENGTH_SHORT).show();
                setLoginFlag();
                startActivity(new Intent(ProfileActivity.this, HomeActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Toast.makeText(getApplicationContext(), "Profile update failed!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setLoginFlag()
    {
        SharedPreferences.Editor editor = getSharedPreferences(getPackageName(), MODE_PRIVATE).edit();
        editor.putString("LoginFlag", "true");
        editor.apply();
    }
}
