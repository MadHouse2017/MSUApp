 package com.madhouse.msu.v10.applicationUtils;

public class ApplicationConstants {

    public static final String APP_ID = "1";
	
	public static final String App_log = "App_log";

	//2208
	public static final String GET_VALIDATE_USER = "MSU_GetValidUser";
	public static final String GET_CHANGE_PASS = "IMSGChangePassword";

	public static final String MSU_CENTERS = "MSU_GetNSDCList";
	public static final String MEMBER_LOCATION = "MSU_GetNearByUsers";


	public static final String GET_PASSWORD_SMS ="GetPasswordSMS";

	//MSU register services
	public static final String WEB_SERVICE_URL= "http://laxmitvs.com/pvcomservice.asmx";
	public static final String TARGE_NAME_SPACE = "http://padhaivadhai.com/";


	/*Web Methods*/
	public static final String APP_LOG = "PVApp_log";
	public static final String MSU_REGITRATION= "MSU_Registration";

	public static final String MSU_GetGroupMembers ="MSU_GetGroupMembers";
	public static final String MSU_SendMsg ="MSU_SendMsg";
	public static final String MSU_GetGroupConversation ="MSU_GetGroupConversation";
	public static final String MSU_GetPearConversation ="MSU_GetPearConversation";

	/*Error Msgs*/
	public static final String  ERROR_MSG_INTERNET_DOWN="You May Be Experiencing Problem With Your Network Service Provider.";
	public static final String  ERROR_MSG_SERVER_DOWN="You May Be Experiencing Problem With Your Network Service Provider.";
	public static final int REQUEST_CODE_ASK_PERMISSIONS= 123;

	/*Response Msgs*/


	public static final String MethodType1 = "insert";
	public static final String MethodType2 = "update";
	public static final String MethodType3 = "view";

	//shared preferences
	public static final String DEVICE_FCM_ID = "DEVICE_FCM_ID";
	public static final String DEVICE_IMEI_ID = "DEVICE_IMEI_ID";
	public static final String USER_ROLE = "USER_role";
	public static final String CURRENT_LOGIN = "CURRENT_LOGIN";
	public static final String STATUS = "STATUS";
	public static final String USER_ID = "USER_ID";
	public static final String NOTI_ACV = "NOTI_ACV";
	public static final String LANGUAGE = "LANGUAGE";


	public static final String PROFILE_PIC = "PROFILE_PIC";
	public static final String ID_TYPE = "ID_TYPE";
	public static final String GOV_ID = "GOV_ID";
	public static final String GOV_ID_PIC = "GOV_ID_PIC";
	public static final String NAME = "NAME";
	public static final String DOB = "DOB";
	public static final String PRIMARY_MOB = "PRIMARY_MOB";
	public static final String SEC_MOB = "SEC_MOB";
	public static final String ADDRESS = "ADDRESS";

	public static final String MARITAL_STATUS = "MARITAL_STATUS";
	public static final String EMAIL = "EMAIL";
	public static final String PASSWORD = "PASSWORD";

	//HOBBY PAGE
	public static final String PREFERENCE_HOBBY = "PREFERENCE_HOBBY";
	public static final String PREFERENCE_PROFICIENCY = "PREFERENCE_PROFICIENCY";

	//QUESTIONARIES PAGE
	public static final String ANS1 = "ANS1";
	public static final String ANS2 = "ANS2";
	public static final String ANS3 = "ANS3";
	public static final String ANS4 = "ANS4";
	public static final String ANS5 = "ANS5";

	public static final String IS_REGISTERED = "IS_REGISTERED";


	public static final String CANYOUDOIMG1 = "CANYOUDOIMG1";
	public static final String CANYOUDOIMG2 = "CANYOUDOIMG2";
	public static final String CANYOUDOIMG3 = "CANYOUDOIMG3";
	public static final String CANYOUDOIMG4 = "CANYOUDOIMG4";
	public static final String CANYOUDOIMG5 = "CANYOUDOIMG5";

	public static final String ISLOGGEDIN = "ISLOGGEDIN";
	public static final String UNIQUE_ID = "UNIQUE_ID";


//FCM
	public static final String SENDER_ID = "617913186155";
	public static final String USER_EMAIL = "krishna.reliance.bses@gmail.com";

	//DB
	// Database Version
	private static final int DATABASE_VERSION = 1;
	// Database Name
	private static final String DATABASE_NAME = "im";
	// Messages table name
	private static final String TABLE_SHOPS = "emp_msg";

	public static final String DATABASE = "CADB";

	// Message Table Columns names
	private static final String MSG_ID = "id";
	private static final String MSG_DATE = "date";
	private static final String MSG_TITLE = "title";
	private static final String MSG_DESC = "desc";
	private static final String MSG_READ = "read";
	private static final String MSG_TYP = "type";
	private static final String MSG_IMG = "img";
	private static final String ERROR_MSG = "msg_err";


    //Registration
	public static final int PROFILE_PICK = 1008;
	public static final int ID_NO = 1010;
	public static final int PROFILE_PICK_GALLERY = 1009;
	public static final int ID_NO_GALLERY = 1011;

	//credential for google
	public static final String GOOGLE_GCM_SENDERID = "61677248047";


}
