package com.example.mohamdkazem.musicplayer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.mohamdkazem.musicplayer.model.Album;
import com.example.mohamdkazem.musicplayer.model.Artist;
import com.example.mohamdkazem.musicplayer.model.Music;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayer {

    private List<Music> musicList = new ArrayList<>();
    private List<Artist> artistList = new ArrayList<>();
    private List<Album> albumList = new ArrayList<>();

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
        getArtistsList();
        getAlbums();
    }
    public List<Music> getalbumMusicList(String aubumName){
        List<Music> musicAlbumList=new ArrayList<>();
        for (int i = 0; i <musicList.size() ; i++) {
            if (musicList.get(i).getAlbum().equals(aubumName)){
                musicAlbumList.add(musicList.get(i));
            }
        }
        return musicAlbumList;
    }
    public Music getMusic(Long musicId) {
        Music music;
        for (int i = 0; i < musicList.size(); i++) {
            if (musicList.get(i).getMusicId().equals(musicId)) {
                music = musicList.get(i);
                return music;
            }
        }
        return null;
    }

    private void getArtistsList() {
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri songUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null,null, null, null);


        if (songCursor != null && songCursor.moveToFirst()) {
            int artist_Key = songCursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST_KEY);
            int artist_Name = songCursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST);
            int artist_Id = songCursor.getColumnIndex(MediaStore.Audio.Artists._ID);
            int artist_Number_of_Albums = songCursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS);
            int artist_Number_of_trakss = songCursor.getColumnIndex(MediaStore.Audio.Artists.NUMBER_OF_TRACKS);


            do {
                long artistId = songCursor.getLong(artist_Id);
                String artName = songCursor.getString(artist_Name);
                String artistKey=songCursor.getString(artist_Key);
                String artTraks=songCursor.getString(artist_Number_of_trakss);
                String artAlbums=songCursor.getString(artist_Number_of_Albums);

                String ArtistArt = getArtistArt(artName);

                artistList.add(new Artist(artName, artistId,artistKey,artTraks,artAlbums,ArtistArt));

            } while (songCursor.moveToNext());
        }
    }
    private  void getAlbums(){
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri songUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null,null, null, null);


        if (songCursor != null && songCursor.moveToFirst()) {
            int album_Name = songCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM);
            int album_Id = songCursor.getColumnIndex(MediaStore.Audio.Albums._ID);
            int album_Art = songCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
            int album_Artist = songCursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST);

            do {
                String albumName = songCursor.getString(album_Name);
                String albumArt=songCursor.getString(album_Art);
                String albumArtist=songCursor.getString(album_Artist);
                Long albumId=songCursor.getLong(album_Id);
                albumList.add(new Album(albumName,albumId,albumArt,albumArtist));

            } while (songCursor.moveToNext());
        }
    }

    private void loadMusic() {
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, MediaStore.Audio.Media.IS_MUSIC, null, null);

        if (songCursor != null && songCursor.moveToFirst()) {
            int music_Id = songCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int music_Title = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int musicData = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int album_Id=songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            int musicAlbum = songCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int artist_Id=songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID);
            int artist_Name=songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

            do {
                long musicId = songCursor.getLong(music_Id);
                String musicTitle = songCursor.getString(music_Title);
                String url = songCursor.getString(musicData);
                long albumId = songCursor.getLong(album_Id);
                String albumName = songCursor.getString(musicAlbum);
                long artistId = songCursor.getLong(artist_Id);
                String artistName = songCursor.getString(artist_Name);
                String musicArt = getMusicArtPath(albumId);

                musicList.add(new Music(musicId,musicTitle,artistName,albumName,url,artistId,musicArt));

                }
                 while (songCursor.moveToNext());
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

    public String getArtistArt(String artistName){
        Cursor cursor=mContext.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[] {MediaStore.Audio.Albums.ARTIST, MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums.ARTIST+ "=?",
                new String[] {String.valueOf(artistName)},
                null);
        if(cursor != null && cursor.moveToFirst()){
            String imagePath= cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
            return imagePath;
        }

        return null;
    }
}
