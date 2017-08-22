package com.madhouse.msu.v10.webServices;

import android.content.Context;

import com.madhouse.msu.v10.applicationUtils.ApplicationConstants;
import com.madhouse.msu.v10.bean.ChangePassBean;
import com.madhouse.msu.v10.bean.ChatUserDetails;
import com.madhouse.msu.v10.bean.Member_Location_Bean;
import com.madhouse.msu.v10.bean.MultiUsrMsgChat;
import com.madhouse.msu.v10.bean.NSDC_Center_Bean;
import com.madhouse.msu.v10.bean.SinglUsrChatList;
import com.madhouse.msu.v10.bean.User;
import com.madhouse.msu.v10.exceptions.ApplicationException;
import com.madhouse.msu.v10.proxies.AppLog;
import com.madhouse.msu.v10.proxies.ChangePasswordProxie;
import com.madhouse.msu.v10.proxies.ChatReplyProxy;
import com.madhouse.msu.v10.proxies.ChatUserDetailsProxy;
import com.madhouse.msu.v10.proxies.MsgChatUserList;
import com.madhouse.msu.v10.proxies.NSDC_Centers_Proxy;
import com.madhouse.msu.v10.proxies.UserRegistrationProxy;
import com.madhouse.msu.v10.proxies.ValidateUserProxie;
import com.madhouse.msu.v10.proxies.members_location_proxy;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;


public class WebserviceImpl implements Webservice{
	Context context;

	public Object retreiveResponse(SoapObject responce, String methodname){


		if(methodname.equalsIgnoreCase(ApplicationConstants.GET_VALIDATE_USER)){


			User user = new User();
			SoapObject soapObject=  (SoapObject) responce.getProperty("MSU_GetValidUserResult");
			SoapObject soapObject1 =(SoapObject)soapObject.getProperty("diffgram");

			if(soapObject1.toString().contains("DocumentElement")){

				SoapObject innerObject = (SoapObject)((SoapObject)((SoapObject)soapObject.getProperty("diffgram")).getProperty("DocumentElement"));
				SoapObject innerInObject = (SoapObject)innerObject.getProperty("ValidateUser");

				try{
					// http://laxmitvs.com/UserPhoto/20178/MSU150817481608.jpeg   -- image path


					//G:\PleskVhosts\laxmitvs.com\httpdocs\UserPhoto\20178\MSU150817492108.jpeg


					if(innerObject.toString().contains("PHOTO")){

						try {

							//String result = "http:\\" + innerInObject.getProperty("PHOTO").toString().substring(16);

							//String[] result = innerInObject.getProperty("PHOTO").toString().split("\"");

							String result = innerInObject.getProperty("PHOTO").toString()+"\\";

							ArrayList<String> updateWords = new ArrayList<String>();
							String newWords="";

							for(int i = 0; i < result.length(); i++) {

								if (result.charAt(i) == '\\') {

									updateWords.add(newWords);
									newWords = "";
								}
								else{
									//updateWords.add();

									newWords = newWords+result.charAt(i);
								}
							}

							String resultImgUrl = "http://laxmitvs.com/" + updateWords.get(updateWords.size()-3) + "/" + updateWords.get(updateWords.size()-2) + "/" + updateWords.get(updateWords.size()-1);


							user.setPHOTO(resultImgUrl);
						}
						catch (Exception er){
							er.printStackTrace();
						}
					}

					if(innerObject.toString().contains("idType")){
						user.setIdType(innerInObject.getProperty("idType").toString());
					}

					if(innerObject.toString().contains("idNumber")){
						user.setIdNumber(innerInObject.getProperty("idNumber").toString());
					}

					if(innerObject.toString().contains("idPhoto")){

						try {

							//String result = "http:\\" + innerInObject.getProperty("PHOTO").toString().substring(16);

							//String[] result = innerInObject.getProperty("PHOTO").toString().split("\"");

							String result = innerInObject.getProperty("idPhoto").toString()+"\\";

							ArrayList<String> updateWords = new ArrayList<String>();
							String newWords="";

							for(int i = 0; i < result.length(); i++) {

								if (result.charAt(i) == '\\') {

									updateWords.add(newWords);
									newWords = "";
								}
								else{
									//updateWords.add();

									newWords = newWords+result.charAt(i);
								}
							}

							String resultImgUrl = "http://laxmitvs.com/" + updateWords.get(updateWords.size()-3) + "/" + updateWords.get(updateWords.size()-2) + "/" + updateWords.get(updateWords.size()-1);


							user.setIdPhoto(resultImgUrl);
						}
						catch (Exception er){
							er.printStackTrace();
						}


					}


					if(innerObject.toString().contains("userName")){
						user.setUserName(innerInObject.getProperty("userName").toString());
					}

					if(innerObject.toString().contains("DOB")){
						user.setDOB(innerInObject.getProperty("DOB").toString());
					}

					if(innerObject.toString().contains("primMobileNo")) {
						user.setPrimMobileNo(innerInObject.getProperty("primMobileNo").toString());
					}

					if(innerObject.toString().contains("secMobileNo")){
						user.setSecMobileNo(innerInObject.getProperty("secMobileNo").toString());
					}


					if(innerObject.toString().contains("ADDRESS")){
						user.setADDRESS(innerInObject.getProperty("ADDRESS").toString());
					}

					if(innerObject.toString().contains("maritalStatus")) {
						user.setMaritalStatus(innerInObject.getProperty("maritalStatus").toString());
					}


					if(innerObject.toString().contains("emailId")){
						user.setEmailId(innerInObject.getProperty("emailId").toString());
					}


					if(innerObject.toString().contains("password")){
						user.setPassword(innerInObject.getProperty("password").toString());
					}

					if(innerObject.toString().contains("gcm_id")){
						user.setGcmId(innerInObject.getProperty("gcm_id").toString());
					}



					if(innerObject.toString().contains("other")){
						user.setOther(innerInObject.getProperty("other").toString());
					}

					if(innerObject.toString().contains("AADHAR_ID")){
						user.setAADHAR_ID(innerInObject.getProperty("AADHAR_ID").toString());
					}

					if(innerObject.toString().contains("MOTHER_NAME_ENG")){
						user.setMOTHER_NAME_ENG(innerInObject.getProperty("MOTHER_NAME_ENG").toString());
					}

					if(innerObject.toString().contains("BHAMASHAH_ID")){
						user.setBHAMASHAH_ID(innerInObject.getProperty("BHAMASHAH_ID").toString());
					}

					if(innerObject.toString().contains("SPOUCE_NAME_HND")){
						user.setSPOUCE_NAME_HND(innerInObject.getProperty("SPOUCE_NAME_HND").toString());
					}

					if(innerObject.toString().contains("M_ID")){
						user.setM_ID(innerInObject.getProperty("M_ID").toString());
					}

					if(innerObject.toString().contains("FAMILYIDNO")){
						user.setFAMILYIDNO(innerInObject.getProperty("FAMILYIDNO").toString());
					}

					if(innerObject.toString().contains("FATHER_NAME_HND")){
						user.setFATHER_NAME_HND(innerInObject.getProperty("FATHER_NAME_HND").toString());
					}

					if(innerObject.toString().contains("NAME_ENG")){
						user.setNAME_ENG(innerInObject.getProperty("NAME_ENG").toString());
					}

					if(innerObject.toString().contains("FATHER_NAME_ENG")){
						user.setFATHER_NAME_ENG(innerInObject.getProperty("FATHER_NAME_ENG").toString());
					}


					if(innerObject.toString().contains("GENDER")){
						user.setGENDER(innerInObject.getProperty("GENDER").toString());
					}

					if(innerObject.toString().contains("NAME_HND")){
						user.setNAME_HND(innerInObject.getProperty("NAME_HND").toString());
					}

					if(innerObject.toString().contains("SPOUCE_NAME_ENG")){
						user.setSPOUCE_NAME_ENG(innerInObject.getProperty("SPOUCE_NAME_ENG").toString());
					}


					if(innerObject.toString().contains("MOTHER_NAME_HND")){
						user.setMOTHER_NAME_HND(innerInObject.getProperty("MOTHER_NAME_HND").toString());
					}


					if(innerObject.toString().contains("USER_ROLE")){
						user.setUSER_ROLE(innerInObject.getProperty("USER_ROLE").toString());
					}

					if(innerObject.toString().contains("PREFERENCE_HOBBY")){
						user.setPREFERENCE_HOBBY(innerInObject.getProperty("PREFERENCE_HOBBY").toString());
					}
					if(innerObject.toString().contains("HOBBY_PROFICIENCY")){
						user.setHOBBY_PROFICIENCY(innerInObject.getProperty("HOBBY_PROFICIENCY").toString());
					}
					if(innerObject.toString().contains("ANS1")){
						user.setANS1(innerInObject.getProperty("ANS1").toString());
					}

					if(innerObject.toString().contains("ANS2")){
						user.setANS2(innerInObject.getProperty("ANS2").toString());
					}

					if(innerObject.toString().contains("ANS3")){
						user.setANS3(innerInObject.getProperty("ANS3").toString());
					}

					if(innerObject.toString().contains("ANS4")){
						user.setANS4(innerInObject.getProperty("ANS4").toString());
					}

					if(innerObject.toString().contains("ANS5")){
						user.setANS5(innerInObject.getProperty("ANS5").toString());
					}

					if(innerObject.toString().contains("IS_REGISTERED")){
						user.setIS_REGISTERED(innerInObject.getProperty("IS_REGISTERED").toString());
					}

					if(innerObject.toString().contains("login_id")){
						user.setUniqueLoginID(innerInObject.getProperty("login_id").toString());
					}



				}
				catch(Exception er){
					er.getStackTrace();
				}

			}
			
			return user;
		}



		if(methodname.equalsIgnoreCase(ApplicationConstants.GET_CHANGE_PASS)){


			ChangePassBean changePass = new ChangePassBean();
			SoapObject soapObject=  (SoapObject) responce.getProperty("IMSGLoginUserResult");
			SoapObject soapObject1 =(SoapObject)soapObject.getProperty("diffgram");

			if(soapObject1.toString().contains("BAPIRESULT")){

				SoapObject innerObject = (SoapObject)((SoapObject)((SoapObject)soapObject.getProperty("diffgram")).getProperty("BAPIRESULT"));
				SoapObject innerInObject = (SoapObject)innerObject.getProperty("iMsgLoginCredentials");

				try{

					if(innerObject.toString().contains("EMPID")){
						changePass.setSuccessMsg(innerObject.getProperty("EMPID").toString());
					}

					if(innerObject.toString().contains("EMPNAME")){
						changePass.setErrorMsg(innerObject.getProperty("EMPNAME").toString());
					}

				}
				catch(Exception er){
					er.getStackTrace();
				}

			}

			return changePass;
		}



		if(methodname.equalsIgnoreCase(ApplicationConstants.MSU_GetPearConversation)){

			ArrayList<SinglUsrChatList> chatUserDetailses = new  ArrayList<SinglUsrChatList>();
			//need to change
			SoapObject soapObject=  (SoapObject) responce.getProperty("MSU_GetPearConversationResult");
			SoapObject soapObject1 =(SoapObject)soapObject.getProperty("diffgram");

			if(soapObject1.toString().contains("DocumentElement")){

				SoapObject innerObject = (SoapObject)((SoapObject)((SoapObject)soapObject.getProperty("diffgram")).getProperty("DocumentElement"));

				System.out.println("..................len = "+ innerObject.getPropertyCount());

				for(int i = 0; i<innerObject.getPropertyCount();i++){

					SinglUsrChatList chatDetail= new SinglUsrChatList();
					SoapObject grpCompObject = (SoapObject)innerObject.getProperty(i);

					if(grpCompObject.toString().contains("login_id_from")){
						chatDetail.setStrUserFrom(grpCompObject.getProperty("login_id_from").toString());
					}
					if(grpCompObject.toString().contains("login_id_to")){
						chatDetail.setStrUserTo(grpCompObject.getProperty("login_id_to").toString());
					}
					if(grpCompObject.toString().contains("pear_msg")){
						chatDetail.setStrUserMsg(grpCompObject.getProperty("pear_msg").toString());
					}
					if(grpCompObject.toString().contains("reg_date")){
						chatDetail.setStrUserDate(grpCompObject.getProperty("reg_date").toString());
					}

					chatUserDetailses.add(chatDetail);
				}

			}else{

			}
			return chatUserDetailses;

		}

		if(methodname.equalsIgnoreCase(ApplicationConstants.MSU_SendMsg)){
			String response= "";
			response = responce.getProperty("MSU_SendMsgResult").toString();
			//ApplicationUtil.getInstance().setNewRegistrationOutput(response);
			return response;
		}

		if(methodname.equalsIgnoreCase(ApplicationConstants.MSU_GetGroupConversation)){

			ArrayList<MultiUsrMsgChat> chatUserDetailses = new  ArrayList<MultiUsrMsgChat>();
			//need to change
			SoapObject soapObject=  (SoapObject) responce.getProperty("MSU_GetGroupConversationResult");
			SoapObject soapObject1 =(SoapObject)soapObject.getProperty("diffgram");

			if(soapObject1.toString().contains("DocumentElement")){

				SoapObject innerObject = (SoapObject)((SoapObject)((SoapObject)soapObject.getProperty("diffgram")).getProperty("DocumentElement"));

				System.out.println("..................len = "+ innerObject.getPropertyCount());

				for(int i = 0; i<innerObject.getPropertyCount();i++){

					MultiUsrMsgChat chatDetail= new MultiUsrMsgChat();
					SoapObject grpCompObject = (SoapObject)innerObject.getProperty(i);

					if(grpCompObject.toString().contains("login_id")){
						chatDetail.setUserId(grpCompObject.getProperty("login_id").toString());
					}
					if(grpCompObject.toString().contains("userName")){
						chatDetail.setUserName(grpCompObject.getProperty("userName").toString());
					}
					if(grpCompObject.toString().contains("grp_msg")){
						chatDetail.setUserMsg(grpCompObject.getProperty("grp_msg").toString());
					}
					if(grpCompObject.toString().contains("reg_date")){
						chatDetail.setUserMsgDate(grpCompObject.getProperty("reg_date").toString());
					}

					chatUserDetailses.add(chatDetail);
				}

			}else{

			}
			return chatUserDetailses;

		}



		if(methodname.equalsIgnoreCase(ApplicationConstants.MSU_GetGroupMembers)){

			ArrayList<ChatUserDetails> chatUserDetailses = new  ArrayList<ChatUserDetails>();
			//need to change
			SoapObject soapObject=  (SoapObject) responce.getProperty("MSU_GetGroupMembersResult");
			SoapObject soapObject1 =(SoapObject)soapObject.getProperty("diffgram");

			if(soapObject1.toString().contains("DocumentElement")){

				SoapObject innerObject = (SoapObject)((SoapObject)((SoapObject)soapObject.getProperty("diffgram")).getProperty("DocumentElement"));

				System.out.println("..................len = "+ innerObject.getPropertyCount());

				for(int i = 0; i<innerObject.getPropertyCount();i++){

					ChatUserDetails chatDetail= new ChatUserDetails();
					SoapObject grpCompObject = (SoapObject)innerObject.getProperty(i);

					if(grpCompObject.toString().contains("login_id")){
						chatDetail.setStrUsrId(grpCompObject.getProperty("login_id").toString());
					}
					if(grpCompObject.toString().contains("userName")){
						chatDetail.setStrUsrName(grpCompObject.getProperty("userName").toString());
					}
					if(grpCompObject.toString().contains("group_id")){
						//chatDetail.set(grpCompObject.getProperty("group_id").toString());
					}
					if(grpCompObject.toString().contains("grp_name")){
						//chatDetail.set(grpCompObject.getProperty("grp_name").toString());
					}
					if(grpCompObject.toString().contains("grp_wrk_pref_hobby")){
						//chatDetail.set(grpCompObject.getProperty("grp_wrk_pref_hobby").toString());
					}
					if(grpCompObject.toString().contains("grp_mbr_desig")){
						chatDetail.setStrUsrDesg(grpCompObject.getProperty("grp_mbr_desig").toString());
					}
					if(grpCompObject.toString().contains("reg_date")){
						//chatDetail.set(grpCompObject.getProperty("reg_date").toString());
					}

					chatUserDetailses.add(chatDetail);
				}

			}else{

			}
			return chatUserDetailses;

		}


		if(methodname.equalsIgnoreCase(ApplicationConstants.MSU_CENTERS)){

			ArrayList<NSDC_Center_Bean> nsdcIds = new  ArrayList<NSDC_Center_Bean>();


			SoapObject soapObject=  (SoapObject) responce.getProperty("MSU_GetNSDCListResult");
			SoapObject soapObject1 =(SoapObject)soapObject.getProperty("diffgram");

			if(soapObject1.toString().contains("DocumentElement")){

				SoapObject innerObject = (SoapObject)((SoapObject)((SoapObject)soapObject.getProperty("diffgram")).getProperty("DocumentElement"));
				//SoapObject innerInObject = (SoapObject)innerObject.getProperty("iMsgLoginCredentials");

				System.out.println("..................len = "+ innerObject.getPropertyCount());

				for(int i = 0; i<innerObject.getPropertyCount();i++){

					NSDC_Center_Bean nsdcId= new NSDC_Center_Bean();
					SoapObject fcmIdObject = (SoapObject)innerObject.getProperty(i);

					if(fcmIdObject.toString().contains("sr_no_id")){
						nsdcId.setSr_no_id(fcmIdObject.getProperty("sr_no_id").toString());
					}
					if(fcmIdObject.toString().contains("nsdc_name")){
						nsdcId.setNsdc_name(fcmIdObject.getProperty("nsdc_name").toString());
					}
					if(fcmIdObject.toString().contains("nsdc_contact_no")){
						nsdcId.setNsdc_contact_no(fcmIdObject.getProperty("nsdc_contact_no").toString());
					}
					if(fcmIdObject.toString().contains("nsdc_lat")){
						nsdcId.setNsdc_lat(fcmIdObject.getProperty("nsdc_lat").toString());
					}
					if(fcmIdObject.toString().contains("nsdc_long")){
						nsdcId.setNsdc_long(fcmIdObject.getProperty("nsdc_long").toString());
					}
					if(fcmIdObject.toString().contains("nsdc_address")){
						nsdcId.setNsdc_address(fcmIdObject.getProperty("nsdc_address").toString());
					}
					if(fcmIdObject.toString().contains("nsdc_pin")){
						nsdcId.setNsdc_pin(fcmIdObject.getProperty("nsdc_pin").toString());
					}
					if(fcmIdObject.toString().contains("nsdc_other")){
						nsdcId.setNsdc_other(fcmIdObject.getProperty("nsdc_other").toString());
					}


					nsdcIds.add(nsdcId);
					//System.out.println(".............OPERATIONAL_COMP_NO = "+msgObject.getProperty("OPERATIONAL_COMP_NO").toString());
				}
			}
			else{

			}
			return nsdcIds;

		}



		if(methodname.equalsIgnoreCase(ApplicationConstants.MEMBER_LOCATION)){

			ArrayList<Member_Location_Bean> memberLocaIds = new  ArrayList<Member_Location_Bean>();


			SoapObject soapObject=  (SoapObject) responce.getProperty("MSU_GetNearByUsersResult");
			SoapObject soapObject1 =(SoapObject)soapObject.getProperty("diffgram");

			if(soapObject1.toString().contains("DocumentElement")){

				SoapObject innerObject = (SoapObject)((SoapObject)((SoapObject)soapObject.getProperty("diffgram")).getProperty("DocumentElement"));
				//SoapObject innerInObject = (SoapObject)innerObject.getProperty("iMsgLoginCredentials");

				System.out.println("..................len = "+ innerObject.getPropertyCount());

				for(int i = 0; i<innerObject.getPropertyCount();i++){

					Member_Location_Bean memberLocId= new Member_Location_Bean();
					SoapObject fcmIdObject = (SoapObject)innerObject.getProperty(i);


					if(fcmIdObject.toString().contains("MobileNo")){
						memberLocId.setMobileNo(fcmIdObject.getProperty("MobileNo").toString());
					}
					if(fcmIdObject.toString().contains("Name")){
						memberLocId.setName(fcmIdObject.getProperty("Name").toString());
					}
					if(fcmIdObject.toString().contains("Address")){
						memberLocId.setAddress(fcmIdObject.getProperty("Address").toString());
					}
					if(fcmIdObject.toString().contains("MemberStatus")){
						memberLocId.setMemberStatus(fcmIdObject.getProperty("MemberStatus").toString());
					}
					if(fcmIdObject.toString().contains("Distance")){
						memberLocId.setDistance(fcmIdObject.getProperty("Distance").toString());
					}
					if(fcmIdObject.toString().contains("Lat")){
						memberLocId.setLat(fcmIdObject.getProperty("Lat").toString());
					}
					if(fcmIdObject.toString().contains("Long")){
						memberLocId.setLongg(fcmIdObject.getProperty("Long").toString());
					}


					memberLocaIds.add(memberLocId);
					//System.out.println(".............OPERATIONAL_COMP_NO = "+msgObject.getProperty("OPERATIONAL_COMP_NO").toString());
				}
			}
			else{

			}
			return memberLocaIds;

		}

		//MSU

		if(methodname.equalsIgnoreCase(ApplicationConstants.MSU_REGITRATION)){
			String response= "";
			response = responce.getProperty("MSU_RegistrationResult").toString();
			//ApplicationUtil.getInstance().setNewRegistrationOutput(response);
			return response;
		}


		return methodname;
	}



	/*Call Web Service Start*/
	public Object callWebService(Object request , String methodname, String methodType) throws ApplicationException{

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		envelope.dotNet =true;
		System.out.println("inside call "+ methodname);
		envelope.setOutputSoapObject(request);
		setMapping( envelope,  methodname);
		HttpTransportSE httpTransportSE;
		try {

			httpTransportSE = new HttpTransportSE(ApplicationConstants.WEB_SERVICE_URL);
			System.out.println("..............connected with "+ ApplicationConstants.WEB_SERVICE_URL);

			AppLog appLog = new AppLog();

			appLog.setStrIMEI("PVAPP");
			appLog.setStrActionPerform(methodname);
			appLog.setStrActionType(methodType);

			SoapSerializationEnvelope envelope1 = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope1.headerOut = new Element[1];
			envelope1.headerOut[0] = buildAuthHeader();
			envelope1.dotNet =true;
			System.out.println("inside call "+ methodname);
			envelope1.setOutputSoapObject(appLog);
			setMapping( envelope1,  ApplicationConstants.APP_LOG);

			httpTransportSE.call(ApplicationConstants.TARGE_NAME_SPACE+ApplicationConstants.APP_LOG, envelope1);
			if(envelope1.bodyIn instanceof SoapObject){
				SoapObject result1 = (SoapObject) envelope1.bodyIn;
				System.out.println("................Applog....result = "+ result1);
			}
			envelope.headerOut = new Element[1];
			envelope.headerOut[0] = buildAuthHeader();
			httpTransportSE.call(ApplicationConstants.TARGE_NAME_SPACE+methodname, envelope);
			if(envelope.bodyIn instanceof SoapObject){
				SoapObject result = (SoapObject) envelope.bodyIn;
				System.out.println("......response = "+result.toString());
				if(result==null){
					System.out.println("................call webservice 1");
					ApplicationException exception = new ApplicationException();
					exception.setErrorMsg(ApplicationConstants.ERROR_MSG_SERVER_DOWN);
					throw exception;
				}else{
					System.out.println("................call webservice 3");
					return retreiveResponse(result, methodname);
				}
			}else{

				System.out.println("................call webservice 2");
				ApplicationException exception = new ApplicationException();
				exception.setErrorMsg(ApplicationConstants.ERROR_MSG_SERVER_DOWN);
				throw exception;
			}

		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
/*Call Web Service End*/
	


	public void setMapping(SoapSerializationEnvelope envelope, String methodName){

		if(methodName.equalsIgnoreCase(ApplicationConstants.GET_VALIDATE_USER)){
			envelope.addMapping(ApplicationConstants.TARGE_NAME_SPACE, methodName, ValidateUserProxie.class);
		}
		if(methodName.equalsIgnoreCase(ApplicationConstants.GET_CHANGE_PASS)){
			envelope.addMapping(ApplicationConstants.TARGE_NAME_SPACE, methodName, ChangePasswordProxie.class);
		}


		if(methodName.equalsIgnoreCase(ApplicationConstants.MSU_CENTERS)){
			envelope.addMapping(ApplicationConstants.TARGE_NAME_SPACE, methodName, NSDC_Centers_Proxy.class);
		}

		if(methodName.equalsIgnoreCase(ApplicationConstants.MEMBER_LOCATION)){
			envelope.addMapping(ApplicationConstants.TARGE_NAME_SPACE, methodName, members_location_proxy.class);
		}


		if(methodName.equalsIgnoreCase(ApplicationConstants.MSU_GetGroupMembers)){
			envelope.addMapping(ApplicationConstants.TARGE_NAME_SPACE, methodName, ChatUserDetailsProxy.class);
		}

		if(methodName.equalsIgnoreCase(ApplicationConstants.MSU_GetGroupConversation)){
			envelope.addMapping(ApplicationConstants.TARGE_NAME_SPACE, methodName, ChatUserDetailsProxy.class);
		}

		if(methodName.equalsIgnoreCase(ApplicationConstants.MSU_GetPearConversation)){
			envelope.addMapping(ApplicationConstants.TARGE_NAME_SPACE, methodName, MsgChatUserList.class);
		}

		if(methodName.equalsIgnoreCase(ApplicationConstants.MSU_SendMsg)){
			envelope.addMapping(ApplicationConstants.TARGE_NAME_SPACE, methodName, ChatReplyProxy.class);
		}

		//MSU
		if(methodName.equalsIgnoreCase(ApplicationConstants.MSU_REGITRATION)){
			envelope.addMapping(ApplicationConstants.TARGE_NAME_SPACE, methodName, UserRegistrationProxy.class);
		}


		
	}


	private Element buildAuthHeader() {
	    Element h = new Element().createElement(ApplicationConstants.TARGE_NAME_SPACE, "UserCredentials");
	    Element username = new Element().createElement(ApplicationConstants.TARGE_NAME_SPACE, "userName");
	    username.addChild(Node.TEXT, "MSUAPP");
	    h.addChild(Node.ELEMENT, username);
	    Element pass = new Element().createElement(ApplicationConstants.TARGE_NAME_SPACE, "password");
	    pass.addChild(Node.TEXT, "m$ug00gle@pp");
	    h.addChild(Node.ELEMENT, pass);

	    return h;
	}


	@Override
	public User validateUser(ValidateUserProxie validateUserProxie, Context context) throws ApplicationException {
		// TODO Auto-generated method stub
		this.context = context; 
		Object obj = callWebService(validateUserProxie, ApplicationConstants.GET_VALIDATE_USER, ApplicationConstants.MethodType1);

		System.out.println("GCM ID..."+validateUserProxie.get_sGcmId());
		System.out.println("EMP ID..."+validateUserProxie.get_sUser());
		if(obj instanceof User){
			return (User) obj;
		}else
		return new User();
	}


	@Override
	public ChangePassBean ChangePassword(ChangePasswordProxie changePasswordProxie, Context context)throws ApplicationException{
		// TODO Auto-generated method stub
		this.context = context;
		Object obj = callWebService(changePasswordProxie, ApplicationConstants.GET_CHANGE_PASS, ApplicationConstants.MethodType3);
		if(obj instanceof User){
			return (ChangePassBean) obj;
		}else
			return new ChangePassBean();
	}


	@Override
	public ArrayList<NSDC_Center_Bean> getNSDCLocation(NSDC_Centers_Proxy nsdc_centers_proxy, Context context)throws ApplicationException {
		// TODO Auto-generated method stub
		this.context = context;
		Object obj = callWebService(nsdc_centers_proxy, ApplicationConstants.MSU_CENTERS, ApplicationConstants.MethodType1);
		if(obj instanceof ArrayList<?>){
			return (ArrayList<NSDC_Center_Bean>)obj;
		}else{
			return new ArrayList<NSDC_Center_Bean>();
		}
	}



	@Override
	public ArrayList<Member_Location_Bean> getMemberLocation(members_location_proxy memberLocationProxy, Context context)throws ApplicationException{
		// TODO Auto-generated method stub
		this.context = context;
		Object obj = callWebService(memberLocationProxy, ApplicationConstants.MEMBER_LOCATION, ApplicationConstants.MethodType1);
		if(obj instanceof ArrayList<?>){
			return (ArrayList<Member_Location_Bean>)obj;
		}else{
			return new ArrayList<Member_Location_Bean>();
		}
	}




	@Override
	public ArrayList<ChatUserDetails> getAllUserDetails(ChatUserDetailsProxy getUserDetailsProxie, Context context) throws ApplicationException {
		this.context = context;
		Object obj = callWebService(getUserDetailsProxie, ApplicationConstants.MSU_GetGroupMembers, ApplicationConstants.MethodType3);
		if(obj instanceof ArrayList<?>){
			return (ArrayList<ChatUserDetails>)obj;
		}else{
			return new ArrayList<ChatUserDetails>();
		}
	}

	@Override
	public ArrayList<SinglUsrChatList> getAllMsgChatDetails(MsgChatUserList getMsgChatListProxie, Context context) throws ApplicationException {
		this.context = context;
		Object obj = callWebService(getMsgChatListProxie, ApplicationConstants.MSU_GetPearConversation, ApplicationConstants.MethodType3);
		if(obj instanceof ArrayList<?>){
			return (ArrayList<SinglUsrChatList>)obj;
		}else{
			return new ArrayList<SinglUsrChatList>();
		}
	}

	@Override
	public ArrayList<MultiUsrMsgChat> getMultiUserConversation(ChatUserDetailsProxy getMultiUsrMsgList, Context context) throws ApplicationException {
		this.context = context;
		Object obj = callWebService(getMultiUsrMsgList, ApplicationConstants.MSU_GetGroupConversation, ApplicationConstants.MethodType3);
		if(obj instanceof ArrayList<?>){
			return (ArrayList<MultiUsrMsgChat>)obj;
		}else{
			return new ArrayList<MultiUsrMsgChat>();
		}
	}

	@Override
	public String chatReply(ChatReplyProxy chatReplyProxy, Context context) throws ApplicationException {
		this.context = context;
		return (String)callWebService(chatReplyProxy, ApplicationConstants.MSU_SendMsg, ApplicationConstants.MethodType1);
	}


	// MSU

	@Override
	public String registerUser(UserRegistrationProxy userRegistration, Context context) throws ApplicationException {
		// TODO Auto-generated method stub
		this.context = context;
		return (String)callWebService(userRegistration, ApplicationConstants.MSU_REGITRATION, ApplicationConstants.MethodType1);
	}

	
}
