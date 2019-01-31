package com.example.mohamdkazem.musicplayer;


import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.mohamdkazem.musicplayer.model.Music;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import androidx.fragment.app.Fragment;

public class PlayerActivity extends SingleFragmentActivity implements AllMusicFragment.CallBacks {


    private MusicPlayer musicPlayer;
    private MediaPlayer mediaPlayer;
    private ImageButton btnPlay;
    private TextView textTotalDurataion, textDuration;
    private SeekBar mSeekBar;
    private int duration;

    private Handler mSeekbarUpdateHandler = new Handler();

    private Runnable mUpdateSeekbar = new Runnable() {
        @Override
        public void run() {
            mSeekBar.setProgress(mediaPlayer.getCurrentPosition());
            mSeekbarUpdateHandler.postDelayed(this, 50);
            setCurrentDuration();
        }
    };

    private void setTotalDuration() {
        duration = mediaPlayer.getDuration();
        String currTime = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
        textTotalDurataion.setText(currTime);
    }

    private void setCurrentDuration() {
        duration = mediaPlayer.getCurrentPosition();
        String currTime = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
        textDuration.setText(currTime);
    }


    @Override
    public Fragment createFragment() {
        return PlayerFragment.newInstance();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        textTotalDurataion = findViewById(R.id.seek_bar_total_duration);
        textDuration = findViewById(R.id.seek_bar_duration);
        mSeekBar = findViewById(R.id.seekBar_activity);
        btnPlay = findViewById(R.id.btn_play_control);
        musicPlayer = new MusicPlayer(getApplicationContext());
        mediaPlayer = new MediaPlayer();

//                FragmentManager fragmentManager=getSupportFragmentManager();
//                fragmentManager.beginTransaction().add(R.id.activity_player,PlayerControlFragment.newInstance(),"control").commit();


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
            mediaPlayer = new MediaPlayer();
            Music music = musicPlayer.getMusic(musicId);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(music.getUri()));
            mediaPlayer.prepare();
            mediaPlayer.start();

            setTotalDuration();
            mSeekBar.setMax(mediaPlayer.getDuration());
            mUpdateSeekbar.run();
            mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        mSeekBar.setProgress(progress);
                        mediaPlayer.seekTo(progress);
                        mUpdateSeekbar.run();
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

        } catch (IllegalArgumentException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
