package com.example.mohamdkazem.musicplayer.model;

public class Artist {


    private String artistName;
    private Long artistId;
    private String artistKey;
    private String numberOfTraks;
    private String numberOfAlbums;
    private String artistArtPath;


    public Artist(String artistName, Long artistId, String artistKey,String numTraks,String numAlbums,String artistAtPath) {
        this.artistName = artistName;
        this.artistId = artistId;
        this.artistKey=artistKey;
        this.numberOfTraks=numTraks;
        this.numberOfAlbums=numAlbums;
        this.artistArtPath=artistAtPath;
    }


    public void setArtistArtPath(String artistArtPath) {
        this.artistArtPath = artistArtPath;
    }
    public String getArtistArtPath() {
        return artistArtPath;
    }
    public String getNumberOfTraks() {
        return numberOfTraks;
    }
    public void setNumberOfTraks(String numberOfTraks) {
        this.numberOfTraks = numberOfTraks;
    }
    public String getNumberOfAlbums() {
        return numberOfAlbums;
    }
    public void setNumberOfAlbums(String numberOfAlbums) {
        numberOfAlbums = numberOfAlbums;
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
