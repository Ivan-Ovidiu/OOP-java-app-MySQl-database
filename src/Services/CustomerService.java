package Services;
import DAO.UserDAO;
import DAOServices.CustomerDAOServices;
import FoodGrabClasses.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CustomerService {
    private CustomerDAOServices customerDAOservices;
    UserDAO userDAO ;
    public CustomerService() throws SQLException {
        customerDAOservices = new CustomerDAOServices();
        userDAO = new UserDAO();
    }



//Creating and returning a customer based on a given id, from the database
    public void createCustomer(Scanner scanner) throws SQLException {
        System.out.println("Enter Name: ");
        String name = scanner.nextLine();
        System.out.println("Enter Email: ");
        String email = scanner.nextLine();
        System.out.println("Enter Customer Phone: ");
        String phone = scanner.nextLine();
        System.out.println("Enter Customer Password: ");
        String password = scanner.nextLine();

        Customer customer = new Customer();

        customer.setName(name);
        customer.setEmail(email);
        customer.setPassword(password);
        customer.setPhoneNumber(phone);
        customerDAOservices.createCustomer(customer);
    }

    public boolean loginUser(Scanner scanner) throws SQLException {
        System.out.println("Enter Username: ");
        String username = scanner.nextLine();
        System.out.println("Enter Password: ");
        String password = scanner.nextLine();

        if(customerDAOservices.loginChecker(username,password)) {
            System.out.println("Login Successful");
            return true;
        }
        else {
            System.out.println("Username or password is incorrect");
            return false;
        }
    }


    public void editProfile(Scanner scanner) throws SQLException {
        System.out.println("All available customer Ids:");
        System.out.println(customerDAOservices.getAllExistingIds());

        int id = scanner.nextInt();
        scanner.nextLine();
        customerDAOservices.displayProfile(id);
        Customer customer = customerDAOservices.getCustomer(id);

        while(true) {
            System.out.println("Choose what to edit:\n" +
                               "1 - name\n" +
                               "2 - email\n"  +
                                "3 - phone number\n" +
                                "4 - password\n" +
                                " --> ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("New name: ");
                    String name = scanner.nextLine();
                    customer.setName(name);
                    break;
                case "2":
                    System.out.println("New email:");
                    String email = scanner.nextLine();
                    customer.setEmail(email);
                    break;
                case "3":
                    System.out.println("New phone number:");
                    String phone = scanner.nextLine();
                    customer.setPhoneNumber(phone);
                    break;
                case "4":
                    System.out.println("New password: ");
                    String password = scanner.nextLine();
                    customer.setPassword(password);
                    break;
                case "5":
                    customerDAOservices.editCustomer(customer);
                    return; // exit the method
                default:
                    System.out.println("Invalid choice, try again.");
            }
            customerDAOservices.editCustomer(customer);
        }
    }

    public void deleteCustomer(Scanner scanner) throws SQLException {
        System.out.println("All available customer Ids:");
        System.out.println(customerDAOservices.getAllExistingIds());
        System.out.println("Enter the customer's id you want to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        customerDAOservices.deleteCustomer(id);
        System.out.println("Customer deleted successfully");
    }

    public void displayFoodList(Scanner scanner) throws SQLException {
        System.out.println("All available customer Ids:");
        System.out.println(customerDAOservices.getAllExistingIds());

        int id = scanner.nextInt();
        scanner.nextLine();
        List<Map.Entry<Food, Integer>> sortedFood = customerDAOservices.sortFoodByPrice(id);
        for (Map.Entry<Food, Integer> entry : sortedFood) {
            System.out.println(entry.getKey().getName() + " => " + entry.getValue());
        }
    }

    public void displayAllOrderCost(Scanner scanner) throws SQLException {
        System.out.println("All available customer Ids:");
        System.out.println(customerDAOservices.getAllExistingIds());

        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println(customerDAOservices.allOrderPrices(id));
    }

    public void displayAllFavouriteRestaurants(Scanner scanner) throws SQLException {
        System.out.println("All available customer Ids:");
        System.out.println(customerDAOservices.getAllExistingIds());

        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println(customerDAOservices.getAllFavouriteRestaurants(id));
    }

    public void displayAllLoyaltyPoints() throws SQLException {
        System.out.println("All available customer Ids:");
        System.out.println(customerDAOservices.getAllLoyaltyPoints());
    }

}
