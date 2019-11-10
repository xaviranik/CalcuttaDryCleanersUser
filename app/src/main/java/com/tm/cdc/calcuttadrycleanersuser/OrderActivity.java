package com.tm.cdc.calcuttadrycleanersuser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {

    Button orderButton;
    TextView orderText;
    ProgressBar orderProgressBar;
    ImageView headerImage;


    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    String userPhoneNumber = currentUser.getPhoneNumber();
    FirebaseFirestore mFireStore = FirebaseFirestore.getInstance();

    String orderID = "";
    String userAddress= "";

    DocumentReference canOrderDoc = mFireStore.document("UserInfo/" + userPhoneNumber + "/CurrentOrder/CanOrder");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        orderButton = (Button) findViewById(R.id.orderButton);
        orderText = (TextView) findViewById(R.id.orderText);
        orderProgressBar = (ProgressBar) findViewById(R.id.orderProgressBar);
        headerImage = (ImageView) findViewById(R.id.headerImage);

        checkForCurrentOrderStatus();
        getUserInfo();

        orderButton.setVisibility(View.INVISIBLE);

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(OrderActivity.this);
                builder.setTitle("Confirm Order!")
                        .setMessage("Are you sure you want to place an order?")
                        .setPositiveButton("Place Order", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                setOrder();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                // do nothing
                            }
                        })
                        .setIcon(R.drawable.laundry_basket)
                        .show();
            }
        });
    }

    private void getUserInfo()
    {
        orderProgressBar.setVisibility(View.VISIBLE);

        final DocumentReference userInfoDoc = mFireStore.document("UserInfo/" + userPhoneNumber);
        userInfoDoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                if(documentSnapshot.exists())
                {
                    orderProgressBar.setVisibility(View.INVISIBLE);
                    userAddress = documentSnapshot.getString("address");
                    orderButton.setVisibility(View.VISIBLE);
                    Log.i("UserAddress", userAddress);
                }
            }
        });

    }

    private void setOrder()
    {
        orderButton.setEnabled(false);
        orderButton.setText("Placing Order");
        orderProgressBar.setVisibility(View.VISIBLE);

        //Setting the order

        final String currentDate;

        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        currentDate = formatter.format(todayDate);
        Log.i("Date", "" + currentDate);

        final DocumentReference orderIDdoc = mFireStore.document("OrderIndex/Index");
        orderIDdoc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot)
            {
                //Assigning order id
                if(documentSnapshot.exists())
                {
                    String currentOrderID = documentSnapshot.getString("id");

                    orderID = currentDate + "-"  + currentOrderID;

                    Log.i("UserOrderBoolean", "" + orderID);

                    //Setting next ID

                    String nextID = String.valueOf(Integer.parseInt(currentOrderID) + 1);

                    Map<String, Object> nextIDIndex = new HashMap<>();
                    nextIDIndex.put("id", nextID);

                    orderIDdoc.set(nextIDIndex, SetOptions.merge());
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Try again Later!", Toast.LENGTH_SHORT).show();
                    return;
                }


                //Adding the order in database


                DocumentReference allOrdersDoc = mFireStore.document("AllOrders/" + orderID);

                final UserOrder userOrder = new UserOrder(false, false, false, userPhoneNumber, userAddress,null);

                allOrdersDoc.set(userOrder, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>()
                {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        Map<String, Object> canOrder = new HashMap<>();
                        canOrder.put("canOrder", false);

                        canOrderDoc.set(canOrder).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid)
                            {
                                Toast.makeText(getApplicationContext(), "Order Placed!", Toast.LENGTH_SHORT).show();
                                //orderButton.setEnabled(true);
                                orderProgressBar.setVisibility(View.INVISIBLE);

                            }
                        });
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                orderProgressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Something is broken!", Toast.LENGTH_SHORT).show();
            }
        });





    }

    private void checkForCurrentOrderStatus()
    {
        orderButton.setEnabled(false);

        canOrderDoc.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e)
            {
                if(documentSnapshot.exists())
                {
                    boolean canOrder = documentSnapshot.getBoolean("canOrder");
                    if(canOrder)
                    {
                        orderButton.setText("Place Order");
                        orderText.setText("Press the button below to place an order. CDC team will be at your door in no time!");
                        orderButton.setEnabled(true);
                        headerImage.setImageResource(R.drawable.basket);
                    }
                    else
                    {
                        orderButton.setText("Your order has been placed!");
                        orderText.setText("CDC team will be at your door in any moment!");
                        orderButton.setEnabled(false);
                        headerImage.setImageResource(R.drawable.delivery);
                    }
                }
            }
        });


    }

    /**/






















}
