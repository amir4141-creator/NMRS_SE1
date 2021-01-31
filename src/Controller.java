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
        if ( ui.getSignInButtonName().equals("sign in") ) {
            ui.showSignInPage();
        }
        // sign out
        else {
            data.setOnline(null); //This means that the customer has logged out.
            ui.getSignInPage().setSignInButtonName("sign in");
        }
    }

    //Used for the sign in button in the signInPage.
    void signIn(){
        String UserName = ui.getSignInPage().getUserName();
        String passWord = ui.getSignInPage().getPassWord();
        if(data.hasSigned(UserName,passWord)){
            Customer customer = data.getCustomer(UserName);
            ui.closeSignInPage();
            ui.setSignInButtonName("sign out");
            ui.setSubscribedPage(customer.getSubscribed());
            setupAccountBox(customer);
        }
        else if(data.hasCustomerWithName(UserName)){
            //Wrong password.
            ui.clearPasswordField();
        }
        else{
            //Customer has not signed up yet. He/She should press the sign up button.
            ui.clearUserNameField();
            ui.clearPassWordField();
        }
    }

    //Used for the sign up button in the signInPage.
    void signUp(boolean isPublisher){
        String userName = ui.getSignInPage().getUserName();
        String passWord = ui.getSignInPage().getPassWord();
        if(isPublisher)
            data.addPublisher(Publisher(userName,passWord));
        else
            data.addCustomer(Customer(userName,passWord));
        signIn();
    }

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



}
