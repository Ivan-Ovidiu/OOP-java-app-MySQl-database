package Menu;

import DAO.CustomerDAO;
import FoodGrabClasses.Customer;
import Services.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.SQLException;
import java.util.Scanner;

public class SingletonInteractiveMenu {

    private CustomerService customerService;

    static SingletonInteractiveMenu menu;

    static {
        try {
            menu = new SingletonInteractiveMenu();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private SingletonInteractiveMenu() throws SQLException {
        customerService = new CustomerService();
    }
    public static SingletonInteractiveMenu getInstance()
    {
        return menu;  //I'll always get the same instance of the class
    }


    public void Start(Scanner scanner) throws SQLException {
        System.out.println("FOODGRAB");
        while(true) {
            System.out.println("1)LOGIN");
            System.out.println("2)Create account");
            String choice1 = scanner.nextLine();
            switch(choice1)
            {
                case "1":
                    if(customerService.loginUser(scanner))
                        break;
                    else
                        continue;
                case "2":
                    customerService.createCustomer(scanner);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
            while (true) {
                System.out.println("Select an option" +
                        "\n1)Edit profile" +
                        "\n2) Exit");

                String choice2 = scanner.nextLine();
                switch (choice2) {
                    case "1":
                       customerService.displayProfile(scanner);
                       break;
                    case "2":
                        break;
                    default:
                        System.out.println("Invalid choice");
                }


            }
        }



    }

}
