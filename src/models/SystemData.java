package models;

import java.util.ArrayList;

public class SystemData {
    private ArrayList<Customer> customers;
    private ArrayList<Publisher> publishers;

    private ArrayList<Content> contents;


    private Customer onlineCustomer;

    public SystemData() {
        customers = new ArrayList<Customer>();
        publishers = new ArrayList<Publisher>();

        contents = new ArrayList<Content>();
        onlineCustomer = null;
    }



    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<Publisher> getPublishers() {
        return publishers;
    }

    public void setOnline(Customer onlineCustomer) {
        this.onlineCustomer = onlineCustomer;
    }


    public Customer getOnlineCustomer() {
        return onlineCustomer;

    }

    public void addCustomer(Customer customerToAdd) {
        if (customerToAdd instanceof Publisher) {
            publishers.add((Publisher) customerToAdd);
            return;
        }
        customers.add(customerToAdd);
    }

    public void removeCustomerAccount(Customer customerToDelete) {
        if (customerToDelete instanceof Publisher) {
            publishers.remove((Publisher) customerToDelete);
            return;
        }
        customers.remove(customerToDelete);
    }

    public Boolean userExist(String userName, String password) {
        if (checkCustomerExist(userName, password))
            return true;
        else if (checkPublisherExist(userName, password))
            return true;
        return false;
    }

    public boolean userExist(String name) {
        for (Customer customer : customers) {
            if (customer.getUserName().equals(name))
                return true;
        }
        for (Publisher publisher : publishers) {
            if (publisher.getUserName().equals(name))
                return true;
        }
        return false;
    }

    public Boolean checkCustomerExist(String userName, String passWord) {
        for (Customer customer : customers) {
            if (customer.getUserName().equals(userName) && customer.getPassWord().equals(passWord)) {
                return true;
            }
        }
        return false;
    }

    public Boolean checkPublisherExist(String userName, String passWord) {
        for (Publisher publisher : publishers) {
            if (publisher.getUserName().equals(userName) && publisher.getPassWord().equals(passWord)) {
                return true;
            }
        }
        return false;
    }

    public void publishContent(Publisher publisher, Content content) {
        publisher.publish(content);
        contents.add(content);
//        if (content.getPrice() == 0){
//            publicContents.add(content);
//        }else {
//            storeContents.add(content);
//        }
    }

    public Customer getCustomer(String userName) {
        for (Customer customer : customers) {
            if (customer.getUserName().equals(userName))
                return customer;
        }
        for (Publisher publisher : publishers) {
            if (publisher.getUserName().equals(userName))
                return publisher;
        }
        return null;
    }

    public int getLastContentIndex() {
        return contents.size() - 1;
    }


    public ArrayList<Content> getContents() {
        return contents;
    }

    public Content getContentByTitle(String title){
        for (Content content: contents){
            if(content.getTitle().equals(title)){
                return content;
            }
        }
        return null;
    }
}