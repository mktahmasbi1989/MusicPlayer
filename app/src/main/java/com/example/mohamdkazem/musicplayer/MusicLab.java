package com.example.mohamdkazem.musicplayer;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.mohamdkazem.musicplayer.model.Album;
import com.example.mohamdkazem.musicplayer.model.Artist;
import com.example.mohamdkazem.musicplayer.model.Music;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MusicLab {

    private static MusicLab ourInstance ;

    private MediaPlayer mediaPlayer;
    private Context mContext;
    private List<Music> musicList=new ArrayList<>() ;
    private List<Artist> artistList =new ArrayList<>();
    private List<Album> albumList=new ArrayList<>() ;
    private Long currentId;
    private Music currentMusic;



    public static MusicLab getInstance(Context context) {
        if (ourInstance==null){
            ourInstance=new MusicLab(context);
        }
        return ourInstance;
    }

    private MusicLab(Context context) {
        mContext=context;
        mediaPlayer=new MediaPlayer();
        loadMusic();
        getArtistsList();
        getAlbums();

    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public List<Album> getAlbumList() {
        return albumList;
    }
    public List<Music> getMusicList() {
        return musicList;
    }
    public List<Artist> getArtistList() {
        return artistList;
    }
    public Long getCurrentId() {
        return currentId;
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

    public List<Music> getAlbumMusicList(String albumName){
        List<Music> musicAlbumList=new ArrayList<>();
        for (int i = 0; i <musicList.size() ; i++) {
            if (musicList.get(i).getAlbum().equals(albumName)){
                musicAlbumList.add(musicList.get(i));
            }
        }
        return musicAlbumList;
    }

    public List<Music> getArtistMusicList(String artistName){
        List<Music> artistMusicList=new ArrayList<>();
        for (int i = 0; i <musicList.size() ; i++) {
            if (musicList.get(i).getArtistName().equals(artistName)){
                artistMusicList.add(musicList.get(i));
            }
        }
        return artistMusicList;
    }

    public void  playMusic(Long MusicId,Context context){

            Music music = getMusic(MusicId);
            currentMusic=music;
            currentId=MusicId;
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(context, Uri.parse(music.getUri()));
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }

    }
    public void PlayAndPause(){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }else  mediaPlayer.start();
    }

    public void nextMusic(){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.reset();
            MusicLab.getInstance(mContext).playMusic(currentId+1,mContext);
        }
    }

    public void previous(){
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.reset();
            MusicLab.getInstance(mContext).playMusic(currentId-1,mContext);
        }
    }

    public boolean chechPlay(){
        if (mediaPlayer.isPlaying()){
            return true;
        }else return false;
    }

    public String setTotalDuration() {
        int duration = MusicLab.getInstance(mContext).getMediaPlayer().getDuration();
        String currTime = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
        return currTime;
    }

    public void repeateMusic(){

    }



}
