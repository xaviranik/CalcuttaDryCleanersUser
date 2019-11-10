package com.tm.cdc.calcuttadrycleanersuser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginInfoCheckerActivity extends AppCompatActivity {

    ImageView settingsImage;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore mFireStore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_info_checker);

        checkForLoginFlag();

        settingsImage = (ImageView) findViewById(R.id.settingsImage);

        RotateAnimation rotate = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);

        rotate.setDuration(4000);
        rotate.setRepeatCount(Animation.INFINITE);
        settingsImage.setAnimation(rotate);

    }

    private void checkForLoginFlag()
    {
        SharedPreferences prefs = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        String restoredText = prefs.getString("LoginFlag", null);
        if (restoredText != null)
        {
            String userLogin = prefs.getString("LoginFlag", "false");

            if(userLogin.equals("true"))
            {
                startActivity(new Intent(LoginInfoCheckerActivity.this, HomeActivity.class));
                finish();
            }
        }
        else
        {
            checkForUserInfo();
        }

    }

    private void checkForUserInfo()
    {
        DocumentReference db = mFireStore.document("UserInfo/" + mAuth.getCurrentUser().getPhoneNumber());

        db.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task)
            {
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot.exists())
                {
                    Toast.makeText(getApplicationContext(), "Profile Loaded", Toast.LENGTH_SHORT).show();
                    setLoginFlag();

                    startActivity(new Intent(LoginInfoCheckerActivity.this, HomeActivity.class));
                    finish();
                }
                else
                {
                    startActivity(new Intent(LoginInfoCheckerActivity.this, ProfileActivity.class));
                    finish();
                }

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
