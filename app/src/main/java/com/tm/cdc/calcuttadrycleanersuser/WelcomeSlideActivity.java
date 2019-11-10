package com.tm.cdc.calcuttadrycleanersuser;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WelcomeSlideActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private LinearLayout mDotLayout;
    private  SlideAdapter slideAdapter;
    private TextView[] mDots;
    private Button prevButton;
    private Button nextButton;

    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_slide);

        mSlideViewPager=(ViewPager)findViewById (R.id.ViewPager);
        mDotLayout=(LinearLayout)findViewById (R.id.DotLayout);
        prevButton=(Button)findViewById(R.id.prevButton);
        nextButton=(Button)findViewById(R.id.nextButton);
        slideAdapter=new SlideAdapter(this);
        mSlideViewPager.setAdapter(slideAdapter);
        addDoteIndicator(0);
        mSlideViewPager.addOnPageChangeListener(viewListener);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mSlideViewPager.setCurrentItem(mCurrentPage+1);
            }
        });
        prevButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mSlideViewPager.setCurrentItem(mCurrentPage-1);
            }
        });
    }

    public void addDoteIndicator(int position)
    {
        mDots=new TextView[3];
        mDotLayout.removeAllViews();
        for(int i=0;i<mDots.length;i++)
        {
            mDots[i]=new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.textColorHint));
            mDotLayout.addView(mDots[i]);
        }
        if(mDots.length>0)
        {
            mDots[position].setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }
    ViewPager.OnPageChangeListener viewListener= new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
        {


        }

        @Override
        public void onPageSelected(int i) {
            addDoteIndicator(i);
            mCurrentPage=i;
            if(i==0)
            {
                nextButton.setEnabled(true);
                prevButton.setEnabled(false);
                prevButton.setVisibility(View.VISIBLE);

                nextButton.setText("Next");
                prevButton.setText("");

            }
            else if(i==mDots.length-1)
            {
                nextButton.setEnabled(true);
                prevButton.setEnabled(true);
                prevButton.setVisibility(View.VISIBLE);

                nextButton.setText("Finish");
                prevButton.setText("Back");

                nextButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        startActivity(new Intent(WelcomeSlideActivity.this, LoginActivity.class));
                    }
                });


            }
            else
            {
                nextButton.setEnabled(true);
                prevButton.setEnabled(true);
                prevButton.setVisibility(View.VISIBLE);

                nextButton.setText("Next");
                prevButton.setText("Previous");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state)
        {

        }
    };


}
