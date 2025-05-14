package Services;
import DAO.CustomerDAO;
import DAO.UserDAO;
import DAOServices.CustomerDAOServices;
import FoodGrabClasses.*;

import java.sql.SQLException;
import java.util.*;

public class CustomerService {
    private CustomerDAOServices customerDAOservices;
    UserDAO userDAO ;
    CustomerDAO customerDAO ;
    public CustomerService() {
        try {
            customerDAOservices = new CustomerDAOServices();
            userDAO = new UserDAO();
            customerDAO = new CustomerDAO();
        }catch (Exception e) {e.printStackTrace();}
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
    public void displayProfile(int id) throws Exception {
        // Retrieve user info
        String _name = userDAO.getUserInfo(id).getName();
        String _email = userDAO.getUserInfo(id).getEmail();
        String _phoneNumber = userDAO.getUserInfo(id).getPhoneNumber();
        Integer _loyaltyPoints = customerDAO.getLoyaltyPoints(id);
        List<Order> _orderHistory = customerDAO.getAllOrders(id);
        Set<Restaurant> _favouriteRestaurants = customerDAO.getAllFavouriteRestaurants(id);
        Set<Address> _addresses = customerDAO.getAllAddresses(id);
        Set<Card> _cards = new HashSet<>(CardService.readCard(id));
        Map<Food,Integer> _selectedFood = customerDAO.getAllSelectedItems(id);

        StringBuilder profile = new StringBuilder();
        profile.append("+================================================+\n");
        profile.append("|               CUSTOMER PROFILE                 |\n");
        profile.append("+================================================+\n");
        profile.append(String.format("| Name: %-42s|\n", _name));
        profile.append(String.format("| Email: %-41s|\n", _email));
        profile.append(String.format("| Phone: %-41s|\n", _phoneNumber));
        profile.append(String.format("| Loyalty Points: %-31s|\n", _loyaltyPoints));
        profile.append("+================================================+\n");

        // Addresses
        profile.append("| Addresses:                                     |\n");
        for (Address addr : _addresses) {
            profile.append(String.format("| - %-44s|\n", addr.toString()));
        }

        // Cards
        profile.append("+------------------------------------------------+\n");
        profile.append("| Cards:                                         |\n");
        for (Card card : _cards) {
            profile.append(String.format("| - %-44s|\n", card.getGetCardNumber()));
        }

        // Favourite Restaurants
        profile.append("+------------------------------------------------+\n");
        profile.append("| Favourite Restaurants:                         |\n");
        for (Restaurant res : _favouriteRestaurants) {
            profile.append(String.format("| - %-44s|\n", res.getName()));
        }

        // Orders
        profile.append("+------------------------------------------------+\n");
        profile.append("| Order History:                                 |\n");
        for (Order order : _orderHistory) {
            profile.append(String.format("| - Order #%s : %s\n",order.getDate(),  order.getDeliveryStatus()));
        }

        // Selected Items
        profile.append("+------------------------------------------------+\n");
        profile.append("| Selected Items:                                |\n");
        for (Map.Entry<Food, Integer> entry : _selectedFood.entrySet()) {
            profile.append(String.format("| - %-30s x%-10d|\n", entry.getKey().getName(), entry.getValue()));
        }

        profile.append("+================================================+\n");

        System.out.println(profile.toString());
    }

    public void editProfile(Scanner scanner)  {
        try {
            System.out.println("All available customer Ids:");
            System.out.println(customerDAOservices.getAllExistingIds());

            int id = scanner.nextInt();
            scanner.nextLine();
            displayProfile(id);
            Customer customer = customerDAOservices.getCustomer(id);
            boolean exit = false;
            while (!exit) {
                System.out.println("Choose what to edit:\n" +
                        "1 - name\n" +
                        "2 - email\n" +
                        "3 - phone number\n" +
                        "4 - password\n" +
                        "5 - ↩ Back\n" +
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
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice, try again.");
                }
                customerDAOservices.editCustomer(customer);
            }
        }catch (Exception e) {e.printStackTrace();}
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

    public void AddCustomerCard(Scanner scanner)  {
        try {
            System.out.println("All available customer Ids:");
            System.out.println(customerDAOservices.getAllExistingIds());
            int id = scanner.nextInt();
            scanner.nextLine();

            CardService.createCard(scanner,id);
        }
        catch (SQLException e) {e.printStackTrace();}

    }

    public void deleteUserCard(Scanner scanner)  {
        try {
            System.out.println("All available customer Ids:");
            System.out.println(customerDAOservices.getAllExistingIds());
            int id = scanner.nextInt();
            scanner.nextLine();

            CardService.deleteCard(scanner, id);

        }catch (SQLException e) {e.printStackTrace();}
    }

    public void modifyUserCard(Scanner scanner)  {
        try {
            System.out.println("All available customer Ids:");
            System.out.println(customerDAOservices.getAllExistingIds());
            int id = scanner.nextInt();
            scanner.nextLine();

            CardService.deleteCard(scanner, id);
        }catch(SQLException e) {e.printStackTrace();}
    }


}
