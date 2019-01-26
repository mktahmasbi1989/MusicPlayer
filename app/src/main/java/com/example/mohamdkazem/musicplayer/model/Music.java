package com.example.mohamdkazem.musicplayer.model;

public class Music {

    private Integer musicId;
    private String title;
    private String artistName;
    private String mAssetPath;
    private String album;

    public Music(String mAssetPath) {
        this.mAssetPath = mAssetPath;
        String[] splits = mAssetPath.split("/");
        String fileName = splits[splits.length - 1];
        title = fileName.substring(0, fileName.lastIndexOf("."));

    }

    public Integer getMusicId() {
        return musicId;
    }
    public void setMusicId(Integer musicId) {
        this.musicId = musicId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getArtistName() {
        return artistName;
    }
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
    public String getmAssetPath() {
        return mAssetPath;
    }
    public void setmAssetPath(String mAssetPath) {
        this.mAssetPath = mAssetPath;
    }
    public String getAlbum() {
        return album;
    }
    public void setAlbum(String album) {
        this.album = album;
    }
}
