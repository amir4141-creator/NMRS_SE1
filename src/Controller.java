import java.util.ArrayList;


/* Methods used from model:
data.getCustomers(),
data.getPublishers(),
data.addCustomer(),
data.addPublisher(),
data.setOnline(),
data.remove(),
data.setOnline(),
data.getOnline(),

customer.getSubscribed(),
customer.addMoney(),
customer.getMoney(),

publisher.addPublished(),

content.getPrice(),
*/
public class Controller{

    private final SystemData data;
    private final SystemUi ui;

    Controller(SystemData data, SystemUi ui){
        this.ui = ui;
        this.data = data;
    }

    ArrayList<Customer> getCustomers(){
        return data.getCustomers();
    }

    ArrayList<Publisher> getPublishers(){
        return data.getPublishers();
    }

    //This is equivalent of sign up.
    void addCustomer(Customer customer){
//        getCustomers().add(customer);
        data.addCustomer(customer);
    }

    void addPublisher(Publisher publisher){
//        getPublishers().add(publisher);
        data.addPublisher(publisher);
    }

    //This is equivalent of sign in.Just sets the online customer.
    void signIn(Customer customer){
        data.setOnline(customer);
    }

    //This means that there is no online person.
    void signOut(Customer customer){
        if(data.getOnline().equals(customer))
            data.setOnline(NULL);
    }

    void removeAccount(Customer customer){
        data.remove(customer);
    }

    boolean customerExists(Customer customer){
        for (Customer customer1: getCustomers()) {
            if(customer.equals(customer1) )
                return true;
        }
        return false;
    }

    boolean customerHasContent(Customer customer, Content content){
        for(Content content1: customer.getSubscribed() ){
            if( content.equals(content1) )
                return true;
        }
        return false;
    }

    //Adds a content to a customer's subscribed contents
    void subscribe(Customer customer, Content content){
        if(!customerHasContent(customer, content) && customer.getMoney() > content.getPrice() ){
            customer.addSubscribed(content);
            customer.pay(content.getPrice());
        }
    }

    void addMoney(Customer customer, int money){
        if(customerExists(customer))
            customer.addMoney(money);
    }

    void postContent(Publisher publisher, Content content){
        publisher.addPublished(content);
        if(content.getPrice() == 0){
            data.getPublicContents()
        }
    }

}
