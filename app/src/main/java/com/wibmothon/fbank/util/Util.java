package com.wibmothon.fbank.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sound.waves.LogHelper;
import com.wibmothon.fbank.model.UserData;
import com.wibmothon.fbank.ui.Dashboard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import androidx.annotation.NonNull;

public class Util {
    private final static String TAG = "Util";
    private static FirebaseDatabase database;
    private static Map<String, CharSequence> mapLang = new HashMap<>();

    public static class RegHandler extends Handler {

        public final static int MSG_SET_RECG_TEXT = 1;
        public final static int MSG_RECG_START = 2;
        public final static int MSG_RECG_END = 3;

        private StringBuilder mTextBuilder = new StringBuilder();
        private TextView mRecognisedTextView;

        public RegHandler(TextView textView) {
            mRecognisedTextView = textView;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SET_RECG_TEXT:
                    char ch = (char) msg.arg1;
                    mTextBuilder.append(ch);
                    if (null != mRecognisedTextView) {
                        //mRecognisedTextView.setText(mTextBuilder.toString());
                    }
                    break;

                case MSG_RECG_START:
                    mTextBuilder.delete(0, mTextBuilder.length());
                    break;

                case MSG_RECG_END:
                    LogHelper.d(TAG, "recognition end");
                    break;
            }
            super.handleMessage(msg);
        }
    }

    /**
     * Connect to Fdb and get all values
     *
     * @return true if Fdb fetching success and false for if Fdb fetching failure
     */
    public static boolean getDataFromFirebase() {
        Log.d(TAG, "GetFrm");
        final FirebaseApp mFirebaseApp = FirebaseApp.getInstance();
        database = FirebaseDatabase.getInstance(mFirebaseApp);
        DatabaseReference dbRef;
        try {
            dbRef = database.getReference("FMint");

            // Attach a listener to read the data at our posts reference
            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d(TAG, "onDataChange Key " + dataSnapshot.getKey());
                    Log.d(TAG, "onDataChange Value " + dataSnapshot.getValue());
                    //Value {Sbalance=5000, Rbalance=50000, balance=135000, Vbalance=80000}


                        for (DataSnapshot dataSnapLangs : dataSnapshot.getChildren()) {
                            if (dataSnapLangs.getKey() != null  && dataSnapLangs.getKey().equalsIgnoreCase("Sbalance")){
                                UserData.sBal =  dataSnapLangs.getValue().toString();
                            }else if (dataSnapLangs.getKey() != null  && dataSnapLangs.getKey().equalsIgnoreCase("Vbalance")){
                                UserData.vBal =  dataSnapLangs.getValue().toString();
                            }else if (dataSnapLangs.getKey() != null  && dataSnapLangs.getKey().equalsIgnoreCase("Rbalance")){
                                UserData.rBal =  dataSnapLangs.getValue().toString();
                            }else if (dataSnapLangs.getKey() != null  && dataSnapLangs.getKey().equalsIgnoreCase("Balance")){
                                UserData.balance =  dataSnapLangs.getValue().toString();
                            }else  if (dataSnapLangs.getKey() != null  && dataSnapLangs.getKey().equalsIgnoreCase("Name")){
                                UserData.name =  dataSnapLangs.getValue().toString();
                            }else if (dataSnapLangs.getKey() != null  && dataSnapLangs.getKey().equalsIgnoreCase("Rname")){
                                UserData.rName =  dataSnapLangs.getValue().toString();
                            }else if (dataSnapLangs.getKey() != null  && dataSnapLangs.getKey().equalsIgnoreCase("SName")){
                                UserData.sName =  dataSnapLangs.getValue().toString();
                            }
                        }


        }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, "The read failed: " + databaseError.getCode());
                }
            });
        } catch (Exception ex) {
            Log.e(TAG, "Error: " + ex);
        }

        return true;


    }

    /**
     * Connect to Fdb and set all values
     *
     * @return true if Fdb fetching success and false for if Fdb fetching failure
     */
    public static boolean setDataFromFirebase(Context ctx,String key, String balance) {
        Log.d(TAG, "GetFrm");
        final FirebaseApp mFirebaseApp = FirebaseApp.getInstance();
        database = FirebaseDatabase.getInstance(mFirebaseApp);
        DatabaseReference dbRef;

        dbRef = database.getReference("FMint");
        dbRef.getRef().child(key).setValue(balance)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ctx, "Data stored", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ctx, "Error", Toast.LENGTH_LONG).show();
                    }
                });
        return true;
    }

}
