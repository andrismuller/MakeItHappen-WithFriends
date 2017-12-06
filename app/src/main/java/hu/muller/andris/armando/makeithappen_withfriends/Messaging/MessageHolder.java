package hu.muller.andris.armando.makeithappen_withfriends.Messaging;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import hu.muller.andris.armando.makeithappen_withfriends.R;

/**
 * Created by Muller Andras on 12/4/2017.
 */

public class MessageHolder extends RecyclerView.ViewHolder {
    public final TextView messageTextView;

    public MessageHolder(View itemView) {
        super(itemView);
        messageTextView = itemView.findViewById(R.id.message_textview);
    }

}
