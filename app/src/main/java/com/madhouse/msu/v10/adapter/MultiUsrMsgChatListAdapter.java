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
import com.madhouse.msu.v10.bean.MultiUsrMsgChat;

import java.util.ArrayList;

/**
 * Created by rv software on 15-Aug-17.
 */

public class MultiUsrMsgChatListAdapter   extends ArrayAdapter<MultiUsrMsgChat> {

    Activity context;
    ArrayList<MultiUsrMsgChat> userOrgConversation;

    private UserPreferences userPreferences;

    public MultiUsrMsgChatListAdapter(Activity context,
                                      ArrayList<MultiUsrMsgChat> chatConversation) {
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
        if (userOrgConversation.get(position).getUserId().equalsIgnoreCase(userPreferences.getUNIQUE_ID())) {

            //System.out.println("....."+quesAnsChat.get(position).getAnswerFromId().toString());

            rowView = inflater.inflate(R.layout.custom_user_self_msg,null, true);

            TextView textView = (TextView) rowView.findViewById(R.id.timestampUser);
            textView.setText(userOrgConversation.get(position).getUserMsgDate());

            TextView textView2 = (TextView) rowView.findViewById(R.id.messageUser);
            textView2.setText(userOrgConversation.get(position).getUserMsg());

            TextView textView3 = (TextView) rowView.findViewById(R.id.userNameReplied);
            textView3.setText(userOrgConversation.get(position).getUserName());


        } else /*if (quesAnsChat.get(position).getAnswerFromId() == "USER")*/
        {

            //System.out.println("....."+quesAnsChat.get(position).getAnswerFromId().toString());

            rowView = inflater.inflate(R.layout.custom_other_msg,null, true);

            TextView textView = (TextView) rowView.findViewById(R.id.timestampWeb);
            textView.setText(userOrgConversation.get(position).getUserMsgDate());

            TextView textView2 = (TextView) rowView.findViewById(R.id.messageWeb);
            textView2.setText(userOrgConversation.get(position).getUserMsg());

            TextView textView3 = (TextView) rowView.findViewById(R.id.webUsernameReplied);
            textView3.setText(userOrgConversation.get(position).getUserName());

        }

        return rowView;
    }
}
