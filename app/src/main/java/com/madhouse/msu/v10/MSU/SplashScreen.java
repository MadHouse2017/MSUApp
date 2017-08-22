package com.madhouse.msu.v10.MSU;

/**
 * Created by Krishna on 8/7/2017.
 */
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.madhouse.msu.v10.R;
import com.madhouse.msu.v10.applicationUtils.UserPreferences;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SplashScreen extends Activity {

    /** Duration of wait **/
    private int SPLASH_DISPLAY_LENGTH = 4000;
    Animation animFadein;
    UserPreferences pref;
    private Intent mainIntent;
    private  TextView splashTitle;
    private static final int    PERMISSIONS_REQUEST = 1234;
    private boolean allPermission;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splashscreen);



        splashTitle = (TextView) findViewById(R.id.companytitle);
        pref = UserPreferences.getInstance(SplashScreen.this);


        if (Build.VERSION.SDK_INT >= 23) {
            checkPermissions();
        } else {
            startNextActivity();
        }


        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/

    }

    private void startNextActivity() {

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                try {

                    if (pref.getIS_REGISTERED().equalsIgnoreCase("YES")) {

                        if (pref.getISLOGGEDIN().equalsIgnoreCase("YES")) {

                            if (pref.getUserid() != null && pref.getPassword() != null && pref.getUser_role().equals("R001")) {

                                mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                                mainIntent.putExtra("empType", "admin");
                                SplashScreen.this.startActivity(mainIntent);
                                SplashScreen.this.finish();

                            } else {

                                mainIntent = new Intent(SplashScreen.this, LoginScreen.class);
                                SplashScreen.this.startActivity(mainIntent);
                                SplashScreen.this.finish();
                                //Set Error message
                                // Toast.makeText(LoginScreen.this,"Login Failed, try again",Toast.LENGTH_LONG).show();
                            }

                        } else {

//                            mainIntent = new Intent(SplashScreen.this, MainActivity.class);
//                            mainIntent.putExtra("empType", "admin");
//                            SplashScreen.this.startActivity(mainIntent);
//                            SplashScreen.this.finish();

                            // use below code

                            mainIntent = new Intent(SplashScreen.this, LoginScreen.class);
                            SplashScreen.this.startActivity(mainIntent);
                            SplashScreen.this.finish();

                        }

                    }

                    else {
                        // load the animation
                        // animFadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.together);
                        // set animation listener
                        //animFadein.setAnimationListener(SplashScreen.this);
                        // start the animation
                        //splashTitle.startAnimation(animFadein);

                        mainIntent = new Intent(SplashScreen.this, WelcomeActivity.class);
                        SplashScreen.this.startActivity(mainIntent);
                        SplashScreen.this.finish();

                    }

                }

                catch (Exception er){
                    er.printStackTrace();

                    mainIntent = new Intent(SplashScreen.this, LoginScreen.class);
                    SplashScreen.this.startActivity(mainIntent);
                    SplashScreen.this.finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);


    }



    /** Get the {@link Activity} to start when the splash screen times out. */
    @SuppressWarnings("rawtypes")
    public Class getNextActivityClass() {
        return MainActivity.class;
    };



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1234: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0){

                    for(int i =0; i<grantResults.length;i++){

                        if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                            allPermission = true;
                        }
                        else{
                            allPermission = false;
                        }

                    }

                    if(allPermission) {
                        startNextActivity();
                    }


                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(SplashScreen.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    public String[] getRequiredPermissions() {
        String[] permissions = null;
        try {
            permissions = getPackageManager().getPackageInfo(getPackageName(),
                    PackageManager.GET_PERMISSIONS).requestedPermissions;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (permissions == null) {
            return new String[0];
        } else {
            return permissions.clone();
        }
    }


    /**
     * Check if the required permissions have been granted, and
     *  if they have. Otherwise
     * {@link #requestPermissions(String[], int)}.
     */
    @TargetApi(Build.VERSION_CODES.M)
    private void checkPermissions() {
        String[] ungrantedPermissions = requiredPermissionsStillNeeded();
        if (ungrantedPermissions.length == 0) {
            startNextActivity();
        } else {
            requestPermissions(ungrantedPermissions, PERMISSIONS_REQUEST);
        }
    }

    /**
     * Convert the array of required permissions to a {@link Set} to remove
     * redundant elements. Then remove already granted permissions, and return
     * an array of ungranted permissions.
     */

    @TargetApi(Build.VERSION_CODES.M)
    private String[] requiredPermissionsStillNeeded() {

        Set<String> permissions = new HashSet<String>();
        for (String permission : getRequiredPermissions()) {
            permissions.add(permission);
        }
        for (Iterator<String> i = permissions.iterator(); i.hasNext();) {
            String permission = i.next();
            if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                Log.d(SplashScreen.class.getSimpleName(),
                        "Permission: " + permission + " already granted.");
                i.remove();
            } else {
                Log.d(SplashScreen.class.getSimpleName(),
                        "Permission: " + permission + " not yet granted.");
            }
        }
        return permissions.toArray(new String[permissions.size()]);
    }


//    @Override
//    public void onAnimationStart(Animation animation) {
//
//    }
//
//    @Override
//    public void onAnimationEnd(Animation animation) {
//        // check for fade in animation
//        if (animation == animFadein) {
//            /*Toast.makeText(getApplicationContext(), "Animation Stopped",
//                    Toast.LENGTH_SHORT).show();*/
//        }
//    }
//
//    @Override
//    public void onAnimationRepeat(Animation animation) {
//
//    }
}