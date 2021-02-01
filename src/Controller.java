import models.*

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class Controller{

    private final SystemData data;
    private final SystemUi ui;

    Controller(SystemData data, SystemUi ui){
        this.ui = ui;
        this.data = data;
    }

    //Used for the button on the first page.
    void signInOut(Customer customer){
        if ( ui.getSignInPage().getSignInButtonName().equals("sign in") ) {
            ui.getSignInPage().show();
        }
        // sign out
        else {
            data.setOnline(-1); //This means that the customer has logged out.
            ui.getSignInPage().setSignInButtonName("sign in");
            ui.setInfo(null);
        }
    }

    //Used for the sign in button in the signInPage.
    void signIn(String userName, String passWord){
        if(data.userExist(userName,passWord)){ // if the user has signed and the password is correct
            Customer customer = data.getCustomer(userName);
            ui.getSignInPage().close();
            ui.setSignInButtonName("sign out");
            ui.setSubscribedPage(customer.getSubscribed());
            setupAccountBox(customer);
            ui.setInfo(customer);
            ui.getSignInPage().clearUserNameField();
            ui.getSignInPage().clearPassWordField();
        }
        else if(data.getCustomer(userName) != null){
            //Wrong password.
            ui.getSignInPage().clearPasswordField();
        }
        else{
            //Customer has not signed up yet. He/She should press the sign up button.
            ui.getSignInPage().clearUserNameField();
            ui.getSignInPage().clearPassWordField();
        }
    }

    //Used for the sign up button in signInPage.
    void signUp(String userName, String passWord, boolean isPublisher){
        if(isPublisher)
            data.addCustomer(new Publisher(userName,passWord));
        else
            data.addCustomer(new Customer(userName,passWord));
        signIn(userName, passWord);
    }

    //sets fields in accountBox
    void setupAccountBox(Customer customer){
        ui.getAccountBox().setSubscribed(customer.getSubscribed());
        ui.getAccountBox().setMoney(customer.getMoney());
        ui.getAccountBox().setName(customer.getUserName());
        if( customer instanceof Publisher ) {
            Publisher publisher = (Publisher) customer;
            ui.getAccountBox().setRole("Publisher");
            ui.getAccountBox().setPublishedContent(publisher.getPublished());
            ui.getAccountBox().enablePostButton();
        }
        else {
            ui.getAccontBox().disablePostButton();
            ui.getAccountBox().setRole("Customer");
        }
    }

    //Used for the like button below a content.
    void like(Content content){
        content.incrementLikes();
    }

    //used for the comment button below a content.
    void comment(Content content){
        ui.getCommentBox().setComments(content.getComments());
        ui.getCommentBox().clearCommentField();
        ui.getCommentBox().show();
    }

    //used for the add button in commentBox
    void sendComment(String comment, Content content){
        content.addComment(comment);
        ui.getCommentBox().addComment(comment);
        ui.getCommentBox().clearCommentField();
    }

    //used for the download button below a content.
    void downloadContent(Content content){
        Customer onlineCustomer = data.getOnlineCustomer();
        String path = "./Downloads/" + onlineCustomer.getUserName() + "/";
        File file = new File(path);
        try {
            file.mkdir();
            FileWriter writer = new FileWriter( path + content.getTitle() + ".txt");
            writer.write(content.getData());
            writer.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //used for the post button in PostPage
    void postContent(Publisher publisher, String title, String text, int price, boolean isDownloadable) {
        Content content = new Content(title, text, price, isDownloadable);
        data.publishContent(publisher, content);
        if(price == 0) {
            ui.getPublicPage().addContentBox(ContentBox(content));
        }
        else{
            ui.getPublicPage().addContentBox(ContentBox(content));
        }
    }

    //used for the post button in accountBox
    void startPosting(Publisher publisher){
        ui.getPostPage().show();
        ui.getPostPage().setPublisher(publisher);
    }

    //used for the delete account button in accountBox
    void deleteAccount(Customer customer){
        signInOut(customer);
        data.setOnline(-1);
        data.removeCustomerAccount(customer);
    }

}
