package com.var.bloodflow.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.var.bloodflow.Adapters.UserAdapter;
import com.var.bloodflow.ModelClasses.Chat;
import com.var.bloodflow.ModelClasses.Chatlist;
import com.var.bloodflow.ModelClasses.Users;
import com.var.bloodflow.R;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ChatFragment extends Fragment {

    FirebaseUser fuser;
    DatabaseReference reference;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<Users> mUsers;
    private List<String> usersList;


    public ChatFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        usersList = new ArrayList<>();
//        reference = FirebaseDatabase.getInstance().getReference("Chatlist").child(fuser.getUid());
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                usersList.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    Chatlist chatlist = snapshot.getValue(Chatlist.class);
//                    usersList.add(chatlist);
//                }
//                chatList();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chat chat = snapshot.getValue(Chat.class);
                    assert chat != null;
                    if (chat.getSender().equals(fuser.getUid())) {
                        usersList.add(chat.getReceiver());
                    }
                    if (chat.getReceiver().equals(fuser.getUid())) {
                        usersList.add(chat.getSender());
                    }
                }
                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        readChats();
        return view;
    }

//    private void chatList() {
//        mUsers = new ArrayList<>();
//        reference = FirebaseDatabase.getInstance().getReference("users");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                mUsers.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    Users user = snapshot.getValue(Users.class);
//                    for (Chatlist chatlist : usersList){
//                        if (user.getUser_id().equals(chatlist.getId())){
//                            mUsers.add(user);
//                        }
//                    }
//                }
//                userAdapter = new UserAdapter(getContext(),mUsers);//,true);
//                recyclerView.setAdapter(userAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    private void readChats() {
        mUsers = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Users user = snapshot.getValue(Users.class);
//
//
//                    // display 1 user from chats
//                    for (String id : usersList) {
//                        assert user != null;
//                        if (user.getUser_id().equals(id)) {
//                            boolean duplicateFound = false;
//                            for (Users user1 : mUsers) {
//                                if (user.getUser_id().equals(user1.getUser_id())) {
//                                    mUsers.add(user);
//                                    duplicateFound = true;
//                                }
//                            }
//
//                            if (!duplicateFound) {
//                                mUsers.add(user);
//                            }
//
//
////                    // display 1 user from chats
////                    for (String id : usersList) {
////                        if (user.getUser_id().equals(id)) {
////                            if (mUsers.size() != 0) {
////                                ListIterator<Users> listIteratorUser = mUsers.listIterator();
////                                while(listIteratorUser.hasNext()){
////                                    Users user1 = listIteratorUser.next();
////                                    if (!user.getUser_id().equals(user1.getUser_id())){
////                                        listIteratorUser.add(user);
////
////                                    }
////                                }
////                            }else {
////                                mUsers.add(user);
//                        }
//                    }
//                }
//
//                userAdapter = new UserAdapter(getContext(), mUsers);
//                recyclerView.setAdapter(userAdapter);
//                userAdapter.notifyDataSetChanged();
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//}
                    for (String id : usersList) {
                        assert user != null;
                        if (user.getUser_id().equals(id)) {
                            if (mUsers.size() != 0) {
                                int flag = 0;
                                for (Users u : mUsers) {
                                    if (user.getUser_id().equals(u.getUser_id())) {
                                        flag = 1;
                                        break;
                                    }
                                }
                                if (flag == 0)
                                    mUsers.add(user);
                            } else {

                                mUsers.add(user);
                            }
                        }
                    }
                }
                userAdapter = new UserAdapter(getContext(), mUsers);
                recyclerView.setAdapter(userAdapter);
                userAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}