package com.example.mohamdkazem.musicplayer;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerControlFragment extends Fragment {
    private ImageButton imageButton;


    public PlayerControlFragment() {
        // Required empty public constructor
    }

    public static PlayerControlFragment newInstance() {
        
        Bundle args = new Bundle();
        
        PlayerControlFragment fragment = new PlayerControlFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_player_control, container, false);
        imageButton=view.findViewById(R.id.btn_play);




        return view;
    }

}
