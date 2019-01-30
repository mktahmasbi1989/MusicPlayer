package com.example.mohamdkazem.musicplayer.model;

import java.util.List;

public class Artist {


    private String artistName;
    private Long artistId;
    private String artistKey;

    public Artist(String artistName, Long artistId,String artistKey) {
        this.artistName = artistName;
        this.artistId = artistId;
        this.artistKey=artistKey;
    }



    public void setArtistKey(String artistKey) {
        this.artistKey = artistKey;
    }

    public String getArtistKey() {
        return artistKey;
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
