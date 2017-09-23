package hu.muller.andris.armando.makeithappen_withfriends.Controlling;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import hu.muller.andris.armando.makeithappen_withfriends.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ControllingFragment extends Fragment {
    private static final String TAG = "ControllingFragment";

    private TextView infoTextView;
    private FloatingActionButton addControllingFab;
    private RecyclerView controllingRescyclerView;
    private ControllingRecyclerViewAdapter adapter;

    public static ControllingFragment newInstance(){
        ControllingFragment fragment = new ControllingFragment();
        return fragment;
    }

    public ControllingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_controlling, container, false);

        infoTextView = view.findViewById(R.id.controlling_information_textview);
        addControllingFab = view.findViewById(R.id.new_controlling_action_button);
        addControllingFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        controllingRescyclerView = view.findViewById(R.id.controlling_recyclerview);
        adapter = new ControllingRecyclerViewAdapter(getContext());
        controllingRescyclerView.setAdapter(adapter);

        return view;
    }

}
