package Menu;

import DAO.CustomerDAO;
import FoodGrabClasses.Customer;
import Services.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;

public class SingletonInteractiveMenu {

    static SingletonInteractiveMenu menu = new SingletonInteractiveMenu();

    private SingletonInteractiveMenu() {}
    public static SingletonInteractiveMenu getInstance()
    {
        return menu;  //I'll always get the same instance of the class
    }


    public void Start() throws SQLException {


        List<Customer> customers = new ArrayList<Customer>();

     {
            CustomerService customerService = new CustomerService();
           System.out.println(customerService.getCustomer(2).getName());
        }


    }

}
