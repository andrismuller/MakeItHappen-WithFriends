package hu.muller.andris.armando.makeithappen_withfriends.Homescreen.applications;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import hu.muller.andris.armando.makeithappen_withfriends.R;
import hu.muller.andris.armando.makeithappen_withfriends.model.ApplicationInfo;

/**
 * Created by Muller Andras on 9/27/2017.
 */

public class HomescreenAppsAdapter extends RecyclerView.Adapter<HomescreenAppsAdapter.ViewHolder> {

    private Activity activity;
    private final List<ApplicationInfo> homescreenAppList;
    private LayoutInflater mInflater;

    public HomescreenAppsAdapter(Activity activity, List<ApplicationInfo> applications) {
        this.mInflater = LayoutInflater.from(activity);
        this.activity = activity;
        this.homescreenAppList = applications;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.app_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ApplicationInfo app = homescreenAppList.get(position);
        holder.nameTV.setText(app.getTitle());
        holder.iconIV.setImageDrawable(app.getIcon());

        holder.applicationsLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(app.getIntent());
//                Intent launchIntent = activity.getPackageManager().getLaunchIntentForPackage(app.);
//                if (launchIntent != null) {
//                    activity.startActivity(launchIntent);
//                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return homescreenAppList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout applicationsLL;
        public TextView nameTV;
        public ImageView iconIV;

        public ViewHolder(View itemView) {
            super(itemView);
            applicationsLL = itemView.findViewById(R.id.applicationLL);
            nameTV = itemView.findViewById(R.id.nameTV);
            iconIV = itemView.findViewById(R.id.iconIV);
        }
    }
}
