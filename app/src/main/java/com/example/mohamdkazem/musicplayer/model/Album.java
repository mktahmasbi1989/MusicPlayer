package com.example.mohamdkazem.musicplayer.model;

public class Album {
    private String albumName;
    private Long albumId;
    private String albumArt;
    private String albumArtist;
    private String artistId;


    public Album(String albumName, Long albumId, String albumArt,String albumArtist) {
        this.albumName = albumName;
        this.albumId = albumId;
        this.albumArt = albumArt;
        this.albumArtist=albumArtist;

    }


    String getArtistId() {
        return artistId;
    }
    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }
    public String getAlbumArtistKey() {
        return albumArtist;
    }
    public String getAlbumArt() {
        return albumArt;
    }
    public void setAlbumArt(String albumArt) {
        this.albumArt = albumArt;
    }
    public void setAlbumArtistKey(String albumArtistKey) {
        this.albumArtist = albumArtistKey;
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
        return albumArt;
    }
    public void setAlbumArtistName(String albumArtistName) {
        this.albumArt = albumArtistName;
    }


}
