package com.example.mohamdkazem.musicplayer.model;


public class Music {

    private Long musicId;
    private String title;
    private String artistName;
    private String album;
    private String uri;


    public Music(Long musicId, String title, String artistName, String album,String uri) {
        this.musicId = musicId;
        this.title = title;
        this.artistName = artistName;
        this.album = album;
        this.uri=uri;
    }

    public Long getMusicId() {
        return musicId;
    }
    public void setMusicId(Long musicId) {
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
    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
    public String getAlbum() {
        return album;
    }
    public void setAlbum(String album) {
        this.album = album;
    }
}
