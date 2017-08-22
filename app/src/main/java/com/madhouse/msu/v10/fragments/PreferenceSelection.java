package com.madhouse.msu.v10.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.madhouse.msu.v10.R;
import com.madhouse.msu.v10.adapter.SpinnerAdapterHelpdesk;
import com.madhouse.msu.v10.applicationUtils.ApplicationUtil;
import com.madhouse.msu.v10.applicationUtils.UserPreferences;


/**
 * Created by Krishna.
 */
public class PreferenceSelection extends Fragment {


    private View rootView ;
    public ProgressBar webservicePG;
    public ProgressBar spinnerProgress;
    private Button next;


    private EditText polenum;
    String meternum="METER";
    private TextView polenum_h;
    private Spinner proficiencySpinner;

    private ProgressDialog progressDialog;
    String[] div;
    String[] divcode;
    private String profLevelID, profLevelNAME;
    private String internet;
    private CheckBox checkBox1,checkBox2, checkBox3, checkBox4, checkBox5, checkBox6;
    private CheckBox checkBox7,checkBox8, checkBox9, checkBox10, checkBox11, checkBox12, checkBox13, checkBox14;
    private String check1="";
    private String imeino;
    private SharedPreferences permissionStatus;
    private Toolbar toolbar;
    private final static String TAG_FRAGMENT = "CHAT_FRAG";
    private UserPreferences preferences;
    private TextView divId, divName;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment

//        Fragment preferenceScreen = new PreferenceSelection();
//        getFragmentManager().beginTransaction()
//                //.setCustomAnimations(R.anim.anim_in, R.anim.anim_out)
//                .remove(preferenceScreen)
//                .commit();

        rootView =  inflater.inflate(R.layout.fragment_entrymodule, container, false);

        toolbar = (Toolbar) rootView.findViewById(R.id.custom_toolbar);
        TextView ctoolbar_title = (TextView) toolbar.findViewById(R.id.ctoolbar_title);
        ctoolbar_title.setText(R.string.pref_heading);
        preferences = UserPreferences.getInstance(getActivity());



        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        progressDialog = new ProgressDialog(getActivity());

        // initialize layout widgets
        init(rootView);

        return rootView;
    }

    // This snippet takes the simple approach of using the first returned Google account,
    // but you can pick any Google account on the device.

    private void init(View rootView){




        webservicePG = new ProgressBar(getActivity());

        //initiatorname = (EditText) rootView.findViewById(R.id.initiatorname);
        //division = (Spinner) rootView.findViewById(R.id.division);
        //vendor = (Spinner) rootView.findViewById(R.id.vendorname);
       // division = (Spinner) rootView.findViewById(R.id.division);

        proficiencySpinner = (Spinner)rootView.findViewById(R.id.proficiency_level);
        polenum_h = (TextView)rootView.findViewById(R.id.polenum_h);
        polenum = (EditText)rootView.findViewById(R.id.polenum);



        next = (Button) rootView.findViewById(R.id.next);

        checkBox1=(CheckBox)rootView.findViewById(R.id.checkBox1);
        checkBox2=(CheckBox)rootView.findViewById(R.id.checkBox2);
        checkBox3=(CheckBox)rootView.findViewById(R.id.checkBox3);
        checkBox4=(CheckBox)rootView.findViewById(R.id.checkBox4);
        checkBox5=(CheckBox)rootView.findViewById(R.id.checkBox5);
        checkBox6=(CheckBox)rootView.findViewById(R.id.checkBox6);

        checkBox7=(CheckBox)rootView.findViewById(R.id.checkBox7);
        checkBox8=(CheckBox)rootView.findViewById(R.id.checkBox8);
        checkBox9=(CheckBox)rootView.findViewById(R.id.checkBox9);
        checkBox10=(CheckBox)rootView.findViewById(R.id.checkBox10);
        checkBox11=(CheckBox)rootView.findViewById(R.id.checkBox11);
        checkBox12=(CheckBox)rootView.findViewById(R.id.checkBox12);

        checkBox13=(CheckBox)rootView.findViewById(R.id.checkBox13);
        checkBox14=(CheckBox)rootView.findViewById(R.id.checkBox14);




        proficiencySpinner.setAdapter(new SpinnerAdapterHelpdesk(getActivity(), R.layout.custom_spinner,  getResources().getStringArray(R.array.prof_level_array),
                getResources().getStringArray(R.array.prof_level_id_array)));

        proficiencySpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               // spinnerProgress = (ProgressBar) view.findViewById(R.id.spinnerProgress);
                //spinnerProgress.setVisibility(View.GONE);
                //if(position!=0) {
                   // LoadComplaintCenter taskCompGroup = new LoadComplaintCenter();
                    // Call execute
                try {
                    divId = (TextView) view.findViewById(R.id.sub_text_seen);
                   divName = (TextView) view.findViewById(R.id.text_main_seen);

                    profLevelNAME = divName.getText().toString();
                    profLevelID = divId.getText().toString();
                }catch (Exception er){
                    er.getMessage();
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                profLevelNAME = divName.getText().toString();
                profLevelID = divId.getText().toString();
            }
        });

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                   // count++;
                    //if(check1.equals("")) {
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);
                    checkBox5.setChecked(false);
                    checkBox6.setChecked(false);

                    checkBox7.setChecked(false);
                    checkBox8.setChecked(false);
                    checkBox9.setChecked(false);
                    checkBox10.setChecked(false);
                    checkBox11.setChecked(false);

                    checkBox12.setChecked(false);
                    checkBox13.setChecked(false);
                    checkBox14.setChecked(false);

                        check1 = checkBox1.getText().toString();


                   // }
                  /*  else if(check2.equals("")){
                        check2 = checkBox1.getText().toString();
                    }
                    else{
                        checkBox1.setChecked(false);
                        Snackbar.make(buttonView, "Select Two Option Only!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }*/
                }
                else{
                   check1 = "";
                }
            }
        });

        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    // count++;
                    //if(check1.equals("")) {
                    checkBox1.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);
                    checkBox5.setChecked(false);
                    checkBox6.setChecked(false);

                    checkBox7.setChecked(false);
                    checkBox8.setChecked(false);
                    checkBox9.setChecked(false);
                    checkBox10.setChecked(false);
                    checkBox11.setChecked(false);

                    checkBox12.setChecked(false);
                    checkBox13.setChecked(false);
                    checkBox14.setChecked(false);

                    check1 = checkBox2.getText().toString();

                }
                else{
                    check1 = "";
                }
            }
        });

        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    // count++;
                    //if(check1.equals("")) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox4.setChecked(false);
                    checkBox5.setChecked(false);
                    checkBox6.setChecked(false);

                    checkBox7.setChecked(false);
                    checkBox8.setChecked(false);
                    checkBox9.setChecked(false);
                    checkBox10.setChecked(false);
                    checkBox11.setChecked(false);

                    checkBox12.setChecked(false);
                    checkBox13.setChecked(false);
                    checkBox14.setChecked(false);

                    check1 = checkBox3.getText().toString();

                }
                else{
                    check1 = "";
                }
            }
        });

        checkBox4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    // count++;
                    //if(check1.equals("")) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox5.setChecked(false);
                    checkBox6.setChecked(false);

                    checkBox7.setChecked(false);
                    checkBox8.setChecked(false);
                    checkBox9.setChecked(false);
                    checkBox10.setChecked(false);
                    checkBox11.setChecked(false);

                    checkBox12.setChecked(false);
                    checkBox13.setChecked(false);
                    checkBox14.setChecked(false);

                    check1 = checkBox4.getText().toString();

                }
                else{
                    check1 = "";
                }
            }
        });

        checkBox5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    // count++;
                    //if(check1.equals("")) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox4.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox6.setChecked(false);

                    checkBox7.setChecked(false);
                    checkBox8.setChecked(false);
                    checkBox9.setChecked(false);
                    checkBox10.setChecked(false);
                    checkBox11.setChecked(false);

                    checkBox12.setChecked(false);
                    checkBox13.setChecked(false);
                    checkBox14.setChecked(false);

                    check1 = checkBox5.getText().toString();

                }
                else{
                    check1 = "";
                }
            }
        });

        checkBox6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    // count++;
                    //if(check1.equals("")) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox4.setChecked(false);
                    checkBox5.setChecked(false);
                    checkBox3.setChecked(false);

                    checkBox7.setChecked(false);
                    checkBox8.setChecked(false);
                    checkBox9.setChecked(false);
                    checkBox10.setChecked(false);
                    checkBox11.setChecked(false);

                    checkBox12.setChecked(false);
                    checkBox13.setChecked(false);
                    checkBox14.setChecked(false);

                    check1 = checkBox6.getText().toString();

                }
                else{
                    check1 = "";
                }
            }
        });


        checkBox7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    // count++;
                    //if(check1.equals("")) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox4.setChecked(false);
                    checkBox5.setChecked(false);
                    checkBox3.setChecked(false);

                    checkBox6.setChecked(false);
                    checkBox8.setChecked(false);
                    checkBox9.setChecked(false);
                    checkBox10.setChecked(false);
                    checkBox11.setChecked(false);

                    checkBox12.setChecked(false);
                    checkBox13.setChecked(false);
                    checkBox14.setChecked(false);

                    check1 = checkBox7.getText().toString();

                }
                else{
                    check1 = "";
                }
            }
        });

        checkBox8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    // count++;
                    //if(check1.equals("")) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox4.setChecked(false);
                    checkBox5.setChecked(false);
                    checkBox3.setChecked(false);

                    checkBox6.setChecked(false);
                    checkBox7.setChecked(false);
                    checkBox9.setChecked(false);
                    checkBox10.setChecked(false);
                    checkBox11.setChecked(false);

                    checkBox12.setChecked(false);
                    checkBox13.setChecked(false);
                    checkBox14.setChecked(false);

                    check1 = checkBox8.getText().toString();

                }
                else{
                    check1 = "";
                }
            }
        });

        checkBox9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    // count++;
                    //if(check1.equals("")) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox4.setChecked(false);
                    checkBox5.setChecked(false);
                    checkBox3.setChecked(false);

                    checkBox6.setChecked(false);
                    checkBox8.setChecked(false);
                    checkBox7.setChecked(false);
                    checkBox10.setChecked(false);
                    checkBox11.setChecked(false);

                    checkBox12.setChecked(false);
                    checkBox13.setChecked(false);
                    checkBox14.setChecked(false);

                    check1 = checkBox9.getText().toString();

                }
                else{
                    check1 = "";
                }
            }
        });

        checkBox10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    // count++;
                    //if(check1.equals("")) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox4.setChecked(false);
                    checkBox5.setChecked(false);
                    checkBox3.setChecked(false);

                    checkBox6.setChecked(false);
                    checkBox8.setChecked(false);
                    checkBox9.setChecked(false);
                    checkBox7.setChecked(false);
                    checkBox11.setChecked(false);

                    checkBox12.setChecked(false);
                    checkBox13.setChecked(false);
                    checkBox14.setChecked(false);

                    check1 = checkBox10.getText().toString();

                }
                else{
                    check1 = "";
                }
            }
        });

        checkBox11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    // count++;
                    //if(check1.equals("")) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox4.setChecked(false);
                    checkBox5.setChecked(false);
                    checkBox3.setChecked(false);

                    checkBox6.setChecked(false);
                    checkBox8.setChecked(false);
                    checkBox9.setChecked(false);
                    checkBox7.setChecked(false);
                    checkBox10.setChecked(false);

                    checkBox12.setChecked(false);
                    checkBox13.setChecked(false);
                    checkBox14.setChecked(false);

                    check1 = checkBox11.getText().toString();

                }
                else{
                    check1 = "";
                }
            }
        });

        checkBox12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    // count++;
                    //if(check1.equals("")) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox4.setChecked(false);
                    checkBox5.setChecked(false);
                    checkBox3.setChecked(false);

                    checkBox6.setChecked(false);
                    checkBox8.setChecked(false);
                    checkBox9.setChecked(false);
                    checkBox7.setChecked(false);
                    checkBox11.setChecked(false);

                    checkBox10.setChecked(false);
                    checkBox13.setChecked(false);
                    checkBox14.setChecked(false);

                    check1 = checkBox12.getText().toString();

                }
                else{
                    check1 = "";
                }
            }
        });

        checkBox13.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    // count++;
                    //if(check1.equals("")) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox4.setChecked(false);
                    checkBox5.setChecked(false);
                    checkBox3.setChecked(false);

                    checkBox6.setChecked(false);
                    checkBox8.setChecked(false);
                    checkBox9.setChecked(false);
                    checkBox7.setChecked(false);
                    checkBox11.setChecked(false);

                    checkBox12.setChecked(false);
                    checkBox10.setChecked(false);
                    checkBox14.setChecked(false);

                    check1 = checkBox13.getText().toString();

                }
                else{
                    check1 = "";
                }
            }
        });

        checkBox14.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    // count++;
                    //if(check1.equals("")) {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox4.setChecked(false);
                    checkBox5.setChecked(false);
                    checkBox3.setChecked(false);

                    checkBox6.setChecked(false);
                    checkBox8.setChecked(false);
                    checkBox9.setChecked(false);
                    checkBox7.setChecked(false);
                    checkBox11.setChecked(false);

                    checkBox12.setChecked(false);
                    checkBox13.setChecked(false);
                    checkBox10.setChecked(false);

                    check1 = checkBox14.getText().toString();

                }
                else{
                    check1 = "";
                }
            }
        });








        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                    if(proficiencySpinner.getSelectedItemPosition() !=0  && (!check1.equals(""))) {

                        preferences.setPREFERENCE_HOBBY(check1);
                        preferences.setPREFERENCE_PROFICIENCY(profLevelID);


                       // Intent intent = new Intent(getActivity(), Questionaries.class);
                      //  getActivity().startActivity(intent);

//                        Fragment vehicleDetailsScreen = new Questionaries();
//                        getFragmentManager().beginTransaction()
//                                //.setCustomAnimations(R.anim.anim_in, R.anim.anim_out)
//                                .replace(R.id.fragment_place,
//                                        vehicleDetailsScreen, "Questionaries").addToBackStack("Questionaries")
//                                .commit();

                        Fragment fragment = new Questionaries();

                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_place, fragment, TAG_FRAGMENT);
                        fragmentTransaction.addToBackStack(TAG_FRAGMENT);
                        fragmentTransaction.commit();

                    }

                    else{
//                        Snackbar.make(buttonView, "Select Two Option Only!", Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();

                        ApplicationUtil.showToast(getString(R.string.fill_alert),getActivity());
                    }

                }
                catch (Exception er){
                    er.printStackTrace();
                }
            }
        });

    }






//    private class sendRequestDetailData extends AsyncTask {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            // webservicePG.setVisibility(View.VISIBLE);
//            progressDialog.setMessage("Sending...");
//            progressDialog.incrementProgressBy(20);
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.show();
//
//            try{
//
//                broadcastMsgProxie = new EntryModuleProxy();
//
//                broadcastMsgProxie.setStrKeyParam(ApplicationConstants.REQUEST_KEY_SUPPORT);
//
//                    broadcastMsgProxie.setMeterNo(meternum);
//                    broadcastMsgProxie.setPoleNo(polenum.getText().toString());
//
//                broadcastMsgProxie.setActivityList1(check1);
//                broadcastMsgProxie.setActivityList2(check2);
//                broadcastMsgProxie.setIMEI_No(imeino);
//            }
//            catch(Exception er){
//                er.printStackTrace();
//            }
//
//
//
//        }
//
//        @Override
//        protected Object doInBackground(Object[] params) {
//
//
//            broadcastMsgBean = new BroadcastMsgBean();
//            //loginStatus = WebService.invokeLoginWS(empId,empPassword, "authenticateUser");
//            try {
//                if(ApplicationUtil.getInstance().checkInternetConnection(getActivity())) {
//                    internet = "y";
//
//                    if(imeino.equals("") || imeino.equals("000000000000000")){
//                        internet = "imeimiss";
//                    }
//                    else {
//                        broadcastMsgBean = ApplicationUtil.getInstance().getWebservice().entryModuleBroadcast(broadcastMsgProxie, getActivity());
//                    }
//                }
//
//                else{
//                    internet = "n";
//                }
//
//
//
//            } catch (ApplicationException e) {
//                e.printStackTrace();
//            }
//
//            //responseDto = ApplicationUtilTest.getInstance().getWebservice().gcmIdReg(regiUploadDto);
//            return null;
//        }
//
//
//        @Override
//        protected void onPostExecute(Object o) {
//            super.onPostExecute(o);
//            //Make Progress Bar invisible
//            //loginStatus = inputEmpid.getText().toString();
//            // webservicePG.setVisibility(View.INVISIBLE);
//            try{
//                progressDialog.dismiss();
//
//                if(internet.equals("y")) {
//
//                    if(!internet.equals("imeimiss")) {
//
//                        if (broadcastMsgBean != null) {
//
//                        if(!broadcastMsgBean.getACK().equals("false")) {
//                            //  Toast.makeText(getActivity(), "Successfully Submit", Toast.LENGTH_LONG).show();
//
//
//                            final Dialog dialog = new Dialog(getActivity(), R.style.MyCustomPrompt);
//                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                            //dialog.setCancelable(false);
//                            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                            dialog.setCanceledOnTouchOutside(false);
//                            dialog.setContentView(R.layout.promt_message_success);
//
//                            TextView requestid = (TextView)dialog.findViewById(R.id.requestid);
//                            TextView closeDialog = (TextView)dialog.findViewById(R.id.closePrompt);
//
//               /* messageTitle.setText(msg.getMSGTITLE());
//                messageDate.setText(msg.getMSGDATE());
//                messageDescription.setText(msg.getMSGDESC());*/
//
//                            requestid.setText(broadcastMsgBean.getACK());
//
//                            closeDialog.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    dialog.dismiss();
//
//                                    count=0;
//
//                                    meternumSpinner.setSelection(0);
//                                    polenum.setText("");
//                                    checkBox1.setChecked(false);
//                                    checkBox2.setChecked(false);
//                                    checkBox3.setChecked(false);
//                                    checkBox4.setChecked(false);
//                                    checkBox5.setChecked(false);
//                                    checkBox6.setChecked(false);
//                                }
//                            });
//                            // messageDescription.setText(message.getGenre().toString());
//                            //   messageTitle.setText(message.getTitle().toString());
//                            dialog.show();
//
//
//
//                        }
//                        else{
//                            Toast.makeText(getActivity(), "Server not responding! Please try again later..", Toast.LENGTH_LONG).show();
//                        }
//                        //sendMessage(fcmIdsStr);
//                    }
//                    else{
//                        Toast.makeText(getActivity(), "Some problem occurs", Toast.LENGTH_LONG).show();
//                    }
//
//                   }
//                else{
//                      Toast.makeText(getActivity(), "IMEI is Missing!", Toast.LENGTH_LONG).show();
//                }
//               }
//              else{
//                  Toast.makeText(getActivity(), "Check internet connection", Toast.LENGTH_LONG).show();
//              }
//          }
//
//            catch(Exception er){
//                er.printStackTrace();
//                Toast.makeText(getActivity(), "Some problem occurs", Toast.LENGTH_LONG).show();
//            }
//
//        }
//        @Override
//        protected void onProgressUpdate(Object[] values) {
//            super.onProgressUpdate(values);
//        }
//    }






}
