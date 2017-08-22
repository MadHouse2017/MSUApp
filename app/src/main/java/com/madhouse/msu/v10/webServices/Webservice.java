package com.madhouse.msu.v10.webServices;

import android.content.Context;

import com.madhouse.msu.v10.bean.ChangePassBean;
import com.madhouse.msu.v10.bean.ChatUserDetails;
import com.madhouse.msu.v10.bean.Member_Location_Bean;
import com.madhouse.msu.v10.bean.MultiUsrMsgChat;
import com.madhouse.msu.v10.bean.NSDC_Center_Bean;
import com.madhouse.msu.v10.bean.SinglUsrChatList;
import com.madhouse.msu.v10.bean.User;
import com.madhouse.msu.v10.exceptions.ApplicationException;
import com.madhouse.msu.v10.proxies.ChangePasswordProxie;
import com.madhouse.msu.v10.proxies.ChatReplyProxy;
import com.madhouse.msu.v10.proxies.ChatUserDetailsProxy;
import com.madhouse.msu.v10.proxies.MsgChatUserList;
import com.madhouse.msu.v10.proxies.NSDC_Centers_Proxy;
import com.madhouse.msu.v10.proxies.UserRegistrationProxy;
import com.madhouse.msu.v10.proxies.ValidateUserProxie;
import com.madhouse.msu.v10.proxies.members_location_proxy;

import java.util.ArrayList;


public interface Webservice {
	//2208
	//public boolean registerUser(UserRegistrationProxy userRegistration, Context context) throws ApplicationException;
	public User validateUser(ValidateUserProxie validateUserProxie, Context context)throws ApplicationException;
	public ChangePassBean ChangePassword(ChangePasswordProxie changePasswordProxie, Context context)throws ApplicationException;

	//msu
	public String registerUser(UserRegistrationProxy userRegistration, Context context) throws ApplicationException;

	public ArrayList<NSDC_Center_Bean> getNSDCLocation(NSDC_Centers_Proxy nsdc_centers_proxy, Context context)throws ApplicationException;

	public ArrayList<Member_Location_Bean> getMemberLocation(members_location_proxy memberLocationProxy, Context context)throws ApplicationException;


	//Rajveer
	//MSU Webservice
	public ArrayList<ChatUserDetails> getAllUserDetails(ChatUserDetailsProxy getUserDetailsProxie, Context context)throws ApplicationException;
	public ArrayList<SinglUsrChatList> getAllMsgChatDetails(MsgChatUserList getMsgChatListProxie, Context context)throws ApplicationException;
	public ArrayList<MultiUsrMsgChat> getMultiUserConversation(ChatUserDetailsProxy getMultiUsrMsgList, Context context) throws  ApplicationException;
	public String chatReply(ChatReplyProxy chatReplyProxy, Context context) throws ApplicationException;


}
