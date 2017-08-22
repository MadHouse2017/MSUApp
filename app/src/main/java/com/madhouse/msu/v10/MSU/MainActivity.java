package com.madhouse.msu.v10.MSU;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationServices;
import com.madhouse.msu.v10.R;
import com.madhouse.msu.v10.applicationUtils.ApplicationUtil;
import com.madhouse.msu.v10.applicationUtils.FCMConfig;
import com.madhouse.msu.v10.applicationUtils.NotificationUtils;
import com.madhouse.msu.v10.applicationUtils.UserPreferences;
import com.madhouse.msu.v10.database.DBHandler;
import com.madhouse.msu.v10.fragments.AboutUs;
import com.madhouse.msu.v10.fragments.ChangePassword;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.madhouse.msu.v10.fragments.ChatUserList;
import com.madhouse.msu.v10.fragments.FAQ;
import com.madhouse.msu.v10.fragments.MSUHome;
import com.madhouse.msu.v10.fragments.Marketplace;
import com.madhouse.msu.v10.fragments.Members_location1;
import com.madhouse.msu.v10.fragments.NSDC_Centers;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    Fragment fragment = null;
    android.support.v4.app.Fragment fragmentx = null;
    private GoogleApiClient client;
    private String role = "";

    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage;
    private UserPreferences userPreferences;
    private DBHandler db;
    private NavigationView navigationView,navigationViewEmp;
    private View headerView, headerViewEmp;
    private final static String TAG_FRAGMENT = "CHAT_FRAG";
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userPreferences = UserPreferences.getInstance(MainActivity.this);

        if(userPreferences.getUser_role().equalsIgnoreCase("R001")){
            setContentView(R.layout.activity_main);
        }
        else {
            setContentView(R.layout.activity_main);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new DBHandler(MainActivity.this);


        mGoogleApiClient = new GoogleApiClient.Builder(MainActivity.this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();

        //FirebaseApp.initializeApp(AppController.getInstance());
        String token = FirebaseInstanceId.getInstance().getToken();
        userPreferences.setDevice_fcm_id(token);
        displayFirebaseRegId();
        //FirebaseApp.initializeApp(AppController.getInstance());


        //fcm
       // txtRegId = (TextView) findViewById(R.id.txt_reg_id);
        txtMessage = (TextView) findViewById(R.id.txt_push_message);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(FCMConfig.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(FCMConfig.TOPIC_GLOBAL);
                   // displayFirebaseRegId();

                }

                else if (intent.getAction().equals(FCMConfig.PUSH_NOTIFICATION)) {
                    // new push notification is received

                 //   Message message = (Message) getIntent().getSerializableExtra("message");
                  /*  Message newMsg = new Message();
                    newMsg.setMSGID("");
                    newMsg.setMSGTYP("");
                    newMsg.setMSGIMG("");
                    newMsg.setMSGREAD("");
                    newMsg.setMSGDATE("");
                    newMsg.setMSGTITLE("");
                    newMsg.setMSGDESC(message);*/

                  //  db.addMsg(message);



                    Toast.makeText(getApplicationContext(), "Push notification: received", Toast.LENGTH_LONG).show();

                  //  txtMessage.setText(message);
                }
            }
        };

       // displayFirebaseRegId();

       /* if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                role= "";
            } else {
                role= extras.getString("empType");
            }
        } else {
            role= (String) savedInstanceState.getSerializable("empType");
        }*/

       /* if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_place, new Home()).commit();
        }*/
        if(userPreferences.getUser_role().equalsIgnoreCase("R001")){
            role = "admin";
        }
        else{

            role = "admin";
        }

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
        headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        if(userPreferences.getUser_role().equalsIgnoreCase("R001")){

            if(userPreferences.getNotiActivity().equalsIgnoreCase("yes")){
                fragmentTransaction.replace(R.id.fragment_place, new MSUHome());
                navigationView.setCheckedItem(R.id.nav_sync);
                userPreferences.setNotiActivity("no");
            }
            else {
                fragmentTransaction.replace(R.id.fragment_place, new MSUHome());
                navigationView.setCheckedItem(R.id.nav_home);
                fragmentTransaction.addToBackStack("tag");
            }
        }
        else{

            fragmentTransaction.replace(R.id.fragment_place, new MSUHome());
            navigationView.setCheckedItem(R.id.nav_home);
            userPreferences.setNotiActivity("no");
        }

        fragmentTransaction.commit();




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        ImageView profileImage = (ImageView) headerView.findViewById(R.id.profilePicture);
        TextView profileName = (TextView) headerView.findViewById(R.id.profileName);
        TextView profileEmail = (TextView) headerView.findViewById(R.id.profileEmail);

        if(userPreferences.getPROFILE_PIC()!=null){
            Glide.with(this).load(userPreferences.getPROFILE_PIC()).into(profileImage);
        }
        else{
            profileImage.setImageResource(R.mipmap.ic_launcher);
        }


        profileName.setText(userPreferences.getNAME()+" | "+userPreferences.getUNIQUE_ID());
        profileEmail.setText(userPreferences.getEmail());

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
       // client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }



    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
           // super.onBackPressed();


            Fragment myFragment = (Fragment) getFragmentManager().findFragmentByTag(TAG_FRAGMENT);
            if (myFragment != null && myFragment.isVisible()) {
                // add your code here
               // getFragmentManager().beginTransaction().replace(R.id.fragment_place, new ).addToBackStack(TAG_FRAGMENT).commit();
               // getFragmentManager().beginTransaction().remove(myFragment).commit();
                getFragmentManager().popBackStack();
            }
            else{
                backButtonHandler();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = 0;
        id = item.getItemId();


        if(role.equalsIgnoreCase("admin")) {

            if (id == R.id.nav_home) {
                fragment = new MSUHome();
                replceFrag();
            }

            if (id == R.id.nav_nsdc_center) {
                if(ApplicationUtil.getInstance().checkLocationEnable(MainActivity.this)) {
                    fragmentx = new NSDC_Centers();
                    replceFragV4();
                }
            }

            if (id == R.id.nav_members_location) {
                if(ApplicationUtil.getInstance().checkLocationEnable(MainActivity.this)) {
                    fragmentx = new Members_location1();
                    replceFragV4();
                }
            }


            if (id == R.id.nav_sync) {
                fragment = new ChatUserList();
                 replceFrag();
            }

            if (id == R.id.nav_marketplace) {
                fragment = new Marketplace();
                replceFrag();
            }

             else if (id == R.id.nav_changepass) {
                 fragment = new ChangePassword();
                 replceFrag();

             }

             else if (id == R.id.nav_logout) {
                 // fragment = new Logout();
                 logoutButtonHandler();
             }


                else if (id == R.id.nav_faq) {
                fragment = new FAQ();
                replceFrag();
                }

             else if (id == R.id.nav_version) {
                 showVersion();
             }

             else if (id == R.id.nav_aboutus) {
                 fragment = new AboutUs();
                 replceFrag();
             }
        }



        /*if (id != R.id.nav_logout || id != R.id.nav_version) {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_place, fragment);
            fragmentTransaction.commit();
        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replceFrag() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fragment);
        fragmentTransaction.commit();
    }

    public void replceFragV4() {
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fragmentx);
        fragmentTransaction.commit();
    }



    public void showVersion() {
        ApplicationUtil.getInstance().showVersion(MainActivity.this, "Application Version" , "BSES Messanger 1.0" );
    }


    public void logoutButtonHandler() {
        ApplicationUtil.getInstance().showLogoutDialog(MainActivity.this, "Logout ?" , "Are you sure you want to Logout the Account?" );
    }


    public void backButtonHandler() {
        ApplicationUtil.getInstance().showBackDialog(MainActivity.this, "Leave application?" , "Are you sure you want to leave the application?" );
    }



    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        //SharedPreferences pref = getApplicationContext().getSharedPreferences(FCMConfig.SHARED_PREF, 0);
        String regId = userPreferences.getDevice_fcm_id();

        Log.e(TAG, "Firebase reg id: " + regId);
        //if (!TextUtils.isEmpty(regId))
           // txtRegId.setText("Firebase Reg Id: " + regId);
       // else
           // txtRegId.setText("Firebase Reg Id is not received yet!");
    }


    @Override
    protected void onResume() {
        super.onResume();


        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(FCMConfig.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(FCMConfig.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());

    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }



  /*  @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.bses.krishna.bses_im_v10.bsesIM/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.bses.krishna.bses_im_v10.bsesIM/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }*/
}
