package com.example.mohamdkazem.musicplayer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.mohamdkazem.musicplayer.model.Music;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayer {

    private List<Music> musicList = new ArrayList<>();
    private Context mContext;


    public MusicPlayer(Context context) {
        mContext = context;
        loadMusic();
    }

    public List<Music> getMusicList() {
        return musicList;
    }

    private void loadMusic() {
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null, null, null);

        if (songCursor != null && songCursor.moveToFirst()) {
            int musicId = songCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int musicTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int musicAlbum = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int musicArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int musicData = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);


            do {
                long currentId = songCursor.getLong(musicId);
                String currentTitle = songCursor.getString(musicTitle);
                String artist = songCursor.getString(musicArtist);
                String album = songCursor.getString(musicAlbum);
                String url=songCursor.getString(musicData);
                musicList.add(new Music(currentId, currentTitle, artist, album, url));
            } while (songCursor.moveToNext());
        }
    }

}
