package ru.mirea.sevostyanov.data.storage.room;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "articles")
public class ArticleEntity {
    @PrimaryKey
    @NonNull
    private String id;
    private String title;
    private String content;
    private String date;
    private String imageUrl;

    public ArticleEntity(String id, String title, String content, String date, String imageUrl) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.imageUrl = imageUrl;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}