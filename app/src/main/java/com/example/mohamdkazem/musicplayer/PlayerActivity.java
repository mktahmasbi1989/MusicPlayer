package com.example.mohamdkazem.musicplayer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mohamdkazem.musicplayer.model.Music;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;

public class PlayerActivity extends SingleFragmentActivity implements ArtistFragment.OnFragmentInteractionListener ,AlbumFragment.OnFragmentInteractionListener,PlayerFragment.CallBacks{

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] tabTitles={"All","Artist","Album","Favorite"};
    private MusicPlayer musicPlayer;
    private MediaPlayer mediaPlayer;
    private Button btnPlay;

    @Override
    public Fragment createFragment() {
        return PlayerFragment.newInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        tabLayout=findViewById(R.id.tabLayout);
        viewPager=findViewById(R.id.viewPager);
        btnPlay=findViewById(R.id.activity_play_control);
        tabLayout.setupWithViewPager(viewPager);
        musicPlayer = new MusicPlayer(getApplicationContext());
        mediaPlayer=new MediaPlayer();

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
            }
        });

        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                if (position==1){
                    return ArtistFragment.newInstance();

                }if (position==2){
                    return AlbumFragment.newInstance();
                }
                else
                    return PlayerFragment.newInstance();

            }

            @Override
            public int getCount() {
                return 4;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return tabTitles[position];
            }
        });
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

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
