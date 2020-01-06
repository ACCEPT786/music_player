package com.example.music_player.Music;

public class Music {
    private String title;
    private String artist;
    private String duration;
    private String data;
    private String size;
    private String duration_display;
    private int counter;
    public Music() {
    }

    public Music(String title, String artist, String duration, String data, String size, String duration_display, int counter) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
        this.data = data;
        this.size = size;
        this.duration_display = duration_display;
        this.counter = counter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDuration_display(){
        return duration_display;
    }

    public int getCounter(){
        return  counter;
    }
    @Override
    public String toString() {
        return "Music{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", duration='" + duration + '\'' +
                ", data='" + data + '\'' +
                ", size='" + size + '\'' +
                ", duration_display='" + duration_display + '\'' +
                ", counter='" + counter + '\'' +
                '}';
    }
}
