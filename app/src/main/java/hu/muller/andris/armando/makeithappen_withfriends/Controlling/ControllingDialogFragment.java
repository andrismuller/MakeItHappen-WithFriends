package hu.muller.andris.armando.makeithappen_withfriends.Controlling;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.muller.andris.armando.makeithappen_withfriends.R;

public class ControllingDialogFragment extends DialogFragment {


    public ControllingDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_controlling_dialog, container, false);
    }

}
