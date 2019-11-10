package com.tm.cdc.calcuttadrycleanersuser;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserProfileActivity extends AppCompatActivity {

    Button updateButton;
    EditText nameText;
    EditText address_1;
    EditText address_2;
    EditText phoneText;
    ProgressBar mProgressbar;
    ImageView updateIcon;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore mFireStore = FirebaseFirestore.getInstance();
    DocumentReference userProfileDoc = mFireStore.document("UserInfo/" + mAuth.getCurrentUser().getPhoneNumber());

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //setBottomNavBar();

        updateButton = (Button) findViewById(R.id.updateButton);
        nameText = (EditText) findViewById(R.id.nameEditText);
        address_1 = (EditText) findViewById(R.id.adddress_1_EditText);
        address_2 = (EditText) findViewById(R.id.adddress_2_EditText);
        phoneText = (EditText) findViewById(R.id.PhoneEditTextNE);
        mProgressbar = (ProgressBar) findViewById(R.id.progressBarP);
        updateIcon = (ImageView) findViewById(R.id.editProfileImageView);

        phoneText.setText(mAuth.getCurrentUser().getPhoneNumber());
        updateIcon.setElevation(10);


        updateButton.setVisibility(View.INVISIBLE);
        nameText.setEnabled(false);
        address_1.setEnabled(false);
        address_2.setEnabled(false);

        setProfileInfo();

        updateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                nameText.setEnabled(true);
                address_1.setEnabled(true);
                address_2.setEnabled(true);
                updateButton.setVisibility(View.VISIBLE);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                updateProfileInfo();
            }
        });

    }

    private void updateProfileInfo()
    {
        mProgressbar.setVisibility(View.VISIBLE);
        nameText.setEnabled(false);
        address_1.setEnabled(false);
        address_2.setEnabled(false);

        String name = nameText.getText().toString();
        String addrs_1 = address_1.getText().toString();
        String addrs_2 = address_2.getText().toString();

        String address = addrs_1 + "---" + addrs_2;

        UserInfo userInfo = new UserInfo(name, address, mAuth.getCurrentUser().getPhoneNumber());

        userProfileDoc.set(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid)
            {
                mProgressbar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Profile updated!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Toast.makeText(getApplicationContext(), "Profile update failed!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setProfileInfo()
    {
        mProgressbar.setVisibility(View.VISIBLE);

        userProfileDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                if(documentSnapshot.exists())
                {
                    mProgressbar.setVisibility(View.INVISIBLE);

                    UserInfo user = documentSnapshot.toObject(UserInfo.class);

                    String name = user.getUserName();
                    String address = user.getAddress();

                    String[] addressString = address.split("---");

                    nameText.setText(name);
                    address_1.setText(addressString[0]);
                    address_2.setText(addressString[1]);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                Toast.makeText(getApplicationContext(), "Profile Load failed!", Toast.LENGTH_SHORT).show();
            }
        });





    }

    private void setBottomNavBar()
    {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.ic_home:
                    {
                        startActivity(new Intent(UserProfileActivity.this, HomeActivity.class));
                        finish();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;
                    }
                    case R.id.ic_profile:
                    {
                        break;
                    }
                    case R.id.ic_service:
                    {
                        startActivity(new Intent(UserProfileActivity.this, ServiceActivity.class));
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        finish();
                        break;
                    }
                }


                return false;
            }
        });

    }

























}
