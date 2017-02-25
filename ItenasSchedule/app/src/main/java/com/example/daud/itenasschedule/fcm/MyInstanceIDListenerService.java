package com.example.daud.itenasschedule.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Debam on 8/4/2016.
 */

public class MyInstanceIDListenerService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e("TOKEN", "Refreshed token: " + refreshedToken);
//        Log.e("TOKEN", "Refreshed token: " + FirebaseInstanceId.getToken());
    }


}
