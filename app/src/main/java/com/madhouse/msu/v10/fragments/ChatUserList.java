package com.madhouse.msu.v10.fragments;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.madhouse.msu.v10.MSU.MultiUserChatActivity;
import com.madhouse.msu.v10.R;
import com.madhouse.msu.v10.adapter.ChatUserListAdapter;
import com.madhouse.msu.v10.applicationUtils.ApplicationUtil;
import com.madhouse.msu.v10.applicationUtils.UserPreferences;
import com.madhouse.msu.v10.bean.ChatUserDetails;
import com.madhouse.msu.v10.exceptions.ApplicationException;
import com.madhouse.msu.v10.proxies.ChatUserDetailsProxy;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatUserList extends Fragment {

    private ListView userChatList;
    private Button groupChatBtn;
    private TextView setUserAdded;

    private CircleImageView setUserImage;
    private FrameLayout frameLayoutList;

    ArrayList<ChatUserDetails> arraylistUser;

    private UserPreferences userPreferences;

    public ChatUserList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate( R.layout.fragment_chat_user_list, container, false);

        userPreferences = UserPreferences.getInstance(getActivity());

        groupChatBtn = (Button)rootView.findViewById(R.id.groupChatBtn);
        userChatList = (ListView)rootView.findViewById(R.id.userChatList);
        setUserAdded =(TextView)rootView.findViewById(R.id.setUserAdded);

        arraylistUser = new ArrayList<ChatUserDetails>();

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading..");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        final Handler handler = new Handler(){
            @SuppressWarnings("deprecation")
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                progressDialog.dismiss();
                if(msg.what==0){
                    if(arraylistUser != null)
                    {
                        //Set To Listview
                        ChatUserListAdapter adapter = new ChatUserListAdapter(getActivity(), arraylistUser);
                        userChatList.setAdapter(adapter);
                    }
                }else{

                    //Comment
                    final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setMessage("Please try again later !");
                    alertDialog.setButton("Ok", new AlertDialog.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // TODO Auto-generated method stub
                            alertDialog.dismiss();

                        }
                    });
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

                //User Logged In
                String userLogInId = userPreferences.getUNIQUE_ID();
                System.out.println("...."+userLogInId);

                ChatUserDetailsProxy proxySetDetailsToFetch = new ChatUserDetailsProxy();
                proxySetDetailsToFetch.setStrLoginId(userLogInId);

                try {
                    arraylistUser = ApplicationUtil.getInstance().getWebservice().getAllUserDetails(proxySetDetailsToFetch, getActivity());
                    //ApplicationUtil.getInstance().setListOfQuestion(questionList);
                    System.out.println("..0....");

                    handler.sendEmptyMessage(0);
                } catch (ApplicationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    System.out.println("..1....");
                    handler.sendEmptyMessage(1);
                }
            }
        });

        /*String statusRmrks = PermissionRequest.showExternalStorage(HomeMainActivity.this);
        if(statusRmrks.equalsIgnoreCase("Deny"))
        {
            progressDialog.dismiss();
            //dialog.dismiss();
            PermissionRequest.requestDevicePermission(HomeMainActivity.this);
        }
        else
        {
            progressDialog.dismiss();*/
            thread.start();
        /*}*/

        groupChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MultiUserChatActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

}
