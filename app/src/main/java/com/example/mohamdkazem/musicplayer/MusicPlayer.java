package com.example.mohamdkazem.musicplayer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.mohamdkazem.musicplayer.model.Album;
import com.example.mohamdkazem.musicplayer.model.Artist;
import com.example.mohamdkazem.musicplayer.model.Music;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayer {

    private List<Music> musicList = new ArrayList<>();
    private List<Artist> artistList=new ArrayList<>();
    private List<Album> albumList=new ArrayList<>();

    public List<Album> getAlbumList() {
        return albumList;
    }
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
    public Music getMusic(Long musicId){
        Music music;
        for (int i = 0; i <musicList.size() ; i++) {
            if (musicList.get(i).getMusicId().equals(musicId)){
                music=musicList.get(i);
                return music;
            }
        }
        return null;
    }


    private void loadMusic() {
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, MediaStore.Audio.Media.IS_MUSIC, null, null);


        if (songCursor != null && songCursor.moveToFirst()) {
            int musicId = songCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int musicTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int musicAlbum = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int musicData = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int artistId=songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID);
            int artistName=songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int albumId=songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);

            do {
                long currentId = songCursor.getLong(musicId);
                String currentTitle = songCursor.getString(musicTitle);
                String artist = songCursor.getString(artistName);
                String album = songCursor.getString(musicAlbum);
                String url=songCursor.getString(musicData);
                long artisId=songCursor.getLong(artistId);
                String artName=songCursor.getString(artistName);
                String albumName=songCursor.getString(musicAlbum);
                Long albumid=songCursor.getLong(albumId);
                String musicArt=getMusicArtPath(albumid);

                albumList.add(new Album(albumName,albumid,artName));
                artistList.add(new Artist(artName,artisId));
                musicList.add(new Music(currentId, currentTitle, artist, album, url,artisId,musicArt));
            } while (songCursor.moveToNext());
        }
    }
    public String getMusicArtPath(Long albumId){
        Cursor cursor=mContext.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID + "=" + albumId, null, null);
        if(cursor != null && cursor.moveToFirst()){
            String imagePath= cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
            return imagePath;
        }

        return null;
    }

}
