package com.madhouse.msu.v10.applicationUtils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by krishna on 17/12/15.
 */
public class UserPreferences {

    private static UserPreferences instance;
    private SharedPreferences preferences;
    String isLogoutfromApp="NO";
    boolean isLocalDatacacheSaved;
    boolean isFbLoggedin;
    boolean isGooglePlusloggedin;

    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;
    // Shared preferences file name
    private static final String PREF_NAME = "msu-welcome";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";





    private UserPreferences(Context context) {
        if (context != null) {
            preferences = context.getSharedPreferences(
                    "user_prefrences", Context.MODE_PRIVATE);
        }
    }
    public static synchronized UserPreferences getInstance(Context context) {
        return instance==null?instance = new UserPreferences(context):instance;
    }


    public synchronized String getDevice_fcm_id() {
        return preferences.getString(ApplicationConstants.DEVICE_FCM_ID, null);
    }
    public synchronized void setDevice_fcm_id(String device_fcm_id) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.DEVICE_FCM_ID, device_fcm_id);
        editor.commit();
    }


    public synchronized String getDevice_imei_id() {
        return preferences.getString(ApplicationConstants.DEVICE_IMEI_ID, null);
    }

    public synchronized void setDevice_imei_id(String device_imei_id) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.DEVICE_IMEI_ID, device_imei_id);
        editor.commit();
    }





    public synchronized String getEmail(){
        return preferences.getString(ApplicationConstants.EMAIL,null);
    }
    public synchronized void setEmail(String email){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.EMAIL, email);
        editor.commit();
    }




    public synchronized String getPassword(){
        return preferences.getString(ApplicationConstants.PASSWORD,null);
    }
    public synchronized void setPassword(String password){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.PASSWORD, password);
        editor.commit();
    }




    public synchronized String getUser_role(){
        return preferences.getString(ApplicationConstants.USER_ROLE,null);
    }
    public synchronized void setUser_role(String user_role){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.USER_ROLE, user_role);
        editor.commit();
    }



    public synchronized String getCurrent_login(){
        return preferences.getString(ApplicationConstants.CURRENT_LOGIN,null);
    }
    public synchronized void setCurrent_login(String current_login){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.CURRENT_LOGIN, current_login);
        editor.commit();
    }




//    public synchronized String getLast_login(){
//        return preferences.getString(ApplicationConstants.LAST_LOGIN,null);
//    }
//    public synchronized void setLast_login(String last_login){
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString(ApplicationConstants.LAST_LOGIN, last_login);
//        editor.commit();
//    }




    public synchronized String getStatus(){
        return preferences.getString(ApplicationConstants.STATUS,null);
    }
    public synchronized void setStatus(String status){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.STATUS, status);
        editor.commit();
    }




    /*public synchronized boolean getgoogleloggedinflag(){
        return preferences.getBoolean(ApplicationConstants.googleloggedinflag, false);
    }
    public synchronized void setgoogleloggedinflag(Boolean googlefalg){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(ApplicationConstants.googleloggedinflag, googlefalg);
        editor.commit();
    }


    public synchronized boolean getfbloggedinflag(){
        return preferences.getBoolean(ApplicationConstants.facebookloggedinflag, false);
    }
    public synchronized void setfbloggedinflag(Boolean fbfalg){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(ApplicationConstants.facebookloggedinflag, fbfalg);
        editor.commit();
    }*/

    public synchronized String getUserid(){
        return preferences.getString(ApplicationConstants.USER_ID, null);
    }
    public synchronized void setUserid(String user_id){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.USER_ID, user_id);
        editor.commit();
    }

    public synchronized String getNotiActivity(){
        return preferences.getString(ApplicationConstants.NOTI_ACV, "no");
    }
    public synchronized void setNotiActivity(String user_id){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.NOTI_ACV, user_id);
        editor.commit();
    }


    public void setFirstTimeLaunch(boolean isFirstTime) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return preferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


    public synchronized String getLanguageActivity(){
        return preferences.getString(ApplicationConstants.LANGUAGE, "english");
    }
    public synchronized void setLanguageActivity(String lang){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.LANGUAGE, lang);
        editor.commit();
    }


    // registration Details Data

    public synchronized String getPROFILE_PIC(){
        return preferences.getString(ApplicationConstants.PROFILE_PIC,null);
    }
    public synchronized void setPROFILE_PIC(String data){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.PROFILE_PIC, data);
        editor.commit();
    }

    public synchronized String getID_TYPE(){
        return preferences.getString(ApplicationConstants.ID_TYPE,null);
    }
    public synchronized void setID_TYPE(String data){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.ID_TYPE, data);
        editor.commit();
    }


    public synchronized String getGOV_ID(){
        return preferences.getString(ApplicationConstants.GOV_ID,null);
    }
    public synchronized void setGOV_ID(String data){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.GOV_ID, data);
        editor.commit();
    }


    public synchronized String getGOV_ID_PIC(){
        return preferences.getString(ApplicationConstants.GOV_ID_PIC,null);
    }
    public synchronized void setGOV_ID_PIC(String data){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.GOV_ID_PIC, data);
        editor.commit();
    }


    public synchronized String getNAME(){
        return preferences.getString(ApplicationConstants.NAME,null);
    }
    public synchronized void setNAME(String data){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.NAME, data);
        editor.commit();
    }

    public synchronized String getDOB(){
        return preferences.getString(ApplicationConstants.DOB,null);
    }
    public synchronized void setDOB(String data){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.DOB, data);
        editor.commit();
    }


    public synchronized String getPRIMARY_MOB(){
        return preferences.getString(ApplicationConstants.PRIMARY_MOB,null);
    }
    public synchronized void setPRIMARY_MOB(String data){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.PRIMARY_MOB, data);
        editor.commit();
    }


    public synchronized String getSEC_MOB(){
        return preferences.getString(ApplicationConstants.SEC_MOB,null);
    }
    public synchronized void setSEC_MOB(String data){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.SEC_MOB, data);
        editor.commit();
    }

    public synchronized String getADDRESS(){
        return preferences.getString(ApplicationConstants.ADDRESS,null);
    }
    public synchronized void setADDRESS(String data){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.ADDRESS, data);
        editor.commit();
    }


    public synchronized String getMARITAL_STATUS(){
        return preferences.getString(ApplicationConstants.MARITAL_STATUS,null);
    }
    public synchronized void setMARITAL_STATUS(String data){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.MARITAL_STATUS, data);
        editor.commit();
    }




    public synchronized String getPREFERENCE_HOBBY(){
        return preferences.getString(ApplicationConstants.PREFERENCE_HOBBY,null);
    }
    public synchronized void setPREFERENCE_HOBBY(String data){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.PREFERENCE_HOBBY, data);
        editor.commit();
    }

    public synchronized String getPREFERENCE_PROFICIENCY(){
        return preferences.getString(ApplicationConstants.PREFERENCE_PROFICIENCY,null);
    }
    public synchronized void setPREFERENCE_PROFICIENCY(String data){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.PREFERENCE_PROFICIENCY, data);
        editor.commit();
    }


    public synchronized String getANS1(){
        return preferences.getString(ApplicationConstants.ANS1,null);
    }
    public synchronized void setANS1(String data){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.ANS1, data);
        editor.commit();
    }

    public synchronized String getANS2(){
        return preferences.getString(ApplicationConstants.ANS2,null);
    }
    public synchronized void setANS2(String data){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.ANS2, data);
        editor.commit();
    }

    public synchronized String getANS3(){
        return preferences.getString(ApplicationConstants.ANS3,null);
    }
    public synchronized void setANS3(String data){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.ANS3, data);
        editor.commit();
    }

    public synchronized String getANS4(){
        return preferences.getString(ApplicationConstants.ANS4,null);
    }
    public synchronized void setANS4(String data){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.ANS4, data);
        editor.commit();
    }

    public synchronized String getANS5(){
        return preferences.getString(ApplicationConstants.ANS5,null);
    }
    public synchronized void setANS5(String data){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.ANS5, data);
        editor.commit();
    }

    public synchronized String getIS_REGISTERED(){
        return preferences.getString(ApplicationConstants.IS_REGISTERED,"NO");
    }
    public synchronized void setIS_REGISTERED(String data){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.IS_REGISTERED, data);
        editor.commit();
    }


    public synchronized String getCANYOUDOIMG1(){
        return preferences.getString(ApplicationConstants.CANYOUDOIMG1,"NOT");
    }
    public synchronized void setCANYOUDOIMG1(String data){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.CANYOUDOIMG1, data);
        editor.commit();
    }


    public synchronized String getCANYOUDOIMG2(){
        return preferences.getString(ApplicationConstants.CANYOUDOIMG2,"NOT");
    }
    public synchronized void setCANYOUDOIMG2(String data){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.CANYOUDOIMG2, data);
        editor.commit();
    }

    public synchronized String getCANYOUDOIMG3(){
        return preferences.getString(ApplicationConstants.CANYOUDOIMG3,"NOT");
    }
    public synchronized void setCANYOUDOIMG3(String data){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.CANYOUDOIMG3, data);
        editor.commit();
    }

    public synchronized String getCANYOUDOIMG4(){
        return preferences.getString(ApplicationConstants.CANYOUDOIMG4,"NOT");
    }
    public synchronized void setCANYOUDOIMG4(String data){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.CANYOUDOIMG4, data);
        editor.commit();
    }

    public synchronized String getCANYOUDOIMG5(){
        return preferences.getString(ApplicationConstants.CANYOUDOIMG5,"NOT");
    }
    public synchronized void setCANYOUDOIMG5(String data){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.CANYOUDOIMG5, data);
        editor.commit();
    }

    public synchronized String getISLOGGEDIN(){
        return preferences.getString(ApplicationConstants.ISLOGGEDIN,"NO");
    }
    public synchronized void setISLOGGEDIN(String data){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.ISLOGGEDIN, data);
        editor.commit();
    }

    public synchronized String getUNIQUE_ID(){
        return preferences.getString(ApplicationConstants.UNIQUE_ID,"NO");
    }
    public synchronized void setUNIQUE_ID(String data){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ApplicationConstants.UNIQUE_ID, data);
        editor.commit();
    }


    // client lat long preferences
   /* public synchronized String getClientSourceLat(){
        return preferences.getString("getClientSourceLat", null);
    }

    public synchronized void setClientSourceLat(String user_id){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("getClientSourceLat", user_id);
        editor.commit();
    }



    public synchronized String getClientSourceLong(){
        return preferences.getString("getClientSourceLong", null);
    }

    public synchronized void setClientSourceLong(String user_id){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("getClientSourceLong", user_id);
        editor.commit();
    }*/


    /*
    public synchronized String getClientDestiLat(){
        return preferences.getString("getClientDestiLat", null);
    }

    public synchronized void setClientDestiLat(String user_id){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("getClientDestiLat", user_id);
        editor.commit();
    }



    public synchronized String getClientDestiLong(){
        return preferences.getString("getClientDestiLong", null);
    }

    public synchronized void setClientDestiLong(String user_id) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("getClientDestiLong", user_id);
        editor.commit();
    }



    public synchronized String getNumberofseats(){
        return preferences.getString("getNumberofseats", null);
    }

    public synchronized void setNumberofseats(String user_id) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("getNumberofseats", user_id);
        editor.commit();
    }*/

}
