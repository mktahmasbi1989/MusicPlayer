package com.example.mohamdkazem.musicplayer.model;

public class Music {

    private Long musicId;
    private String title;
    private String artistName;
    private String album;
    private String uri;
    private Long artistId;
    private Long albumId;
    private String imageUri;


    public Music(Long musicId, String title, String artistName, String album, String uri,Long artisId,String imageArtUri ) {
        this.musicId = musicId;
        this.title = title;
        this.artistName = artistName;
        this.album = album;
        this.uri=uri;
        this.artistId=artisId;
        this.imageUri=imageArtUri;

    }

    public String getImageUri() {
        return imageUri;
    }
    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
    public Long getAlbumId() {
        return albumId;
    }
    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }
    public Long getMusicId() {
        return musicId;
    }
    public Long getArtistId() {
        return artistId;
    }
    public void setArtistId(Long artistId) {
        this.artistId = artistId;
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
