package hu.muller.andris.armando.makeithappen_withfriends.Messaging;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;

import hu.muller.andris.armando.makeithappen_withfriends.MainFragment;
import hu.muller.andris.armando.makeithappen_withfriends.R;
import hu.muller.andris.armando.makeithappen_withfriends.model.MyMessage;

public class SendMessageDialogFragment extends DialogFragment {

    private String userID;
    private String name;

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    RecyclerView messagesRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    EarlierMessagesAdapter messagesAdapter;
    EditText sendMessageEdittext;
    ImageButton sendMessageButton;
    TextView nameTextView;
    ImageView profileImageView;
    ImageButton backButton;

    public SendMessageDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_send_message_dialog, null);
        messagesRecyclerView = view.findViewById(R.id.earlier_messages_recyclerview);
        layoutManager = new LinearLayoutManager(getContext());
        messagesRecyclerView.setLayoutManager(layoutManager);
        messagesAdapter = new EarlierMessagesAdapter(userID, "me");
        messagesRecyclerView.setAdapter(messagesAdapter);
        sendMessageEdittext = view.findViewById(R.id.send_message_edittext);
        sendMessageButton = view.findViewById(R.id.send_message_button);
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyMessage message = new MyMessage(sendMessageEdittext.getText().toString(), Calendar.getInstance().getTimeInMillis(),"me",userID);
                message.save();
                messagesAdapter.addMessage(message);
                sendMessageEdittext.setText("");
                //TODO: send to firebase
            }
        });
        profileImageView = view.findViewById(R.id.send_message_friend_imageview);
        profileImageView.setImageBitmap(loadImageFromStorage(userID));
        nameTextView = view.findViewById(R.id.send_message_name_textview);
        nameTextView.setText(name);
        backButton = view.findViewById(R.id.send_message_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        return view;
    }

    private Bitmap loadImageFromStorage(String userId) {
        Bitmap b = null;
        try {
            File f=new File(MainFragment.IMAGE_DIR_PATH, userId + "_pic.jpg");
            b = BitmapFactory.decodeStream(new FileInputStream(f));
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return b;
    }

}
