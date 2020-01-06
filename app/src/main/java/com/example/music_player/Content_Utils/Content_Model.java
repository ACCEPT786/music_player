package com.example.music_player.Content_Utils;

public class Content_Model {
    private int imageView;
    private String text;
    private int id;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Content_Model() {
    }

    public Content_Model(int imageView, String text, int id) {
        this.imageView = imageView;
        this.text = text;
        this.id = id;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
