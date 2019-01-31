package com.example.mohamdkazem.musicplayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.mohamdkazem.musicplayer.model.Music;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;

public class PlayerActivity extends SingleFragmentActivity implements AllMusicFragment.CallBacks {


    private MusicPlayer musicPlayer;
    private MediaPlayer mediaPlayer;
    private ImageButton btnPlay;
    private ConstraintLayout constraintLayout;


    @Override
    public Fragment createFragment() {
        return PlayerFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        btnPlay = findViewById(R.id.btn_play);
        constraintLayout=findViewById(R.id.player_controller);
        musicPlayer = new MusicPlayer(getApplicationContext());
        mediaPlayer = new MediaPlayer();
//
//        constraintLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FragmentManager fragmentManager=getSupportFragmentManager();
//                fragmentManager.beginTransaction().add(R.id.activity_player,PlayerControlFragment.newInstance(),"control").commit();
//            }
//        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
            }
        });

    }

    @Override
    public void playMusic(Long musicId) {
        try {
            mediaPlayer=new MediaPlayer();
            Music music=musicPlayer.getMusic(musicId);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(music.getUri()));
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
