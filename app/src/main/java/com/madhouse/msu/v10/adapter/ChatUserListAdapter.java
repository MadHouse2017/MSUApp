package com.madhouse.msu.v10.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.madhouse.msu.v10.MSU.SingleUserChatActivity;
import com.madhouse.msu.v10.R;
import com.madhouse.msu.v10.bean.ChatUserDetails;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by rv software on 10-Aug-17.
 */

public class ChatUserListAdapter extends ArrayAdapter<ChatUserDetails> {

    ArrayList<ChatUserDetails> userListDetails;
    Activity context;

    CircleImageView setUsrImg;
    ///TextView txtUsrName, txtUsrDesg;

    LinearLayout clickToChat;


    public ChatUserListAdapter(Activity context, ArrayList<ChatUserDetails> userListDtls) {
        super(context, R.layout.extra_file_test, userListDtls);

        this.context = context;
        this.userListDetails = userListDtls;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView = inflater.inflate(R.layout.custom_chat_user_list,null, true);

        TextView textView = (TextView) rowView.findViewById(R.id.txtUsrName);
        textView.setText(userListDetails.get(position).getStrUsrName());

        TextView textView2 = (TextView) rowView.findViewById(R.id.txtUsrDesg);
        textView2.setText(userListDetails.get(position).getStrUsrDesg());

        clickToChat = (LinearLayout) rowView.findViewById(R.id.clickToChat);

        clickToChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicked.....");
                Intent intent = new Intent(getContext(), SingleUserChatActivity.class);
                System.out.println("selected index..."+userListDetails.get(position).getStrUsrId());
                intent.putExtra("userLoginIdTo", userListDetails.get(position).getStrUsrId());
                getContext().startActivity(intent);
            }
        });

        return rowView;
    }
}
