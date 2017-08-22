package com.madhouse.msu.v10.services;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.madhouse.msu.v10.applicationUtils.FCMConfig;
import com.madhouse.msu.v10.applicationUtils.NotificationUtils;
import com.madhouse.msu.v10.applicationUtils.UserPreferences;
import com.madhouse.msu.v10.bean.Message;
import com.madhouse.msu.v10.MSU.MainActivity;
import com.madhouse.msu.v10.database.DBHandler;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
 
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

 
    private NotificationUtils notificationUtils;
    private DBHandler db;
    private UserPreferences userPreferences;



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());
        db = new DBHandler(getApplicationContext());
        userPreferences = UserPreferences.getInstance(getApplicationContext());
 
        if (remoteMessage == null)
            return;
 
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }
 
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
           // Log.e(TAG, "arr: " + remoteMessage.getData().values().toArray());
            //String[] x = new String[remoteMessage.getData().values().toArray().length];
           // x = (String[]) remoteMessage.getData().values().toArray();
            //Log.e(TAG, "val1: " + x[0]);

 
            try {
                Map<String, String> data = remoteMessage.getData();
                JSONObject json = new JSONObject(data);
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }
 
    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(FCMConfig.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
 
            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }else{
            // If the app is in background, firebase itself handles the notification
        }
    }
 
    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());
 
        try {

            String chatId="", title="",message="",imageUrl="",timestamp="", textsel="", sender_empId="", receiver_empid="";
            boolean isBackground=false;
            JSONObject payload;

           // JSONObject data = json.getJSONObject("data");
            if(json.has("chat_id")){
                chatId = json.getString("chat_id");
            }

            if(json.has("title")){
                title = json.getString("title");
            }
            if(json.has("message")){
                message = json.getString("message");
            }
            if(json.has("is_background")) {
                isBackground = json.getBoolean("is_background");
            }
            if(json.has("image")) {
                imageUrl = json.getString("image");
            }
            if(json.has("time")) {
                timestamp = json.getString("time");
            }
            //if(json.has("payload")) {
            //    payload = json.getJSONObject("payload");
            //}
            if(json.has("textselect")) {
                textsel = json.getString("textselect");
            }

            if(json.has("emp_id")) {
                sender_empId = json.getString("emp_id");
            }
            if(json.has("emp_id_rcv")) {
                receiver_empid = json.getString("emp_id_rcv");
            }



            Log.e(TAG, "chatID: " + chatId);
            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);
            //Log.e(TAG, "payload: " + payload.);
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);
            Log.e(TAG, "textsel...........: " + textsel);
            Log.e(TAG, "empId...........: " + sender_empId);
            Log.e(TAG, "empId...........: " + receiver_empid);


            //userPreferences.setLocalMsgId("1");
           // int msgId = Integer.parseInt(userPreferences.getLocalMsgId())+1;

            Message newMsg = new Message();
           // newMsg.setMSGID(msgId+"");
            newMsg.setSEND_TO(receiver_empid);
            newMsg.setMSGTYP("");
            newMsg.setMSGIMG(imageUrl);
            newMsg.setMSGREAD("U");
            newMsg.setMSGDATE(timestamp);
            newMsg.setMSGTITLE(title);
            newMsg.setMSGDESC(message);
            newMsg.setSEND_BY(sender_empId);
            newMsg.setERRORMSG("");

           // userPreferences.setLocalMsgId(msgId+"");
 
            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(FCMConfig.PUSH_NOTIFICATION);

                //List<Message> updatedMsg = db.getAllMsgs();

              //  for (Message msg : updatedMsg) {
              //          if(!msg.getMSGDESC().equals(newMsg.getMSGDESC())){
                if(newMsg!=null) {
                   // db.addMsg(newMsg);
                   // db.addEmp(newMsg);
                }
            //            }
             //   }

                //pushNotification.putExtra("message",  newMsg);
                userPreferences.setNotiActivity("no");
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
 
                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            }

            else {
                // app is in background, show the notification in notification tray
                if(newMsg!=null) {
                   // db.addMsg(newMsg);
                   // db.addEmp(newMsg);
                }
                userPreferences.setNotiActivity("yes");
                Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
                //resultIntent.putExtra("message", newMsg);
               // resultIntent.putExtra("empType","R02");
 
                // check for image attachment
                if (TextUtils.isEmpty(imageUrl)) {
                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
                } else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }
 
    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }
 
    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}