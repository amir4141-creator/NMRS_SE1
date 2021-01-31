package models;

import java.util.ArrayList;
import java.util.HashMap;

public class Customer {

    private String userName;
    private String passWord;
    private ArrayList<Content> subscribed;
    private int money;

    public Customer(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;

        this.subscribed = new ArrayList<Content>();
        this.money = 0;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public int getMoney() {
        return money;
    }

    public void addMoney(int moneyToAdd) {
        money += moneyToAdd;
    }
    boolean customerHasContent(Content content){
        for(Content content1: subscribed ){
            if( content.equals(content1) )
                return true;
        }
        return false;
    }

    void subscribe(Content content){
        if(!customerHasContent(content) && getMoney() > content.getPrice() ){
            subscribed.add(content);
        }
    }



}
