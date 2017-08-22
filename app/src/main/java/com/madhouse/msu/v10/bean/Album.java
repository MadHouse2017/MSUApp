package com.madhouse.msu.v10.bean;

/**
 * Created by Krishna on 5/2/2017.
 */
public class Album {
    private String name;
    private String numOfSongs;
    private int thumbnail;
    private int thumbnail1;
    private int thumbnail2;
    private int thumbnail3;
    private int thumbnail4;
    private int thumbnail5;

    public Album() {
    }

    public Album(String name, String numOfSongs, int thumbnail, int thumbnail1, int thumbnail2, int thumbnail3, int thumbnail4, int thumbnail5) {
        this.name = name;
        this.numOfSongs = numOfSongs;
        this.thumbnail = thumbnail;
        this.thumbnail1 = thumbnail1;
        this.thumbnail2 = thumbnail2;
        this.thumbnail3 = thumbnail3;
        this.thumbnail4 = thumbnail4;
        this.thumbnail5 = thumbnail5;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumOfSongs() {
        return numOfSongs;
    }

    public void setNumOfSongs(String numOfSongs) {
        this.numOfSongs = numOfSongs;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getThumbnail1() {
        return thumbnail1;
    }

    public void setThumbnail1(int thumbnail1) {
        this.thumbnail1 = thumbnail1;
    }

    public int getThumbnail2() {
        return thumbnail2;
    }

    public void setThumbnail2(int thumbnail2) {
        this.thumbnail2 = thumbnail2;
    }

    public int getThumbnail3() {
        return thumbnail3;
    }

    public void setThumbnail3(int thumbnail3) {
        this.thumbnail3 = thumbnail3;
    }

    public int getThumbnail4() {
        return thumbnail4;
    }

    public void setThumbnail4(int thumbnail4) {
        this.thumbnail4 = thumbnail4;
    }

    public int getThumbnail5() {
        return thumbnail5;
    }

    public void setThumbnail5(int thumbnail5) {
        this.thumbnail5 = thumbnail5;
    }
}