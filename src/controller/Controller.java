package controller;

import models.*;
import view.*;

import javax.swing.*;
import java.util.ArrayList;

public class Controller {

    public static void run() {
        SwingUtilities.invokeLater(new MainFrame() {
            public SystemData data;

            @Override
            protected boolean signUpActionLoginPanel(String username, String password) {  // sign up as publisher or customer??
                return false;
            }

            @Override
            protected boolean loginActionLoginPanel(String username, String password) {
                return data.userExist(username,password);
            }

            @Override
            protected int postActionPostPanel(String title, String text, int price, boolean isDownloadable, boolean isPublic) {
                Content content = new Content(title, text, price, isDownloadable);
                data.publishContent((Publisher)data.getOnlineCustomer(), content);
                return data.getLastContentIndex();
//                if(isPublic) {
//                    ui.getPublicPage().addContentBox(ContentBox(content));
//                }
//                else{
//                    ui.getPublicPage().addContentBox(ContentBox(content));
//                }
            }

            @Override
            protected void deleteActionProfilePanel() {
                data.setOnline(-1);
                data.removeCustomerAccount(data.getOnlineCustomer());
            }

            @Override
            protected boolean changeNameActionProfilePanel(String nawName) {
                if(data.userExist(nawName))
                    return false;
                data.getOnlineCustomer().changeName(nawName);
                return true;
            }

            @Override
            protected boolean addMoneyActionProfilePanel(int changeMoneyAmount) {
                if( changeMoneyAmount<0 )
                    return false;
                data.getOnlineCustomer().addMoney(changeMoneyAmount);
            }

            @Override
            protected String getNameProfilePanel() {
                return data.getOnlineCustomer().getUserName();
            }

            @Override
            protected int getMoneyProfilePanel() {
                return data.getOnlineCustomer().getMoney();
            }

            @Override
            protected Role getRoleProfilePanel() {
                if ( data.getOnlineCustomer() instanceof Publisher )
                    return Role.PUBLISHER;
                return Role.CUSTOMER;
            }

            @Override
            protected String[] getPublishersNameArraySubscribePanel() {
                String[] publishersName = new String[data.getPublishers().size()];
                for(int i=0; i<data.getPublishers().size(); i++){
                    publishersName[i] = data.getPublishers().get(i).getUserName();
                }
                return publishersName;
            }

            @Override
            protected void setSubscribedPublishersSubscribePanel(ArrayList<String> selectedPublisherNames) {

            }

            @Override
            protected String getTitle(int index) {
                return data.getContents().get(index).getTitle();
            }

            @Override
            protected String getContent(int index) {
                return data.getContents().get(index).getData();
            }

            @Override
            protected int getLikeNumber(int index) {
                return 0;
            }

            @Override
            protected boolean isDownloadable(int index) {
                return false;
            }

            @Override
            protected ArrayList<Integer> getMagazineIndexList() {
                return null;
            }

            @Override
            protected void logoutButtonAction() {

            }

            @Override
            protected void downloadButtonAction(int index) {

            }

            @Override
            protected ArrayList<String[]> getCommentByIndex(int index) {
                return null;
            }

            @Override
            protected boolean addComment(int magazineIndex, String[] newComment) {
                return false;
            }

            @Override
            protected void incrementLikeByIndex(int index) {

            }

            @Override
            protected void decrementLikeByIndex(int index) {

            }
        });
    }
}
