package models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Content {
    private String title;
    private String data;
    private int price;
    private Boolean isDownloadable;
    private ArrayList<String> comments;
    private int likes;
    private Date creationDate;

    public Content(String title, String data, int price, Boolean isDownloadable) {
        this.title = title;
        this.data = data;
        this.price = price;
        this.isDownloadable = isDownloadable;
        this.comments = new ArrayList<String>();
        this.likes = 0;
        this.creationDate = new Date();
    }

    public String getTitle() {
        return title;
    }

    public String getData() {
        return data;
    }

    public int getPrice() {
        return price;
    }

    public Boolean getDownloadable() {
        return isDownloadable;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public int getLikes() {
        return likes;
    }

    public void addComment(String newComment) {
        comments.add(newComment);
    }

    public void incrementLikes() {
        likes++;
    }
}
