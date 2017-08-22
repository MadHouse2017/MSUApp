package com.madhouse.msu.v10.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.itextpdf.text.ExceptionConverter;
import com.madhouse.msu.v10.MSU.LoginScreen;
import com.madhouse.msu.v10.MSU.RegistrationDetails;
import com.madhouse.msu.v10.R;
import com.madhouse.msu.v10.applicationUtils.ApplicationConstants;
import com.madhouse.msu.v10.applicationUtils.ApplicationUtil;
import com.madhouse.msu.v10.applicationUtils.UserPreferences;
import com.madhouse.msu.v10.exceptions.ApplicationException;
import com.madhouse.msu.v10.proxies.UserBhamashahBean;
import com.madhouse.msu.v10.proxies.UserRegistrationProxy;


public class Questionaries extends Fragment{
	boolean sent = false;
	String res;
	Button musicButton;
	RadioGroup radioGrp1, radioGrp2, radioGrp3, radioGrp4, radioGrp5;
	RadioButton radioBtn1, radioBtn2, radioBtn3, radioBtn4;
	EditText feed1, feed2;
	Button submit;

	ProgressDialog progressDialog ;
	private Toolbar toolbar;
	private String ans1="1", ans2="1", ans3="1", ans4="1", ans5="1";
	private UserPreferences preferences;
	private View view;
	private String registrationResponse;
	private int marks = 0;



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



		view =  inflater.inflate(R.layout.questionaries, container, false);


		
	   progressDialog = new ProgressDialog(getActivity());
		
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		toolbar = (Toolbar) view.findViewById(R.id.custom_toolbar);
		TextView ctoolbar_title = (TextView) toolbar.findViewById(R.id.ctoolbar_title);
		ctoolbar_title.setText(R.string.msu_questionaries_heading);

		preferences = UserPreferences.getInstance(getActivity());
		
		feed1 = (EditText)view.findViewById(R.id.feed1);
		feed2= (EditText)view.findViewById(R.id.feed2);
		
		radioGrp1 = (RadioGroup)view.findViewById(R.id.radioGroup1);
		radioGrp2 = (RadioGroup)view.findViewById(R.id.radioGroup2);
		radioGrp3 = (RadioGroup)view.findViewById(R.id.radioGroup3);
		radioGrp4 = (RadioGroup)view.findViewById(R.id.radioGroup4);
		radioGrp5 = (RadioGroup)view.findViewById(R.id.radioGroup5);
		
		Button finalSubmit = (Button)view.findViewById(R.id.finalsubmit);


		radioGrp1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// find which radio button is selected
				if(checkedId == R.id.radioGroup1BtnA) {
					ans1 = "1";
				}
				else if(checkedId == R.id.radioGroup1BtnB) {
					ans1 = "2";
				}
				else {
					ans1 = "3";
				}
			}
		});

		radioGrp2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// find which radio button is selected
				if(checkedId == R.id.radioGroup2BtnA) {
					ans2 = "1";
				}
				else if(checkedId == R.id.radioGroup2BtnB) {
					ans2 = "2";
				}
				else {
					ans2 = "3";
				}
			}
		});


		radioGrp3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// find which radio button is selected
				if(checkedId == R.id.radioGroup3BtnA) {
					ans3 = "1";
				}
				else if(checkedId == R.id.radioGroup3BtnB) {
					ans3 = "2";
				}
				else {
					ans3 = "3";
				}
			}
		});


		radioGrp4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// find which radio button is selected
				if(checkedId == R.id.radioGroup4BtnA) {
					ans4 = "1";
				}
				else if(checkedId == R.id.radioGroup4BtnB) {
					ans4 = "2";
				}
				else {
					ans4 = "3";
				}
			}
		});

		radioGrp5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// find which radio button is selected
				if(checkedId == R.id.radioGroup5BtnA) {
					ans5 = "1";
				}
				else if(checkedId == R.id.radioGroup5BtnB) {
					ans5 = "2";
				}
				else {
					ans5 = "3";
				}
			}
		});





		finalSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
		public void onClick(View arg0) {
				// TODO Auto-generated method stub
		//if(feed1.getText().toString().equals("")==false && feed2.getText().toString().equals("")==false){
			
			
			if(ApplicationUtil.getInstance().checkInternetConnection(getActivity())==false){
				ApplicationUtil.getInstance().getAlertDialog(ApplicationConstants.ERROR_MSG_INTERNET_DOWN, getActivity()).show();
			}
			
			else {

				preferences.setANS1(ans1);
				preferences.setANS2(ans2);
				preferences.setANS3(ans3);
				preferences.setANS4(ans4);
				preferences.setANS5(ans5);


				if(ans1.equalsIgnoreCase("2")){
					marks = marks+2;
				}
				if(ans2.equalsIgnoreCase("1")){
					marks = marks+2;
				}
				if(ans3.equalsIgnoreCase("3")){
					marks = marks+2;
				}
				if (ans4.equalsIgnoreCase("3")){
					marks = marks+2;
				}
				if (ans5.equalsIgnoreCase("2")){
					marks = marks+2;
				}



//				try {
//					final Dialog dialog = new Dialog(getActivity(), R.style.MyCustomPrompt);
//					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//					//dialog.setCancelable(false);
//					//dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//					dialog.setCanceledOnTouchOutside(false);
//					dialog.setContentView(R.layout.promt_message_success);
//
//					Button closeDialog = (Button) dialog.findViewById(R.id.closePrompt);
//					TextView messageTitle = (TextView) dialog.findViewById(R.id.alert_title);
//					TextView messageDate = (TextView) dialog.findViewById(R.id.alert_date);
//					TextView messageDescription = (TextView) dialog.findViewById(R.id.message_description);
//					TextView usernameshow = (TextView) dialog.findViewById(R.id.username_show);
//					TextView passwordshow = (TextView) dialog.findViewById(R.id.password_show);
//
//					messageTitle.setText(getString(R.string.success_title));
//					messageDate.setText("");
//					messageDescription.setText(getString(R.string.success_desc));
//					usernameshow.setText("Your Login ID - "+preferences.getUserid());
//					passwordshow.setText("Your Password - "+preferences.getPassword());
//
//					closeDialog.setOnClickListener(new View.OnClickListener() {
//						@SuppressLint("NewApi")
//						@Override
//						public void onClick(View v) {
//							dialog.dismiss();
//
//							Intent mainIntent = new Intent(getActivity(), LoginScreen.class);
//							mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//							startActivity(mainIntent);
//							if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//								getActivity().finishAffinity();
//							}
//							//Questionaries.this.finish();
//
//						}
//					});
//					// messageDescription.setText(message.getGenre().toString());
//					//   messageTitle.setText(message.getTitle().toString());
//					dialog.show();
//				}
//				catch (Exception er){
//					er.printStackTrace();
//				}



					final ProgressDialog progressDialog = new ProgressDialog(getActivity());
					progressDialog.setMessage("Registering..");
					progressDialog.show();

					final Handler handler = new  Handler(){
						@SuppressWarnings("deprecation")
						@Override
						public void handleMessage(Message msg) {
							// TODO Auto-generated method stub
							progressDialog.dismiss();
							if(msg.what == 0){

								try {

									preferences.setIS_REGISTERED("YES");
									marks = 0;


									final Dialog dialog = new Dialog(getActivity(), R.style.MyCustomPrompt);
									dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
									//dialog.setCancelable(false);
									//dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
									dialog.setCanceledOnTouchOutside(false);
									dialog.setContentView(R.layout.prompt_otp_verification);

									Button closeDialog = (Button) dialog.findViewById(R.id.closePrompt);
									TextView messageTitle = (TextView) dialog.findViewById(R.id.alert_title);
									TextView messageDate = (TextView) dialog.findViewById(R.id.alert_date);
									TextView messageDescription = (TextView) dialog.findViewById(R.id.message_description);
									EditText otpCode = (EditText) dialog.findViewById(R.id.username_show);
									TextView passwordshow = (TextView) dialog.findViewById(R.id.password_show);

									messageTitle.setText(getString(R.string.otp_title));
									messageDate.setText("");
									messageDescription.setText(getString(R.string.otp_desc));
									passwordshow.setText(getString(R.string.otp_resend));

									closeDialog.setOnClickListener(new View.OnClickListener() {
										@SuppressLint("NewApi")
										@Override
										public void onClick(View v) {
											dialog.dismiss();

											callSuccessDialog();

										}
									});
									// messageDescription.setText(message.getGenre().toString());
									//   messageTitle.setText(message.getTitle().toString());
									dialog.show();



									}
									catch (Exception er){
										er.printStackTrace();
									}

							}


							else if(msg.what == 2){
								AlertDialog alertDialog = ApplicationUtil.getInstance().getAlertDialog("Already Registered", getActivity());
								if(!getActivity().isFinishing())
								{
									alertDialog.show();
								}

							}


							else{

									AlertDialog alertDialog = ApplicationUtil.getInstance().getAlertDialog("Some error has occurred", getActivity());
									if(!getActivity().isFinishing())
									{
										alertDialog.show();
									}


								}

							super.handleMessage(msg);
						}
					};

					Thread thread = new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub

							UserRegistrationProxy userRegistration = new UserRegistrationProxy();



							UserBhamashahBean ubb = RegistrationDetails.userBhamashah;


						//	PVFirebaseInstanceIDService getPVFirebaseInstanceIDService = new PVFirebaseInstanceIDService();
						//	System.out.println("......FCM ID...."+getPVFirebaseInstanceIDService.sendRegistrationToServer());

							try {
								userRegistration.setBHAMASHAH_ID(preferences.getGOV_ID());
								userRegistration.setAADHAR_ID(ubb.getAADHAR_ID());
								userRegistration.setDOB(preferences.getDOB());
								userRegistration.setEmailId(preferences.getEmail());
								userRegistration.setADDRESS(preferences.getADDRESS());


								userRegistration.setFAMILYIDNO(ubb.getFAMILYIDNO());
								userRegistration.setFATHER_NAME_ENG(ubb.getFATHER_NAME_ENG());
								userRegistration.setFATHER_NAME_HND(ubb.getFATHER_NAME_HND());
								userRegistration.setGENDER("FEMALE");
								userRegistration.setIdNumber(preferences.getGOV_ID());
								userRegistration.setGcmId("");
								userRegistration.setIdPhoto(preferences.getGOV_ID_PIC());
								userRegistration.setIdType(preferences.getID_TYPE());
								userRegistration.setM_ID(ubb.getM_ID());
								userRegistration.setUserName(preferences.getNAME());
								userRegistration.setSPOUCE_NAME_HND(ubb.getSPOUCE_NAME_HND());
								userRegistration.setSPOUCE_NAME_ENG(ubb.getSPOUCE_NAME_ENG());
								userRegistration.setSecMobileNo(preferences.getSEC_MOB());
								userRegistration.setPrimMobileNo(preferences.getPRIMARY_MOB());
								userRegistration.setPHOTO(preferences.getPROFILE_PIC());
								userRegistration.setPassword(preferences.getPassword());
								userRegistration.setOther(marks + "");
								userRegistration.setMaritalStatus(preferences.getMARITAL_STATUS());
								userRegistration.setNAME_ENG(preferences.getNAME());
								userRegistration.setNAME_HND(preferences.getNAME());
								userRegistration.setMOTHER_NAME_ENG(ubb.getMOTHER_NAME_ENG());
								userRegistration.setMOTHER_NAME_HND(ubb.getMOTHER_NAME_HND());

								userRegistration.setUSER_ROLE(preferences.getUser_role());
								userRegistration.setPREFERENCE_HOBBY(preferences.getPREFERENCE_HOBBY());
								userRegistration.setHOBBY_PROFICIENCY(preferences.getPREFERENCE_PROFICIENCY());
								userRegistration.setANS1(preferences.getANS1());
								userRegistration.setANS2(preferences.getANS2());
								userRegistration.setANS3(preferences.getANS3());
								userRegistration.setANS4(preferences.getANS4());
								userRegistration.setANS5(preferences.getANS5());
								userRegistration.setIS_REGISTERED(preferences.getIS_REGISTERED());

							}

							catch (Exception er){
								er.getMessage();
							}


							System.out.println("Checkbox is checked and call to register here !");

							try {
								registrationResponse = ApplicationUtil.getInstance().getWebservice().registerUser(userRegistration, getActivity());
								if(registrationResponse != ""){


									if(registrationResponse.equalsIgnoreCase("Not Register")){
										handler.sendEmptyMessage(1);
									}
									else if(registrationResponse.equalsIgnoreCase("Already Register")){
										handler.sendEmptyMessage(2);
									}

									else if(registrationResponse.equalsIgnoreCase("Error Registering.")){
										handler.sendEmptyMessage(1);
									}
									else{
										handler.sendEmptyMessage(0);
									}

								}
								else{
									handler.sendEmptyMessage(1);
								}
							} catch (ApplicationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								handler.sendEmptyMessage(1);
							}
						}
					});
					thread.start();
				}



			}
		});


		return view;
		
	}


	private void callSuccessDialog(){


		final Dialog dialog = new Dialog(getActivity(), R.style.MyCustomPrompt);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//dialog.setCancelable(false);
		//dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog.setCanceledOnTouchOutside(false);
		dialog.setContentView(R.layout.promt_message_success);

		Button closeDialog = (Button) dialog.findViewById(R.id.closePrompt);
		TextView messageTitle = (TextView) dialog.findViewById(R.id.alert_title);
		TextView messageDate = (TextView) dialog.findViewById(R.id.alert_date);
		TextView messageDescription = (TextView) dialog.findViewById(R.id.message_description);
		TextView usernameshow = (TextView) dialog.findViewById(R.id.username_show);
		TextView passwordshow = (TextView) dialog.findViewById(R.id.password_show);

		messageTitle.setText(getString(R.string.success_title));
		messageDate.setText("");
		messageDescription.setText(getString(R.string.success_desc));
		usernameshow.setText("Your Login ID - "+preferences.getUserid());
		passwordshow.setText("Your Password - "+preferences.getPassword());

		closeDialog.setOnClickListener(new View.OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View v) {
				dialog.dismiss();

				Intent mainIntent = new Intent(getActivity(), LoginScreen.class);
				mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(mainIntent);
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					getActivity().finishAffinity();
				}
				//Questionaries.this.finish();

			}
		});
		// messageDescription.setText(message.getGenre().toString());
		//   messageTitle.setText(message.getTitle().toString());
		dialog.show();


	}
	

}
