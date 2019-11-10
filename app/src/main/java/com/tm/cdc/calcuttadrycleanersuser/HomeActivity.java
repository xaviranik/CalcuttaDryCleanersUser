package com.tm.cdc.calcuttadrycleanersuser;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    Button orderButton;
    Button trackButton;
    Button historyButton;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    //ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navView);
        orderButton = (Button) findViewById(R.id.orderButton);
        trackButton = (Button) findViewById(R.id.TrackButton);
        historyButton = (Button) findViewById(R.id.historyButton);


        setBottomNavigation();


        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomeActivity.this, OrderActivity.class));
            }
        });

        trackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomeActivity.this, TrackActivity.class));
            }
        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomeActivity.this, HistoryActivity.class));
            }
        });


        //setViewPager();
        //viewFlipper = (ViewFlipper) findViewById(R.id.viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.logout:
            {
                mAuth.signOut();

                Toast.makeText(getApplicationContext(), "Logging Out", Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run()
                    {
                        Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                }, 2000);

                break;
            }

            case R.id.more_service:
            {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("More Services")
                        .setMessage("OUR MARKET SEGMENTS SERVED INCLUDE:\n\n" +
                                "\u2022 Laundry cleaning services\n" +
                                "\u2022 House laundry services\n" +
                                "\u2022 Professional laundry service\n" +
                                "\u2022 Commercial laundry service\n" +
                                "\u2022 Industrial laundry service\n" +
                                "\u2022 Medical laundry service\n" +
                                "\u2022 Healthcare laundry service\n" +
                                "\u2022 Restaurant laundry service\n" +
                                "\u2022 Hotel laundry service\n" +
                                "\u2022 Spa & salon laundry service" + "\n\n" +
                                "CALCUTTA Dry Cleaners is a full service commercial laundry service business. " +
                                "We save you time and money by providing and managing the inventory, and by washing, " +
                                "ironing, folding, and delivering clean linen to your establishment weekly.")
                        .show();

                break;
            }

            case R.id.about:
            {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(HomeActivity.this);
                builder.setTitle("About")
                        .setMessage("© Calcutta Dry Cleaners, All Rights reserved.")
                        .show();

                break;
            }
        }


        return super.onOptionsItemSelected(item);
    }

    /*
    private void setViewPager()
    {

        int img[] = {R.drawable.flip_image1, R.drawable.flip_image2, R.drawable.flip_image3};

        for(int i: img)
        {
            flipImage(i);
        }

    }

    private void flipImage(int img)
    {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(img);

        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);

        viewFlipper.setInAnimation(this, android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }
    */

    private void setBottomNavigation()
    {
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.ic_home:
                    {
                        break;
                    }
                    case R.id.ic_profile:
                    {
                        startActivity(new Intent(HomeActivity.this, UserProfileActivity.class));
                        //finish();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;
                    }
                    case R.id.ic_service:
                    {
                        startActivity(new Intent(HomeActivity.this, ServiceActivity.class));
                        //finish();
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;
                    }
                }


                return false;
            }
        });

    }



























}
