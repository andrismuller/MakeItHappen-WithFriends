package hu.muller.andris.armando.makeithappen_withfriends.Messaging;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hu.muller.andris.armando.makeithappen_withfriends.R;
import hu.muller.andris.armando.makeithappen_withfriends.model.MyMessage;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagingFragment extends Fragment {
    RecyclerView messagingRecyclerView;
    FriendsAdapter friendsAdapter;
    FloatingActionButton addFriendButton;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference mFirebaseDatabaseReference;

    public MessagingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messaging, container, false);
        messagingRecyclerView = view.findViewById(R.id.friends_recyclerview);
        layoutManager = new LinearLayoutManager(getContext());
        friendsAdapter = new FriendsAdapter(getActivity(), MessagingFragment.this);
        messagingRecyclerView.setLayoutManager(layoutManager);
        messagingRecyclerView.setAdapter(friendsAdapter);

//        addFriendButton = view.findViewById(R.id.add_friend_button);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FriendsAdapter.SEND_MESSAGE_REQUEST_CODE){

        }
    }
}
