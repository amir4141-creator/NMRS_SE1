package models;

import java.util.ArrayList;

public class Publisher extends Customer{
    private ArrayList<Content> published;
    public Publisher(String userName , String passWord) {
        super(userName,passWord);
    }

    public void publish(Content newContent){
        published.add(newContent);
    }
}
