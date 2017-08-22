package com.madhouse.msu.v10.adapter;

/**
 * Created by Krishna on 5/2/2017.
 */

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.madhouse.msu.v10.R;
import com.madhouse.msu.v10.applicationUtils.UserPreferences;
import com.madhouse.msu.v10.bean.Album;

import java.util.List;
import java.util.Random;

/**
 * Created by Krishna on 12/08/17.
 */
public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder>{

    private Context mContext;
    private List<Album> albumList;
    public int VIEW_TYPE_HEADER = 0;
    public int VIEW_TYPE_ITEM = 1;
    public int VIEW_TYPE_ITEM_CALL = 2;
    public int VIEW_TYPE_ITEM_THIN = 3;
    public int VIEW_TYPE_TEXT = 4;
    private UserPreferences userPreferences;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView cardlogo, overflow, cardlogo1,cardlogo2,cardlogo3,cardlogo4;
        public LinearLayout thumbnail;
        public CardView card_view;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (LinearLayout) view.findViewById(R.id.thumbnail);
            cardlogo = (ImageView) view.findViewById(R.id.cardlogo);
            cardlogo1 = (ImageView) view.findViewById(R.id.cardlogo1);
            cardlogo2 = (ImageView) view.findViewById(R.id.cardlogo2);
            cardlogo3 = (ImageView) view.findViewById(R.id.cardlogo3);
            cardlogo4 = (ImageView) view.findViewById(R.id.cardlogo4);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            card_view = (CardView) view.findViewById(R.id.card_view);
        }
    }


    public AlbumsAdapter(Context mContext, List<Album> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public int getItemViewType(int position) {
        //return isHeader(position) ? VIEW_TYPE_HEADER : VIEW_TYPE_ITEM;

        if(isHeader(position) == 1){
           return VIEW_TYPE_HEADER;
        }
        if(isHeader(position) == 2){
            return VIEW_TYPE_ITEM_CALL;
        }
        if(isHeader(position) == 3){
            return VIEW_TYPE_ITEM_THIN;
        }
        if(isHeader(position) == 4){
            return VIEW_TYPE_TEXT;
        }
        else{
           return VIEW_TYPE_ITEM;

        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        userPreferences = UserPreferences.getInstance(mContext);

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_card, parent, false);

        View itemViewLinear = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_card_linear, parent, false);

        View itemViewLinearCall = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_card_linear_call, parent, false);

        View itemViewLinearThin = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_card_linear_thin, parent, false);

        View itemViewText = LayoutInflater.from(parent.getContext()).inflate(R.layout.album_card_text, parent, false);


        if (viewType == VIEW_TYPE_HEADER) {
            // Setup header view holder

//            Random r = new Random();
//            int randPosition = r.nextInt(20 - 1) + 1;
//
//
//            final ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(parent.getContext(), R.anim.flippingx);
//            anim.setTarget(itemViewLinear);
//            anim.setDuration(2000);
//            anim.setRepeatCount(0);
//            anim.setStartDelay(randPosition*1000);
//            anim.start();

            return new MyViewHolder(itemViewLinear);
        }



        if (viewType == VIEW_TYPE_ITEM_CALL) {
            // Setup header view holder

            return new MyViewHolder(itemViewLinearCall);
        }

        if (viewType == VIEW_TYPE_ITEM_THIN) {

//            Random r = new Random();
//            int randPosition = r.nextInt(20 - 1) + 1;
//
//            // Setup header view holder
//            final ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(parent.getContext(), R.anim.flippingx);
//            anim.setTarget(itemViewLinearThin);
//            anim.setDuration(3000);
//            anim.setRepeatCount(0);
//            anim.setStartDelay(randPosition*1000);
//            anim.start();
            return new MyViewHolder(itemViewLinearThin);

        }

        if (viewType == VIEW_TYPE_TEXT) {
            return new MyViewHolder(itemViewText);
        }



       /* final ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(parent.getContext(), R.anim.flipping);


        anim.setTarget(itemView);
        anim.setDuration(8000);
        anim.setRepeatCount(4);
        anim.setStartDelay(6000);
        anim.start();*/

        return new MyViewHolder(itemView);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {


       /* if (isHeader(position)) {
            return;
        }*/

        Album album = albumList.get(position);

        final ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(mContext, R.anim.flippingx);


        // loading album cover using Glide library
        if(position == 0){

            holder.cardlogo1.setVisibility(View.GONE);
            holder.cardlogo2.setVisibility(View.GONE);
            holder.cardlogo3.setVisibility(View.GONE);
            holder.cardlogo4.setVisibility(View.GONE);

            holder.thumbnail.setBackgroundColor(ContextCompat.getColor(mContext, R.color.nocurrent));

            anim.setTarget(holder.title);

            anim.setDuration(3000);
            anim.setRepeatCount(0);
            anim.setStartDelay(3000);
            anim.start();

        }

        if(position == 1){

            holder.cardlogo1.setVisibility(View.GONE);
            holder.cardlogo2.setVisibility(View.GONE);
            holder.cardlogo3.setVisibility(View.GONE);
            holder.cardlogo4.setVisibility(View.GONE);

            holder.thumbnail.setBackgroundColor(ContextCompat.getColor(mContext, R.color.safetytip));
            anim.setTarget(holder.title);
            anim.setDuration(4000);
            anim.setRepeatCount(0);
            anim.setStartDelay(5000);
            anim.start();
        }
        if(position == 2){

            holder.cardlogo1.setVisibility(View.GONE);
            holder.cardlogo2.setVisibility(View.GONE);
            holder.cardlogo3.setVisibility(View.GONE);
            holder.cardlogo4.setVisibility(View.GONE);


            holder.thumbnail.setBackgroundColor(ContextCompat.getColor(mContext, R.color.streetlight));

            anim.setTarget(holder.title);
            anim.setDuration(5000);
            anim.setRepeatCount(0);
            anim.setStartDelay(6000);
            anim.start();
        }

        if(position == 3){

            holder.cardlogo1.setVisibility(View.GONE);
            holder.cardlogo2.setVisibility(View.GONE);
            holder.cardlogo3.setVisibility(View.GONE);
            holder.cardlogo4.setVisibility(View.GONE);

            holder.thumbnail.setBackgroundColor(ContextCompat.getColor(mContext, R.color.arun));

            anim.setTarget(holder.title);
            anim.setDuration(3000);
            anim.setRepeatCount(0);
            anim.setStartDelay(2000);
            anim.start();
        }

        if(position == 4){

            holder.cardlogo1.setVisibility(View.GONE);
            holder.cardlogo2.setVisibility(View.GONE);
            holder.cardlogo3.setVisibility(View.GONE);
            holder.cardlogo4.setVisibility(View.GONE);

            holder.thumbnail.setBackgroundColor(ContextCompat.getColor(mContext, R.color.myaccount));

            anim.setTarget(holder.title);
            //anim.setTarget(holder.card_view);
            anim.setDuration(3000);
            anim.setRepeatCount(0);
            anim.setStartDelay(3000);
            anim.start();
        }


        if(position == 5){

            holder.cardlogo1.setVisibility(View.GONE);
            holder.cardlogo2.setVisibility(View.GONE);
            holder.cardlogo3.setVisibility(View.GONE);
            holder.cardlogo4.setVisibility(View.GONE);

            holder.thumbnail.setBackgroundColor(ContextCompat.getColor(mContext, R.color.instantpay));
            anim.setTarget(holder.title);
            //anim.setTarget(holder.card_view);
            anim.setDuration(4000);
            anim.setRepeatCount(0);
            anim.setStartDelay(10000);
            anim.start();
        }


        if(position == 6){


//            holder.cardlogo1.setVisibility(View.INVISIBLE);
//            holder.cardlogo2.setVisibility(View.VISIBLE);
//            holder.cardlogo3.setVisibility(View.INVISIBLE);
//            holder.cardlogo4.setVisibility(View.VISIBLE);
//
//            //Glide.with(mContext).load(album.getThumbnail()).into(holder.cardlogo);
//            Glide.with(mContext).load(album.getThumbnail1()).into(holder.cardlogo1);
//            Glide.with(mContext).load(album.getThumbnail2()).into(holder.cardlogo2);
//            Glide.with(mContext).load(album.getThumbnail3()).into(holder.cardlogo3);
//          Glide.with(mContext).load(album.getThumbnail4()).into(holder.cardlogo4);

            holder.cardlogo1.setVisibility(View.GONE);
            holder.cardlogo2.setVisibility(View.GONE);
            holder.cardlogo3.setVisibility(View.GONE);
            holder.cardlogo4.setVisibility(View.GONE);

            holder.thumbnail.setBackgroundColor(ContextCompat.getColor(mContext, R.color.bg_screen4));
            anim.setTarget(holder.title);
            //anim.setTarget(holder.card_view);
            anim.setDuration(4000);
            anim.setRepeatCount(0);
            anim.setStartDelay(10000);
            anim.start();


        }


        if(position == 7){

            holder.cardlogo1.setVisibility(View.GONE);
            holder.cardlogo2.setVisibility(View.GONE);
            holder.cardlogo3.setVisibility(View.GONE);
            holder.cardlogo4.setVisibility(View.GONE);

            RotateAnimation rotateAnim = new RotateAnimation(0f, 350f, 30f, 30f);
            rotateAnim.setInterpolator(new LinearInterpolator());
            rotateAnim.setRepeatCount(Animation.INFINITE);
            rotateAnim.setDuration(3000);
            //holder.overflow.startAnimation(rotateAnim);


            holder.thumbnail.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorOrange));
            anim.setTarget(holder.title);
            //anim.setTarget(holder.card_view);
            anim.setDuration(1000);
            anim.setRepeatCount(0);
            anim.setStartDelay(15000);
            anim.start();
        }

        if(position == 8){

            holder.cardlogo1.setVisibility(View.GONE);
            holder.cardlogo2.setVisibility(View.GONE);
            holder.cardlogo3.setVisibility(View.GONE);
            holder.cardlogo4.setVisibility(View.GONE);

            RotateAnimation rotateAnim = new RotateAnimation(0f, 350f, 30f, 30f);
            rotateAnim.setInterpolator(new LinearInterpolator());
            rotateAnim.setRepeatCount(Animation.INFINITE);
            rotateAnim.setDuration(3000);
           // holder.overflow.startAnimation(rotateAnim);


            holder.thumbnail.setBackgroundColor(ContextCompat.getColor(mContext, R.color.myaccount));
            anim.setTarget(holder.title);
            //anim.setTarget(holder.card_view);
            anim.setDuration(2000);
            anim.setRepeatCount(0);
            anim.setStartDelay(12000);
            anim.start();
        }

        if(position == 9){

            holder.cardlogo1.setVisibility(View.GONE);
            holder.cardlogo2.setVisibility(View.GONE);
            holder.cardlogo3.setVisibility(View.GONE);
            holder.cardlogo4.setVisibility(View.GONE);

            RotateAnimation rotateAnim = new RotateAnimation(0f, 350f, 30f, 30f);
            rotateAnim.setInterpolator(new LinearInterpolator());
            rotateAnim.setRepeatCount(Animation.INFINITE);
            rotateAnim.setDuration(3000);
           // holder.overflow.startAnimation(rotateAnim);


            holder.thumbnail.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dot_dark_screen3));
            anim.setTarget(holder.title);
            //anim.setTarget(holder.card_view);
            anim.setDuration(3000);
            anim.setRepeatCount(0);
            anim.setStartDelay(20000);
            anim.start();
        }

        if(position == 10){

            holder.cardlogo1.setVisibility(View.GONE);
            holder.cardlogo2.setVisibility(View.GONE);
            holder.cardlogo3.setVisibility(View.GONE);
            holder.cardlogo4.setVisibility(View.GONE);

            RotateAnimation rotateAnim = new RotateAnimation(0f, 350f, 30f, 30f);
            rotateAnim.setInterpolator(new LinearInterpolator());
            rotateAnim.setRepeatCount(Animation.INFINITE);
            rotateAnim.setDuration(3000);
           // holder.overflow.startAnimation(rotateAnim);


            holder.thumbnail.setBackgroundColor(ContextCompat.getColor(mContext, R.color.streetlight));
            anim.setTarget(holder.title);
            //anim.setTarget(holder.card_view);
            anim.setDuration(3000);
            anim.setRepeatCount(0);
            anim.setStartDelay(5000);
            anim.start();
        }

        if(position == 11){

            holder.cardlogo1.setVisibility(View.GONE);
            holder.cardlogo2.setVisibility(View.GONE);
            holder.cardlogo3.setVisibility(View.GONE);
            holder.cardlogo4.setVisibility(View.GONE);

            if(userPreferences.getCANYOUDOIMG1().equals("DONE")){
                holder.overflow.setVisibility(holder.itemView.VISIBLE);
                holder.title.setVisibility(View.GONE);
                holder.count.setVisibility(View.GONE);
            }


            RotateAnimation rotateAnim = new RotateAnimation(0f, 350f, 30f, 30f);
            rotateAnim.setInterpolator(new LinearInterpolator());
            rotateAnim.setRepeatCount(Animation.INFINITE);
            rotateAnim.setDuration(3000);
           // holder.overflow.startAnimation(rotateAnim);


           // holder.thumbnail.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dodont));

        }


        if(position == 12){

//            holder.cardlogo1.setVisibility(View.GONE);
//            holder.cardlogo2.setVisibility(View.GONE);
//            holder.cardlogo3.setVisibility(View.GONE);
//            holder.cardlogo4.setVisibility(View.GONE);
//
//            holder.thumbnail.setVisibility(View.GONE);

            holder.cardlogo1.setVisibility(View.GONE);
            holder.cardlogo2.setVisibility(View.GONE);
            holder.cardlogo3.setVisibility(View.GONE);
            holder.cardlogo4.setVisibility(View.GONE);

            if(userPreferences.getCANYOUDOIMG2().equals("DONE")){
                holder.overflow.setVisibility(holder.itemView.VISIBLE);
                holder.title.setVisibility(View.GONE);
                holder.count.setVisibility(View.GONE);
            }

            RotateAnimation rotateAnim = new RotateAnimation(0f, 350f, 30f, 30f);
            rotateAnim.setInterpolator(new LinearInterpolator());
            rotateAnim.setRepeatCount(Animation.INFINITE);
            rotateAnim.setDuration(3000);
            // holder.overflow.startAnimation(rotateAnim);


           // holder.thumbnail.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dodont));



        }


        if(position == 13){

            holder.cardlogo1.setVisibility(View.GONE);
            holder.cardlogo2.setVisibility(View.GONE);
            holder.cardlogo3.setVisibility(View.GONE);
            holder.cardlogo4.setVisibility(View.GONE);

            if(userPreferences.getCANYOUDOIMG3().equals("DONE")){
                holder.overflow.setVisibility(holder.itemView.VISIBLE);
                holder.title.setVisibility(View.GONE);
                holder.count.setVisibility(View.GONE);
            }

           // holder.thumbnail.setBackgroundResource(R.drawable.idea);
            //holder.thumbnail.setBackgroundColor(ContextCompat.getColor(mContext, R.color.feedback));

        }

        if(position == 14){

            holder.cardlogo1.setVisibility(View.GONE);
            holder.cardlogo2.setVisibility(View.GONE);
            holder.cardlogo3.setVisibility(View.GONE);
            holder.cardlogo4.setVisibility(View.GONE);

            if(userPreferences.getCANYOUDOIMG4().equals("DONE")){
                holder.overflow.setVisibility(holder.itemView.VISIBLE);
                holder.title.setVisibility(View.GONE);
                holder.count.setVisibility(View.GONE);
            }

            //holder.thumbnail.setBackgroundResource(R.drawable.idea);
           // holder.thumbnail.setBackgroundColor(ContextCompat.getColor(mContext, R.color.netmeter));



        }

        if(position == 15){

            holder.cardlogo1.setVisibility(View.GONE);
            holder.cardlogo2.setVisibility(View.GONE);
            holder.cardlogo3.setVisibility(View.GONE);
            holder.cardlogo4.setVisibility(View.GONE);

            if(userPreferences.getCANYOUDOIMG5().equals("DONE")){
                holder.overflow.setVisibility(holder.itemView.VISIBLE);
                holder.title.setVisibility(View.GONE);
                holder.count.setVisibility(View.GONE);
            }

           // holder.thumbnail.setBackgroundResource(R.drawable.idea);
            // holder.thumbnail.setBackgroundColor(ContextCompat.getColor(mContext, R.color.netmeter));



        }

        holder.title.setText(album.getName());
        holder.count.setText(album.getNumOfSongs());
        Glide.with(mContext).load(album.getThumbnail()).into(holder.cardlogo);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // showPopupMenu(holder.overflow);
            }
        });
    }

    public int isHeader(int position) {

//        if(position == 4 || position == 5 ){
//            return 1;
//        }
//        if(position == 7 || position == 8 || position == 9 || position == 10 || position == 11 || position == 13 || position == 14){
//            return 3;
//        }
//        if(position == 12){
//            return 4;
//        }
//        if(position == 6){
//            return 2;
//        }
//        else{
//            return 0;
//        }


        if(position == 0 || position == 5 || position == 10){
            return 4;
        }

        if(position == 1 || position == 2 || position == 3 || position == 4 || position == 7 || position == 8 || position == 9 || position == 6 ){
            return 3;
        }


        if(position == 11 || position == 12 || position == 13 || position == 14 || position == 15){
            return 0;
        }


        else{
            return 3;
        }

    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}