package com.example.adhit.bikubiku.ui.panelkabim.kabimsettings;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adhit.bikubiku.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class KabimSettingsFragment extends Fragment {


    public KabimSettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kabim_settings, container, false);
    }

}
