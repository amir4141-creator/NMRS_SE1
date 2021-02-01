package models;

import java.util.ArrayList;

public class SystemData {
    private ArrayList<Customer> customers;
    private ArrayList<Publisher> publishers;

    private ArrayList<Content> storeContents;
    private ArrayList<Content> publicContents;

    private int onlineIndex;

    public SystemData() {
        customers = new ArrayList<Customer>();
        publishers = new ArrayList<Publisher>();

        storeContents = new ArrayList<Content>();
        publicContents = new ArrayList<Content>();

        onlineIndex = -1;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<Publisher> getPublishers() {
        return publishers;
    }

    public void setOnline(int onlineIndex) {
        this.onlineIndex = onlineIndex;
    }

    public Customer getOnlineCustomer() {
        return customers.get(onlineIndex);
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

    public Boolean userExist(String userName, String password){
        if (checkCustomerExist(userName, password))
            return true;
        else if(checkPublisherExist(userName, password))
            return true;
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

    public void publishContent(Publisher publisher, Content content){
        publisher.publish(content);

        if (content.getPrice() == 0){
            publicContents.add(content);
        }else {
            storeContents.add(content);
        }
    }

    public Customer getCustomer(String userName){
        for(Customer customer: customers){
            if(customer.getUserName().equals(userName))
                return customer;
        }
        return null;
    }
}