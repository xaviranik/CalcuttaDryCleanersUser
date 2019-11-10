package com.tm.cdc.calcuttadrycleanersuser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SlideAdapter extends PagerAdapter
{
    Context context;
    LayoutInflater layoutInflater;

    public SlideAdapter(Context context)
    {
        this.context=context;
    }

//Arrays

    public int[] slide_image ={
            R.drawable.shirt,R.drawable.transport,R.drawable.satisfaction
    };

    public String[] slide_heading ={
            "Quality Clean","Free Home Pick-up and Delivery","Customer Satisfaction"
    };

    public String[] slide_desc={
            "Your linens remain in our total quality control process, from pick-up to delivery. We never outsource, so you can rest assured that your linens will always remain in our diligent care.",
            "We guarantee our products are picked up and delivered on time so you have the right inventory when you need it.",
            "All linen products are serviced to our highest quality standards, Your satisfaction is our only goal!"
    };
    @Override
    public int getCount()
    {
        return slide_heading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(RelativeLayout) object;
    }
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.slide_layout,container,false);
        ImageView imageView=(ImageView)view.findViewById(R.id.slide_image);
        TextView textView=(TextView)view.findViewById(R.id.slide_heading);
        TextView textView1=(TextView)view.findViewById(R.id.slide_description);
        imageView.setImageResource(slide_image[position]);
        textView.setText(slide_heading[position]);
        textView1.setText(slide_desc[position]);
        container.addView(view);
        return  view;


    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
