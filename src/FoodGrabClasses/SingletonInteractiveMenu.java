package FoodGrabClasses;

import Statements.CustomerStatement;
import Statements.RestaurantStatement;
import Statements.UserStatements;

import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class SingletonInteractiveMenu {
    static SingletonInteractiveMenu menu = new SingletonInteractiveMenu();
    private SingletonInteractiveMenu() {}
    public static SingletonInteractiveMenu getInstance()
    {
        return menu;
    }//I'll always get the saem instance of the class

    public void Start() throws SQLException {


//Variables
        List<Customer> customers = new ArrayList<Customer>();

     {
            CustomerStatement customerStatement = new CustomerStatement();
            UserStatements userStatements = new UserStatements();
            List<Integer> Ids = customerStatement.getCustomerId();
            int customerNumber = customerStatement.getNumberOfCustomers();
            System.out.println(customerNumber);
            for (Integer id : Ids)
            {
                Customer customer = new Customer(userStatements.getUserInfo(id).name,userStatements.getUserInfo(id).email,
                        userStatements.getUserInfo(id).phoneNumber,userStatements.getUserInfo(id).password,customerStatement.getLoyaltyPoints(id),customerStatement.getAllOrders(id),
                        customerStatement.getAllFavouriteRestaurants(id));
                customers.add(customer);
            }
        }
    }

}
