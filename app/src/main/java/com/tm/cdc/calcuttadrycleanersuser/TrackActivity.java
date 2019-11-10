package com.tm.cdc.calcuttadrycleanersuser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrackActivity extends AppCompatActivity {

    RecyclerView orderList;
    RecyclerView.Adapter orderlistAdapter;
    List<UserOrderStrings> userOrderStringsList;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore mFireStore = FirebaseFirestore.getInstance();

    CollectionReference userOrderCol = mFireStore.collection("AllOrders");

    String userPhoneNum;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        orderList = (RecyclerView) findViewById(R.id.main_list);
        orderList.setHasFixedSize(true);
        orderList.setLayoutManager(new LinearLayoutManager(this));
        userOrderStringsList = new ArrayList<>();

        trackOngoingOrders();
    }

    private void trackOngoingOrders()
    {
        userPhoneNum = mAuth.getCurrentUser().getPhoneNumber();


        userOrderCol.whereEqualTo("userPhoneNumber", userPhoneNum)
                .whereEqualTo("tracking", true)
                .whereEqualTo("accepted", true)
                .whereEqualTo("completed", false)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e)
                    {
                        if(e != null)
                        {
                            Log.i("Error", "" + e.getMessage());
                        }

                        for(DocumentChange doc: documentSnapshots.getDocumentChanges())
                        {
                            if(doc.getType() == DocumentChange.Type.ADDED || doc.getType() == DocumentChange.Type.MODIFIED)
                            {
                                UserOrderInfo userOrderInfo = doc.getDocument().toObject(UserOrderInfo.class);

                                String orderID = doc.getDocument().getId();
                                String totalItem = userOrderInfo.getTotalItems();
                                String totalPrice = userOrderInfo.getTotalPrice();
                                String timestamp = DateFormat.getDateTimeInstance().format(userOrderInfo.getTimeStamp());

                                //Log.i("RecylerView", "" + userPhoneNumber +  userAddress + userOrderID);

                                UserOrderStrings userOrder = new UserOrderStrings(orderID, totalPrice, totalItem, timestamp, 1);

                                userOrderStringsList.add(userOrder);

                                orderlistAdapter = new UserOrderAdapter(userOrderStringsList, getApplicationContext());
                                orderList.setAdapter(orderlistAdapter);
                            }


                        }
                    }
                });


    }




























}
