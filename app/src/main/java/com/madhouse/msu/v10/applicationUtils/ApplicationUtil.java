package com.madhouse.msu.v10.applicationUtils;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.madhouse.msu.v10.R;
import com.madhouse.msu.v10.MSU.LoginScreen;
import com.madhouse.msu.v10.webServices.Webservice;
import com.madhouse.msu.v10.webServices.WebserviceImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.support.v4.app.ActivityCompat.requestPermissions;

/**
 * Created by Krishna on 5/3/2016.
 */
public final class ApplicationUtil {

    public static ApplicationUtil instance;
    public Webservice webservice;
    private static final String TAG = "Contacts";

    private ApplicationUtil(){
        instance = this;
    }

    public static ApplicationUtil getInstance() {

        if(instance == null){
            instance = new ApplicationUtil();
        }
        return instance;
    }
    // check internet connection
    public boolean checkInternetConnection(Context activity){
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        return connected;


    }

    //decode base64
    public Bitmap decodeBase64(String input, Context activity){

        byte[] decodeByte = Base64.decode(input.trim(),activity.MODE_WORLD_READABLE);
        System.out.println("....1");
        return BitmapFactory.decodeByteArray(decodeByte, 0, decodeByte.length);
    }


    //get alert dialog
    public AlertDialog getAlertDialog(String msg, Context context){
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage(msg);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                alertDialog.dismiss();
            }
        });
        return alertDialog;
    }


    //get alert dialog
    public AlertDialog showAlertDialogWithTitle(String title, String msg,String pos_btn, String neg_btn, int view, Context context){

       final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        // Setting Dialog Title
       // alertDialog.setTitle(title);
        // Setting Dialog Message
      //  alertDialog.setMessage(msg);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(view, null);
        builder.setView(dialogView);
        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.dialog_icon);
        // Setting Positive "Yes" Button
      /*  if(!option1.equalsIgnoreCase("")) {
            alertDialog.setPositiveButton(option1,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                          //  finish();
                        }
                    });
        }
        // Setting Negative "NO" Button
        if(!option2.equalsIgnoreCase("")) {
            alertDialog.setNegativeButton(option2,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event
                            dialog.cancel();
                        }
                    });
        }*/
        // Showing Alert Message
       // alertDialog.show();
        TextView msg_title = (TextView) dialogView.findViewById(R.id.alert_title);
        msg_title.setText(title);

        TextView messageDescription = (TextView) dialogView.findViewById(R.id.message_description);
        messageDescription.setText(msg);

        Button negative = (Button) dialogView.findViewById(R.id.dialogButtonNO);
        Button positive = (Button) dialogView.findViewById(R.id.dialogButtonYES);

       final AlertDialog alertDialog = builder.create();

        if(pos_btn.length()==0){
            positive.setVisibility(View.GONE);
        }
        else{
        }

        if(neg_btn.length()==0){

        }
        else{
            negative.setText("DISMISS");
            negative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.cancel();
                }
            });
        }



        return alertDialog;
    }


    //get IMEI number
    public String getImeiNo(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    // is service running in foreground
    public boolean isRunningInForeground(Context context) {
        ActivityManager manager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = manager.getRunningTasks(1);
        for(int i=0;i<tasks.size();i++)
        {
            System.out.println("Task-------"+tasks.get(i));
        }
        System.out.println("Task-------"+tasks.get(0).baseActivity);
        System.out.println("Task-------"+tasks.get(0).numActivities);
        if (tasks.isEmpty()) {
            return false;
        }
        String topActivityName = tasks.get(0).topActivity.getPackageName();
        System.out.println(".............topActivity = " +topActivityName);
        System.out.println(".............packageName = " +context.getPackageName());
        return topActivityName.equalsIgnoreCase(context.getPackageName());
    }


    // check service running or not
    public boolean isMyServiceRunning(String servicename, Activity context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            System.out.println(".............service.service.getClassName() = "+ service.service.getClassName());
            if (servicename.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    // URL to Bitmap conversion
    public boolean getBitmapFromURL(String src, String key, Context context) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            if(connection.getInputStream() !=null){

                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                ByteArrayOutputStream baos=new  ByteArrayOutputStream();
                myBitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
                byte[] arr = baos.toByteArray();
                String imageString= Base64.encodeToString(arr, Base64.DEFAULT);
                System.out.println("..........."+ key);
                System.out.println("..........."+ imageString);
                SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor edit=shre.edit();
                edit.putString(key, imageString);
                edit.commit();
                return true;
            }else{
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }



    public Webservice getWebservice() {
        webservice = new WebserviceImpl();
        return webservice;
    }


    public static void showLogoutDialog(final Context context, String title, String msg) {
        //final android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(context);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder( context);
        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(msg);
        alertDialog.setIcon(R.mipmap.ic_warning_black_24dp);
        // Setting Icon to Dialog
        // alertDialog.setIcon(R.drawable.dialog_icon);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {

                UserPreferences userPreferences = UserPreferences.getInstance(context);


                userPreferences.setIS_REGISTERED("NO");
                userPreferences.setISLOGGEDIN("NO");

                userPreferences.setUser_role("");
                userPreferences.setDOB("");
                userPreferences.setUserid("");

                userPreferences.setIS_REGISTERED("");
                userPreferences.setADDRESS("");
                userPreferences.setANS1("");
                userPreferences.setANS2("");
                userPreferences.setANS3("");
                userPreferences.setANS4("");
                userPreferences.setANS5("");
                userPreferences.setEmail("");
                userPreferences.setNAME("");
                userPreferences.setGOV_ID("");
                userPreferences.setGOV_ID_PIC("");
                userPreferences.setID_TYPE("");
                userPreferences.setMARITAL_STATUS("");
                userPreferences.setPRIMARY_MOB("");
                userPreferences.setPassword("");
                userPreferences.setPREFERENCE_HOBBY("");
                userPreferences.setPREFERENCE_PROFICIENCY("");
                userPreferences.setPROFILE_PIC("");
                userPreferences.setSEC_MOB("");
                userPreferences.setUNIQUE_ID("");


                Intent i = new Intent(context, LoginScreen.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(i);
                System.exit(0);
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    public static void showVersion(final Context context, String title, String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        // Setting Dialog Title
        alertDialog.setTitle(title);
        // Setting Dialog Message
        alertDialog.setMessage(msg);
        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.dialog_icon);
        // Setting Positive "Yes" Button
       /* alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity) context).finish();
                    }
                });*/
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });
        // Showing Alert Message
        alertDialog.show();
    }


    public static void showBackDialog(final Context context, String title, String msg) {
       AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        // Setting Dialog Title
        alertDialog.setTitle(title);
        // Setting Dialog Message
        alertDialog.setMessage(msg);
        // Setting Icon to Dialog
        //alertDialog.setIcon(R.drawable.dialog_icon);
        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity) context).finish();
                    }
                });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.cancel();
                    }
                });
        // Showing Alert Message
        alertDialog.show();
    }



    public static boolean isConnectingToInternet(Context context) {
        if (context != null) {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)
                    for (int i = 0; i < info.length; i++)
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
            }
        }

        return false;
    }



    public static String getDeviceId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }



    public static void hideSoftKeyBoard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }


    public static void showSoftKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }



    public static boolean eMailValidation(String emailstring) {
        if (null == emailstring || emailstring.length() == 0) {
            return false;
        }
        Pattern emailPattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher emailMatcher = emailPattern.matcher(emailstring);
        return emailMatcher.matches();
    }
    public static void showToast(String msg, Context ctx) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }


    public static void showNetworkErrorMessage(final Context context) {
        android.support.v7.app.AlertDialog.Builder dlg = new android.support.v7.app.AlertDialog.Builder(context);
        dlg.setCancelable(false);
        dlg.setTitle("Error");
        dlg.setMessage("Network error has occured. Please check the network status of your phone and retry");
        dlg.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dlg.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ((Activity) context).finish();
                System.exit(0);
            }
        });
        dlg.show();
    }



    public String addNotificationKey(String senderId, String userEmail, String[] registrationId, String idToken)
                                    throws IOException, JSONException {

        URL url = new URL("https://android.googleapis.com/gcm/notification");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setDoOutput(true);

        // HTTP request header
        con.setRequestProperty("project_id", senderId);
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Authorization", "key=" + "AIzaSyBfAYc8T2zJp_UGkEbde6nFgM3xeMOUlo4");
        con.setRequestMethod("POST");
        con.connect();

        // HTTP request
        JSONObject data = new JSONObject();
        data.put("operation", "create");
        data.put("notification_key_name", userEmail);
        data.put("registration_ids", new JSONArray(Arrays.asList(registrationId)));
      //  data.put("id_token", idToken);

        OutputStream os = con.getOutputStream();
        os.write(data.toString().getBytes("UTF-8"));
        os.close();

        // add ids
      /*  data.put("operation", "create");
        data.put("notification_key_name", userEmail);
        data.put("registration_ids", new JSONArray(Arrays.asList(registrationId)));
        data.put("id_token", idToken);

        os.write(data.toString().getBytes("UTF-8"));
        os.close();*/

        // Read the response into a string
//        HTTP/1.0 200 OK
 //       HTTP/1.0 401 Unauthorized
        InputStream is = null;

        int responseCode =  con.getResponseCode();
        Log.e(".Get Notif Err Code.:", responseCode+"");

        if(responseCode >200 && responseCode<400){
           is = con.getInputStream();
        }
        else{
            is = con.getErrorStream();
        }


        String responseString = new Scanner(is, "UTF-8").useDelimiter("\\A").next();
        is.close();

        // Parse the JSON string and return the notification key
        JSONObject response = new JSONObject(responseString);
        return response.getString("notification_key");

    }


    private void insertDummyContact(Context context) {
        // Two operations are needed to insert a new contact.
        ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>(2);

        // First, set up a new raw contact.
        ContentProviderOperation.Builder op =
                ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                        .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null);
        operations.add(op.build());

        // Next, set the name for the contact.
        op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        "__DUMMY CONTACT from runtime permissions sample");
        operations.add(op.build());

        // Apply the operations.
        ContentResolver resolver = context.getContentResolver();
        try {
            resolver.applyBatch(ContactsContract.AUTHORITY, operations);
        } catch (RemoteException e) {
            Log.d(TAG, "Could not add a new contact: " + e.getMessage());
        } catch (OperationApplicationException e) {
            Log.d(TAG, "Could not add a new contact: " + e.getMessage());
        }
    }


    public void  showPermiDialog(final Context context, String title, String msg) {
        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions((Activity) context, new String[] {Manifest.permission.WRITE_CONTACTS},
                                    ApplicationConstants.REQUEST_CODE_ASK_PERMISSIONS);
                            dialog.dismiss();
                        }
                    }
                });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event
                        dialog.dismiss();
                    }
                });
        // Showing Alert Message
        alertDialog.show();
    }


//    public String getAccount(Context con) {
//        Account[] accounts = AccountManager.get(con).getAccountsByType("com.google");
//        if (accounts.length == 0) {
//            return null;
//        }
//        return accounts[0].name;
//    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }


    /**
     * @param encodedString
     * @return bitmap (from given string)
     */
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }


    public boolean checkLocationEnable(final Context context){

        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        boolean isenable = false;


        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            // notify user

            isenable = false;

            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setMessage(context.getResources().getString(R.string.gps_network_not_enabled));
            dialog.setPositiveButton(context.getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton(context.getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();

        }
        else{
            isenable = true;
        }

        return isenable;
    }



}
