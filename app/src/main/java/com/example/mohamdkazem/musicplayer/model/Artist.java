package com.example.mohamdkazem.musicplayer.model;

public class Artist {

    private String artistName;
    private Long artistId;

    public Artist(String artistName, Long artistId) {
        this.artistName = artistName;
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
    public Long getArtistId() {
        return artistId;
    }
    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }
}
