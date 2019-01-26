package com.example.mohamdkazem.musicplayer;

import android.support.v4.app.Fragment;
import android.os.Bundle;

public class PlayerActivity extends SingleFragmentActivity {

    @Override
    public Fragment createFragment() {
        return PlayerFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
    }


}
