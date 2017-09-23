package hu.muller.andris.armando.makeithappen_withfriends.Controlling;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hu.muller.andris.armando.makeithappen_withfriends.R;
import hu.muller.andris.armando.makeithappen_withfriends.model.Controlling;

/**
 * Created by Muller Andras on 9/23/2017.
 */

public class ControllingRecyclerViewAdapter extends RecyclerView.Adapter<ControllingRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "ControllingRecyclerViewAdapter";

    Context context;
    private List<Controlling> controllings = new ArrayList<>();

    public ControllingRecyclerViewAdapter(Context context){
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "ControllingRV.ViewHolder";
        TextView nameTextView;
        TextView dateDurationTextView;
        TextView websitesTextView;
        TextView appTextView;
        TextView internetTextView;
        Button startNowButton;
        Button activateButton;
        ImageButton deleteButton;
        ImageButton editButton;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.controlling_item_name_textview);
            dateDurationTextView = itemView.findViewById(R.id.controlling_item_dateandduration_textview);
            websitesTextView = itemView.findViewById(R.id.controlling_item_websites_textview);
            appTextView = itemView.findViewById(R.id.controlling_item_apps_textview);
            internetTextView = itemView.findViewById(R.id.controlling_item_internet_textview);
            startNowButton = itemView.findViewById(R.id.controlling_startnow_button);
            activateButton = itemView.findViewById(R.id.controlling_active_button);
            deleteButton = itemView.findViewById(R.id.controlling_delete_button);
            editButton = itemView.findViewById(R.id.controlling_edit_button);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.controlling_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.nameTextView.setText(controllings.get(position).getName());
        String dateDuration = context.getString(R.string.duration) + controllings.get(position).getDurationValue() + " " + controllings.get(position).getDurationUnit()
                + " - " + context.getString(R.string.date) + controllings.get(position).getStartTime();
        holder.dateDurationTextView.setText(dateDuration);
        holder.websitesTextView.setText(controllings.get(position).websitesToString());
        holder.appTextView.setText(controllings.get(position).appstoString());
        holder.internetTextView.setText(controllings.get(position).isInternet() ? context.getString(R.string.disabled) : context.getString(R.string.enabled));
        holder.startNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.startNowButton.getText().toString().equals("started")){
                    holder.startNowButton.setText("clicked again");
                } else {
                    holder.startNowButton.setText("started");
                }
                Toast.makeText(context, "start now clicked", Toast.LENGTH_LONG);
            }
        });
        holder.activateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.startNowButton.getText().toString().equals("activated")){
                    holder.startNowButton.setText("not activated");
                } else {
                    holder.startNowButton.setText("activated");
                }
                Toast.makeText(context, "activate clicked", Toast.LENGTH_LONG);
            }
        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controllings.remove(position);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return controllings.size();
    }


}
