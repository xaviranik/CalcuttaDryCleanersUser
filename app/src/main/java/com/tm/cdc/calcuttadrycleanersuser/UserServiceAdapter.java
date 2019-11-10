package com.tm.cdc.calcuttadrycleanersuser;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class UserServiceAdapter extends RecyclerView.Adapter<UserServiceAdapter.ViewHolder> {

    List<UserInvoiceStrings> userInvoiceStringsList;
    Context context;

    public UserServiceAdapter(List<UserInvoiceStrings> userInvoiceStringsList, Context context)
    {
        this.userInvoiceStringsList = userInvoiceStringsList;
        this.context = context;
    }

    @NonNull
    @Override
    public UserServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_services, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserServiceAdapter.ViewHolder holder, int position)
    {
        UserInvoiceStrings userInvoiceStrings = userInvoiceStringsList.get(position);

        holder.itemNameText.setText(userInvoiceStrings.getItemName());
        holder.priceText.setText(userInvoiceStrings.getItemPrice());
        holder.serialText.setText(userInvoiceStrings.getSerialNum());
    }

    @Override
    public int getItemCount()
    {
        return userInvoiceStringsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView itemNameText, priceText, serialText;


        public ViewHolder(View itemView)
        {
            super(itemView);

            itemNameText = (TextView) itemView.findViewById(R.id.itemNameText);
            priceText = (TextView) itemView.findViewById(R.id.itemPriceText);
            serialText = (TextView) itemView.findViewById(R.id.itemSerial);

        }
    }
}
