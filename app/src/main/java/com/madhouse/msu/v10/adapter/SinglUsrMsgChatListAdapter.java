package com.madhouse.msu.v10.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.madhouse.msu.v10.R;
import com.madhouse.msu.v10.applicationUtils.UserPreferences;
import com.madhouse.msu.v10.bean.SinglUsrChatList;

import java.util.ArrayList;

/**
 * Created by Rajveer on 14/08/2017.
 */

public class SinglUsrMsgChatListAdapter  extends ArrayAdapter<SinglUsrChatList>{

    Activity context;
    ArrayList<SinglUsrChatList> userOrgConversation;

    private UserPreferences userPreferences;

    public SinglUsrMsgChatListAdapter(Activity context,
                                    ArrayList<SinglUsrChatList> chatConversation) {
        super(context, R.layout.extra_file_test, chatConversation);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.userOrgConversation = chatConversation;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //super.getView(position, convertView, parent);

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater
                .inflate(R.layout.custom_user_self_msg, null, true);

        userPreferences = UserPreferences.getInstance(getContext());

        //WEB USER PUBLIC
        if (userOrgConversation.get(position).getStrUserFrom().equalsIgnoreCase(userPreferences.getUNIQUE_ID())) {

            //System.out.println("....."+quesAnsChat.get(position).getAnswerFromId().toString());

            rowView = inflater.inflate(R.layout.custom_user_self_msg,null, true);

            TextView textView = (TextView) rowView.findViewById(R.id.timestampUser);
            textView.setText(userOrgConversation.get(position).getStrUserDate());

            TextView textView2 = (TextView) rowView.findViewById(R.id.messageUser);
            textView2.setText(userOrgConversation.get(position).getStrUserMsg());


        } else /*if (quesAnsChat.get(position).getAnswerFromId() == "USER")*/
        {

            //System.out.println("....."+quesAnsChat.get(position).getAnswerFromId().toString());

            rowView = inflater.inflate(R.layout.custom_other_msg,null, true);

            TextView textView = (TextView) rowView.findViewById(R.id.timestampUser);
            textView.setText(userOrgConversation.get(position).getStrUserDate());

            TextView textView2 = (TextView) rowView.findViewById(R.id.messageUser);
            textView2.setText(userOrgConversation.get(position).getStrUserMsg());

        }

        return rowView;
    }
}
