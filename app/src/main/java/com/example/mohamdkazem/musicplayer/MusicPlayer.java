package com.example.mohamdkazem.musicplayer;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import com.example.mohamdkazem.musicplayer.model.Music;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicPlayer {

    private static final String MUSIC_FOLDER = "chaartaar";
    private static final String TAG = "musicPlayer";
    private static final int MAX_STREAM = 5;
    private List<Music> musicList=new ArrayList<>();
    private AssetManager mAssetManeger;
    private SoundPool mSoundPool;
    private MediaPlayer mediaPlayer;
    private MediaStore mediaStore;
    private Context mContext;


    public List<Music> getMusicList() {
        return musicList;
    }

    public MusicPlayer(Context context) {
        mContext=context;
        mAssetManeger=context.getAssets();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            mSoundPool = new SoundPool.Builder()
                    .setAudioAttributes(attributes)
                    .build();
            loadMusic();
        }else {

            mSoundPool = new SoundPool(MAX_STREAM, AudioManager.STREAM_MUSIC, 0);
            loadMusic();


        }
    }


    private void loadMusic() {
        mediaPlayer=new MediaPlayer();
        try {
            String[] fileNames =  mAssetManeger.list(MUSIC_FOLDER);
            for (String fileName : fileNames) {
                String assetPath = MUSIC_FOLDER + File.separator + fileName;
                Music music = new Music(assetPath);
                musicList.add(music);

                AssetFileDescriptor afd = mAssetManeger.openFd(music.getmAssetPath());
                int soundId = mSoundPool.load(afd, 1);
                music.setMusicId(soundId);
            }
        } catch (IOException e) {
            Log.e(TAG, "file cannot be loaded", e);
        }
    }
    public void play(Music music, float rate) {
        if (music.getMusicId() == null)
            return;

        mSoundPool.play(music.getMusicId(),1.0f, 1.0f, 1, 0, rate);
    }

    public void release() {
        mSoundPool.release();


    }
}
