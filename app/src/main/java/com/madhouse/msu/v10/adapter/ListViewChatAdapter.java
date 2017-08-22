package com.madhouse.msu.v10.adapter;

/**
 * Created by Krishna on 4/27/2016.
 */

import android.app.Dialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.madhouse.msu.v10.R;
import com.madhouse.msu.v10.applicationUtils.UserPreferences;
import com.madhouse.msu.v10.bean.Message;

import java.util.List;

public class ListViewChatAdapter extends RecyclerView.Adapter<ListViewChatAdapter.MyViewHolder> {

    private List<Message> moviesList;
    private  Message message;
    private TextView messageTitle, messageDate;
    private TextView messageDescription;
    private  View itemView;
    private CardView cv;
    private UserPreferences userPreferences;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre, titleR, yearR, genreR;
        public LinearLayout titleDateBox, titleDateBoxRight, msgBox, msgBoxRight;

        public MyViewHolder(View view) {
            super(view);
            titleDateBox = (LinearLayout) view.findViewById(R.id.title_date_box);
            msgBox = (LinearLayout) view.findViewById(R.id.msg_box);
            title = (TextView) view.findViewById(R.id.title);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.year);

            titleDateBoxRight = (LinearLayout) view.findViewById(R.id.title_date_box_right);
            msgBoxRight = (LinearLayout) view.findViewById(R.id.msg_box_right);
            titleR = (TextView) view.findViewById(R.id.titleright);
            genreR = (TextView) view.findViewById(R.id.genreright);
            yearR = (TextView) view.findViewById(R.id.yearright);

        }
    }


    public ListViewChatAdapter(List<Message> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        userPreferences = UserPreferences.getInstance(parent.getContext());
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_row_chat_thread, parent, false);

        cv = (CardView) itemView.findViewById(R.id.card_view);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //message = moviesList.get(position);

        cv.setTag(moviesList.get(position));

        if(userPreferences.getUserid().equals(moviesList.get(position).getCHATID())){

            holder.titleDateBox.setVisibility(View.VISIBLE);
            holder.msgBox.setVisibility(View.VISIBLE);

            holder.titleDateBoxRight.setVisibility(View.GONE);
            holder.msgBoxRight.setVisibility(View.GONE);

            holder.title.setText(moviesList.get(position).getMSGTITLE());
            holder.genre.setText(moviesList.get(position).getMSGDESC());
            holder.year.setText(moviesList.get(position).getMSGDATE());

        }
        else{
            holder.titleDateBox.setVisibility(View.GONE);
            holder.msgBox.setVisibility(View.GONE);

            holder.titleDateBoxRight.setVisibility(View.VISIBLE);
            holder.msgBoxRight.setVisibility(View.VISIBLE);

            holder.titleR.setText(moviesList.get(position).getMSGTITLE());
            holder.genreR.setText(moviesList.get(position).getMSGDESC());
            holder.yearR.setText(moviesList.get(position).getMSGDATE());

        }



        cv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Message msg  = (Message)v.getTag();
                //Message msg = (Message)view;

                final Dialog dialog = new Dialog(v.getContext(), R.style.MyCustomPrompt);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                //dialog.setCancelable(false);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCanceledOnTouchOutside(false);
                dialog.setContentView(R.layout.promt_message_desc);

                Button closeDialog = (Button)dialog.findViewById(R.id.closePrompt);
                messageTitle = (TextView)dialog.findViewById(R.id.alert_title);
                messageDate = (TextView)dialog.findViewById(R.id.alert_date);
                messageDescription = (TextView)dialog.findViewById(R.id.message_description);

                messageTitle.setText(msg.getMSGTITLE());
                messageDate.setText(msg.getMSGDATE());
                messageDescription.setText(msg.getMSGDESC());

                closeDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                // messageDescription.setText(message.getGenre().toString());
                //   messageTitle.setText(message.getTitle().toString());
                dialog.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}