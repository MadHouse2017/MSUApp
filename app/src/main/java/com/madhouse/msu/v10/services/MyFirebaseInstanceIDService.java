package com.madhouse.msu.v10.services;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.madhouse.msu.v10.applicationUtils.FCMConfig;
import com.madhouse.msu.v10.applicationUtils.UserPreferences;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();
    private UserPreferences userPreferences;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();



      /*  try {
            FirebaseInstanceId.getInstance().deleteInstanceId();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        // Saving reg id to shared preferences
        //storeRegIdInPref(refreshedToken);
 
        // sending reg id to your server
        sendRegistrationToServer(refreshedToken);
 
        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(FCMConfig.REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }
 
    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        Log.e(TAG, "sendRegistrationToServer: " + token);
        userPreferences = UserPreferences.getInstance(MyFirebaseInstanceIDService.this);
        userPreferences.setDevice_fcm_id(token);
    }
 
    private void storeRegIdInPref(String token) {
        userPreferences = UserPreferences.getInstance(MyFirebaseInstanceIDService.this);
        userPreferences.setDevice_fcm_id(token);
       // SharedPreferences pref = getApplicationContext().getSharedPreferences(FCMConfig.SHARED_PREF, 0);
       // SharedPreferences.Editor editor = pref.edit();
       // editor.putString("regId", token);
       // editor.commit();
    }
}