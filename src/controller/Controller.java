package controller;

import models.*;
import view.*;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller {
    public final static SystemData data;

    static {
        data = new SystemData();
    }

    public static void run() {
        SwingUtilities.invokeLater(new MainFrame() {


            @Override
            protected boolean signUpActionLoginPanel(String username, String password, Role role) {
                if (data.userExist(username))
                    return false;

                if (role == Role.PUBLISHER)
                    data.addCustomer(new Publisher(username, password));
                else
                    data.addCustomer(new Customer(username, password));
                loginActionLoginPanel(username, password);
                return true;
            }

            @Override
            protected boolean loginActionLoginPanel(String username, String password) {
                if (data.userExist(username, password)) {
                    data.setOnline(data.getCustomer(username));
                    return true;
                }
                return false;
            }

            @Override
            protected int postActionPostPanel(String title, String content, int price,
                                              boolean isDownloadable, boolean isPublic) {
                Content contentPost = new Content(title, content, price, isDownloadable);
                data.publishContent((Publisher) data.getOnlineCustomer(), contentPost);
                return data.getLastContentIndex();
            }

            @Override
            protected void deleteActionProfilePanel() {
                data.setOnline(null);
                data.removeCustomerAccount(data.getOnlineCustomer());
            }

            @Override
            protected boolean changeNameActionProfilePanel(String nawName) {
                if (data.userExist(nawName))
                    return false;
                data.getOnlineCustomer().changeName(nawName);
                return true;
            }

            @Override
            protected boolean addMoneyActionProfilePanel(int changeMoneyAmount) {
                if (changeMoneyAmount < 0)
                    return false;
                data.getOnlineCustomer().addMoney(changeMoneyAmount);
                return true;
            }

            @Override
            protected String getNameProfilePanel() {
                return data.getOnlineCustomer().getUserName();
            }

            @Override
            protected int getMoneyProfilePanel() {
                return data.getOnlineCustomer().getMoney();
            }

            //todo : AMIR : there is bug here i think
            @Override
            protected Role getRoleProfilePanel() {
//                if(data.getOnlineIndex() <0){
//                    return null;
//                }else {
                if (data.getOnlineCustomer() instanceof Publisher)
                    return Role.PUBLISHER;
//                }

                return Role.CUSTOMER;
            }

            @Override
            protected ArrayList<String> getPublishedMagazineProfilePanel() {
                Publisher publisher;
                if (data.getOnlineCustomer() instanceof Publisher)
                    publisher = (Publisher) data.getOnlineCustomer();
                else
                    return new ArrayList<>();
                ArrayList<String> titles = new ArrayList<>();
                for (Content content : publisher.getPublished()) {
                    titles.add(content.getTitle());
                }
                return titles;
            }

            @Override
            protected ArrayList<String> getSubscribedMagazineProfilePanel() {
                ArrayList<String> titles = new ArrayList<>();
                for (Content content : data.getOnlineCustomer().getSubscribed()) {
                    titles.add(content.getTitle());
                }
                return titles;
            }

            @Override
            protected HashMap<String, Integer> getMagazineInfoArraySubscribePanel() {
                HashMap<String, Integer> map = new HashMap<>();
                for (Content content : data.getContents()){
                    if(content.getPrice() !=0){
                        map.put(content.getTitle(),content.getPrice());
                    }
                }
                return map;
            }

            @Override
            protected void setSubscribedPublishersSubscribePanel(ArrayList<String> selectedPublisherNames) {
                for (String title: selectedPublisherNames){
                    data.getOnlineCustomer().subscribe(data.getContentByTitle(title));
                }
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
                return data.getContents().get(index).getLikes();
            }

            @Override
            protected boolean isDownloadable(int index) {
                return data.getContents().get(index).getDownloadable();
            }

            @Override
            protected ArrayList<Integer> getMagazineIndexList() {
                ArrayList<Integer> indexes = new ArrayList<>();
                for (int i = 0; i < data.getContents().size(); i++) {
                    indexes.add(i);
                }
                return indexes;
            }

            @Override
            protected void logoutButtonAction() {
                data.setOnline(null);
            }

            @Override
            protected void downloadButtonAction(int index) {
                String path = ".\\Downloads\\" + data.getOnlineCustomer().getUserName() + "\\";
                File file = new File(path);
                try {
                    file.mkdirs();
                    FileWriter writer = new FileWriter(path + data.getContents().get(index).getTitle() + ".txt");
                    writer.write(data.getContents().get(index).getData());
                    writer.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected ArrayList<String[]> getCommentByIndex(int index) {
                return data.getContents().get(index).getComments();
            }

            @Override
            protected boolean addComment(int magazineIndex, String[] newComment) {
                data.getContents().get(magazineIndex).getComments().add(newComment);
                return true;
            }

            @Override
            protected void incrementLikeByIndex(int index) {
                data.getContents().get(index).incrementLikes();
            }

            @Override
            protected void decrementLikeByIndex(int index) {
                data.getContents().get(index).decrementLikes();
            }
        });
    }
}
