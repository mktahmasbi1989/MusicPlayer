package com.example.mohamdkazem.musicplayer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.mohamdkazem.musicplayer.model.Artist;
import com.example.mohamdkazem.musicplayer.model.Music;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayer {

    private List<Music> musicList = new ArrayList<>();
    private List<Artist> artistList=new ArrayList<>();


    public List<Music> getMusicList() {
        return musicList;
    }
    public List<Artist> getArtistList() {
        return artistList;
    }

    private Context mContext;


    public MusicPlayer(Context context) {
        mContext = context;
        loadMusic();
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
            int artistId=songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID);
            int artistName=songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

            do {
                long currentId = songCursor.getLong(musicId);
                String currentTitle = songCursor.getString(musicTitle);
                String artist = songCursor.getString(musicArtist);
                String album = songCursor.getString(musicAlbum);
                String url=songCursor.getString(musicData);
                long artisId=songCursor.getLong(artistId);
                String artName=songCursor.getString(artistName);
                artistList.add(new Artist(artName,artisId));
                musicList.add(new Music(currentId, currentTitle, artist, album, url));
            } while (songCursor.moveToNext());
        }
    }

}
