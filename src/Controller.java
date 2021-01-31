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
            data.setOnline(null); //This means that the customer has logged out.
            ui.getSignInPage().setSignInButtonName("sign in");
        }
    }

    //Used for the sign in button in the signInPage.
    void signIn(String userName, String passWord){
        if(data.hasSigned(userName,passWord)){
            Customer customer = data.getCustomer(userName);
            ui.getSignInPage().close();
            ui.getSignInPage().setSignInButtonName("sign out");
            ui.setSubscribedPage(customer.getSubscribed());
            setupAccountBox(customer);
            ui.getSignInPage().clearUserNameField();
            ui.getSignInPage().clearPassWordField();
        }
        else if(data.hasCustomerWithName(userName)){
            //Wrong password.
            ui.getSignInPage().clearPasswordField();
        }
        else{
            //Customer has not signed up yet. He/She should press the sign up button.
            ui.getSignInPage().clearUserNameField();
            ui.getSignInPage().clearPassWordField();
        }
    }

    //Used for the sign up button in the signInPage.
    void signUp(String userName, String passWord, boolean isPublisher){
        if(isPublisher)
            data.addPublisher(Publisher(userName,passWord));
        else
            data.addCustomer(Customer(userName,passWord));
        signIn(userName, passWord);
    }

    //sets fields in the accountBox
    void setupAccountBox(Customer customer){
        ui.getAccountBox().setSubscribedContent(customer.getSubscribed());
        ui.getAccountBox().setMoney(customer.getMoney());
        ui.getAccountBox().setName(customer.getName());
        ui.getAccountBox().setRole( customer.instanceOf(Publisher) ? "Publisher" : "Customer" );
        if( customer.instanceOf(Publisher) ) {
            Publisher publisher = (Publisher) customer;
            ui.getAccountBox().setPublishedContent(publisher.getPublished());
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

    //used for the add button in the commentBox
    void sendComment(String comment, Content content){
        content.addComment(comment);
        ui.getCommentBox().addComment(comment);
        ui.getCommentBox().clearCommentField();
    }


}
