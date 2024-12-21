package ru.mirea.sevostyanov.domain.models;

public class Articles {
    private String id;
    private String title;
    private String content;
    private String date;
    private String imageUrl;

    public Articles(String id, String title, String content, String date, String imageUrl) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.imageUrl = imageUrl;
    }


    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public String getDate() { return date; }
    public String getImageUrl() { return imageUrl; }
}