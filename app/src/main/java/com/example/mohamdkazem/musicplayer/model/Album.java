package com.example.mohamdkazem.musicplayer.model;

public class Album {
    private String albumName;
    private Long albumId;
    private String albumArtistName;


    public Album(String albumName, Long albumId, String albumArtistName) {
        this.albumName = albumName;
        this.albumId = albumId;
        this.albumArtistName = albumArtistName;
    }

    public String getAlbumName() {
        return albumName;
    }
    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
    public Long getAlbumId() {
        return albumId;
    }
    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }
    public String getAlbumArtistName() {
        return albumArtistName;
    }
    public void setAlbumArtistName(String albumArtistName) {
        this.albumArtistName = albumArtistName;
    }
}
