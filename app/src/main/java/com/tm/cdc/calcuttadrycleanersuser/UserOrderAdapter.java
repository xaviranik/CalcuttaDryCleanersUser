package com.tm.cdc.calcuttadrycleanersuser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class UserOrderAdapter extends RecyclerView.Adapter<UserOrderAdapter.ViewHolder> {

    List<UserOrderStrings> userOrderStringsList;
    Context context;

    public UserOrderAdapter(List<UserOrderStrings> userOrderStringsList, Context context)
    {
        this.userOrderStringsList = userOrderStringsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position)
    {
        UserOrderStrings userOrderStrings = userOrderStringsList.get(position);

        final String orderID = userOrderStrings.getOrderID();

        holder.userOrderID.setText("Order ID :   " +orderID);
        holder.totalItem.setText("Total Items :   " + userOrderStrings.getTotalItems());
        holder.totalPrice.setText("Total Price :   " +userOrderStrings.getTotalPrice());
        holder.timeStamp.setText("Ordered on :   " +userOrderStrings.getTimeStamp());

        switch (userOrderStrings.orderStatus)
        {
            case 1:
            {
                holder.iconImage.setImageResource(R.drawable.washing);
                break;
            }
            case 2:
            {
                holder.iconImage.setImageResource(R.drawable.checked);
                holder.orderStatus.setText("Completed");
                holder.orderStatusBG.setImageResource(android.R.color.holo_green_dark);
                break;
            }
        }


    }

    @Override
    public int getItemCount()
    {
        return userOrderStringsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView userOrderID, totalItem, totalPrice, timeStamp, orderStatus;
        ImageView iconImage, orderStatusBG;
        RelativeLayout relativeLayout;

        public ViewHolder(View itemView)
        {
            super(itemView);
            userOrderID = (TextView) itemView.findViewById(R.id.userOrderIDTextC);
            totalItem = (TextView) itemView.findViewById(R.id.orderItemNumber);
            totalPrice = (TextView) itemView.findViewById(R.id.orderBillC);
            timeStamp = (TextView) itemView.findViewById(R.id.timeStampText);
            iconImage = (ImageView) itemView.findViewById(R.id.iconImage);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.itemRelativeLayout);
            orderStatus = (TextView) itemView.findViewById(R.id.orderStatus);
            orderStatusBG = (ImageView) itemView.findViewById(R.id.orderStatusBG);


        }
    }























}
