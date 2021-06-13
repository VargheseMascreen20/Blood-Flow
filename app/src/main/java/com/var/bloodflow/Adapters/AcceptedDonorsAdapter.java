package com.var.bloodflow.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.var.bloodflow.MessageActivity;
import com.var.bloodflow.ModelClasses.Users;
import com.var.bloodflow.R;

import java.util.List;

public class AcceptedDonorsAdapter extends RecyclerView.Adapter<AcceptedDonorsAdapter.ViewHolder> {

    String theLastMesssage;
    private Context mContext;
    private List<Users> mUsers;
//    private boolean isChat;

    public AcceptedDonorsAdapter(Context mContext, List<Users> mUsers) {//boolean isChat
        this.mUsers = mUsers;
        this.mContext = mContext;
//        this.isChat = isChat;

    }

    @NonNull
    @Override
    public AcceptedDonorsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.accepted_donors_item, parent, false);
        return new AcceptedDonorsAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AcceptedDonorsAdapter.ViewHolder holder, int position) {
        Users user = mUsers.get(position);
        String img = "https://firebasestorage.googleapis.com/v0/b/blood-flow-c80bc.appspot.com/o/image%2FUsers_Profile_Cover_Imgs%2FLogoMakr-4q1rZ1.png?alt=media&token=5bb4f49a-eb7c-48b3-99dc-a2590aab42a1";
        holder.username.setText(user.getName() + " (" + user.getBldgrp() + ")");
        if (user.getImage().equals(img)) {
            Glide.with(mContext).load("https://firebasestorage.googleapis.com/v0/b/blood-flow-c80bc.appspot.com/o/image%2FUsers_Profile_Cover_Imgs%2Fapp_icon.jpeg?alt=media&token=f5a55fdb-d743-4593-a395-ca391cfffd06").circleCrop().placeholder(R.color.black).into(holder.profile_image);
        } else {
            Glide.with(mContext).load(user.getImage()).into(holder.profile_image);
        }

//        if(isChat){
//            lastMessage(user.getUser_id(),holder.last_msg);
//        }else {
//            holder.last_msg.setVisibility(View.GONE);
//        }
//
//        if (isChat){
//            if (user.getStatus().equals("online")){
//                holder.img_on.setVisibility(View.VISIBLE);
//                holder.img_off.setVisibility(View.GONE);
//            }
//            else{
//                holder.img_on.setVisibility(View.GONE);
//                holder.img_off.setVisibility(View.VISIBLE);
//            }
//        }else {
//            holder.img_on.setVisibility(View.GONE);
//            holder.img_off.setVisibility(View.GONE);
//        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("userid", user.getUser_id());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mUsers != null)
            return mUsers.size();
        return 0;
    }

//    private void lastMessage(String userid, TextView last_msg) {
//        theLastMesssage = "default";
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Chats");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Chat chat = snapshot.getValue(Chat.class);
//                    if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid)
//                            || chat.getReceiver().equals(userid) && chat.getSender().equals(firebaseUser.getUid())) {
//                        theLastMesssage = chat.getMessage();
//                    }
//                }
//                switch (theLastMesssage){
//                    case "default":last_msg.setText("No Message");
//                    break;
//
//                    default:last_msg.setText(theLastMesssage);
//                    break;
//                }
//                theLastMesssage = "default";
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView username;
        public ImageView profile_image;
//        private ImageView img_on;
//        private ImageView img_off;
//        private TextView last_msg;


        public ViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
//            img_on = itemView.findViewById(R.id.img_on);
//            img_off = itemView.findViewById(R.id.img_off);
//            last_msg = itemView.findViewById(R.id.last_msg);
        }
    }
}