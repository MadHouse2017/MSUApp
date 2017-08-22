package com.madhouse.msu.v10.MSU;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.madhouse.msu.v10.R;
import com.madhouse.msu.v10.adapter.MultiUsrMsgChatListAdapter;
import com.madhouse.msu.v10.adapter.SinglUsrMsgChatListAdapter;
import com.madhouse.msu.v10.applicationUtils.ApplicationUtil;
import com.madhouse.msu.v10.applicationUtils.UserPreferences;
import com.madhouse.msu.v10.bean.MultiUsrMsgChat;
import com.madhouse.msu.v10.exceptions.ApplicationException;
import com.madhouse.msu.v10.proxies.ChatReplyProxy;
import com.madhouse.msu.v10.proxies.ChatUserDetailsProxy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MultiUserChatActivity extends Activity implements RecognitionListener {

    ListView quesAnsChatListview;

    ArrayList<MultiUsrMsgChat> userChat;

    EditText messageReply;
    Button btn_send;

    String strResponse;

    MultiUsrMsgChatListAdapter adapter;
    private UserPreferences userPreferences;

    LinearLayout layoutImgSlct;

    private SpeechRecognizer speech = null;
    private Intent recogniserIntent;
    private int recognizerNumber;
    ProgressBar progressBar;
    ImageView imgAttachment;
    private int recentmsgCount=0,oldmsgCount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_user_chat);

        userPreferences = UserPreferences.getInstance(MultiUserChatActivity.this);

        speech = SpeechRecognizer.createSpeechRecognizer(MultiUserChatActivity.this);

        imgAttachment = (ImageView) findViewById(R.id.imgAttachment);
        progressBar =(ProgressBar)findViewById(R.id.progressBar);
        layoutImgSlct = (LinearLayout)findViewById(R.id.layoutImgSlct);

        quesAnsChatListview = (ListView)findViewById(R.id.quesAnsChatListview);
        btn_send = (Button)findViewById(R.id.btn_send);
        messageReply = (EditText)findViewById(R.id.messageReply);

        ImageView back = (ImageView)findViewById(R.id.backbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        userChat = new ArrayList<MultiUsrMsgChat>();

        final ProgressDialog progressDialog = new ProgressDialog(MultiUserChatActivity.this);
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
                    if(userChat != null)
                    {


                        recentmsgCount = userChat.size();

                        adapter = new MultiUsrMsgChatListAdapter(MultiUserChatActivity.this, userChat);
                        quesAnsChatListview.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        if(oldmsgCount<recentmsgCount){
                            quesAnsChatListview.smoothScrollToPosition(adapter.getCount());
                            oldmsgCount=recentmsgCount;
                        }
                        quesAnsChatListview.smoothScrollToPosition(adapter.getCount());
                    }
                    else
                    {
                        final AlertDialog alertDialog = new AlertDialog.Builder(MultiUserChatActivity.this).create();
                        alertDialog.setMessage("No conversation found !");
                        alertDialog.setButton("Ok", new AlertDialog.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                // TODO Auto-generated method stub
                                alertDialog.dismiss();

                            }
                        });
                        if(!MultiUserChatActivity.this.isFinishing())
                        {
                            alertDialog.show();
                        }
                    }
                }else{

                    //Comment
                    final AlertDialog alertDialog = new AlertDialog.Builder(MultiUserChatActivity.this).create();
                    alertDialog.setMessage("Please try again later !");
                    alertDialog.setButton("Ok", new AlertDialog.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // TODO Auto-generated method stub
                            alertDialog.dismiss();

                        }
                    });
                    if(!MultiUserChatActivity.this.isFinishing())
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

                ChatUserDetailsProxy proxySetDetailsToFetch = new ChatUserDetailsProxy();
                proxySetDetailsToFetch.setStrLoginId(userLogInId);

                try {
                    userChat = ApplicationUtil.getInstance().getWebservice().getMultiUserConversation(proxySetDetailsToFetch, MultiUserChatActivity.this);
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

        layoutImgSlct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecognition();
                recognizerNumber = 0;
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(messageReply.getText().toString().equalsIgnoreCase(""))
                {
                    return;
                }

                MultiUsrMsgChat chatUser = new MultiUsrMsgChat();
                chatUser.setUserId(userPreferences.getUNIQUE_ID());
                chatUser.setUserMsg(messageReply.getText().toString());
                chatUser.setUserName(userPreferences.getNAME());

                Calendar calender = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy hh:mm");
                String strDate = format.format(calender.getTime());

                chatUser.setUserMsgDate(strDate);

                userChat.add(chatUser);
                adapter.notifyDataSetChanged();
                quesAnsChatListview.smoothScrollToPosition(adapter.getCount());

                //messageReply.setText("");

                final ProgressDialog progressDialog = new ProgressDialog(MultiUserChatActivity.this);
                progressDialog.setMessage("Uploading..");
                //progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
                final Handler handler = new Handler(){
                    @SuppressWarnings("deprecation")
                    @Override
                    public void handleMessage(Message msg) {
                        // TODO Auto-generated method stub
                        progressDialog.dismiss();
                        if(msg.what==0){

                            messageReply.setText("");

                            final AlertDialog alertDialog = new AlertDialog.Builder(MultiUserChatActivity.this).create();
                            alertDialog.setMessage(strResponse.toString());
                            alertDialog.setButton("Ok", new AlertDialog.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    // TODO Auto-generated method stub
                                    alertDialog.dismiss();
                                    //finish();
                                    messageReply.setText("");
                                }
                            });
                            if(!MultiUserChatActivity.this.isFinishing())
                            {
                               // alertDialog.show();
                            }
                        }else{

                            //Comment
                            final AlertDialog alertDialog = new AlertDialog.Builder(MultiUserChatActivity.this).create();
                            alertDialog.setMessage("Please try again later !");
                            alertDialog.setButton("Ok", new AlertDialog.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    // TODO Auto-generated method stub
                                    alertDialog.dismiss();

                                }
                            });
                            if(!MultiUserChatActivity.this.isFinishing())
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

                        ChatReplyProxy proxySetDetailsToFetch = new ChatReplyProxy();
                        proxySetDetailsToFetch.setStrLoginIdFrom(userPreferences.getUNIQUE_ID());
                        //proxySetDetailsToFetch.setStrLoginIdTo("MSU15081711");
                        proxySetDetailsToFetch.setStrMsg(messageReply.getText().toString());
                        proxySetDetailsToFetch.setStrStatus("Group");

                        try {

                            strResponse = ApplicationUtil.getInstance().getWebservice().chatReply(proxySetDetailsToFetch, MultiUserChatActivity.this);
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

                thread.start();

            }
        });

    }

    public void startRecognition() {
        progressBar.setVisibility(View.VISIBLE);
        imgAttachment.setVisibility(View.GONE);
        speech.setRecognitionListener(this);
        recogniserIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recogniserIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        recogniserIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recogniserIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        speech.startListening(recogniserIntent);
    }

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int error) {

    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        messageReply.setText(matches.get(0));

        progressBar.setVisibility(View.GONE);
        imgAttachment.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }
}
