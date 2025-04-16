package DAOServices;
import DAO.*;
import FoodGrabClasses.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomerDAOServices {
    private CustomerDAO customerDAO;
    private UserDAO userDAO;


    public CustomerDAOServices() throws SQLException {
        customerDAO = new CustomerDAO();
        userDAO = new UserDAO();
    }

    public void createCustomer(Customer customer) throws SQLException {
        if (customer == null)
            throw new IllegalArgumentException("Customer cannot be null");
        customerDAO.add(customer);
    }

    public boolean loginChecker(String username,String password) throws SQLException {
       return userDAO.verifyUserExistence(username,password);
    }

    public Customer getCustomer(int id)throws SQLException {

        String _name = userDAO.getUserInfo(id).getName();
        String _email = userDAO.getUserInfo(id).getEmail();
        String _phoneNumber = userDAO.getUserInfo(id).getPhoneNumber();
        String _password = userDAO.getUserInfo(id).getPassword();
        Integer _loyaltyPoints = customerDAO.getLoyaltyPoints(id);
        List<Order> _orderHistory = customerDAO.getAllOrders(id);
        Set<Restaurant> _favouriteRestaurants = customerDAO.getAllFavouriteRestaurants(id);
        Set<Address> _addresses = customerDAO.getAllAddresses(id);
        Set<Card> _cards = customerDAO.getAllCards(id);
        Map<Food,Integer> _selectedFood = customerDAO.getAllSelectedItems(id);
        Customer customer = new Customer(id,_name,_email,_phoneNumber,_password,_addresses,_cards,_loyaltyPoints,_orderHistory,_favouriteRestaurants,_selectedFood);
        return customer;
    }

    public void displayProfile(int id) throws SQLException {
        // Retrieve user info
        String _name = userDAO.getUserInfo(id).getName();
        String _email = userDAO.getUserInfo(id).getEmail();
        String _phoneNumber = userDAO.getUserInfo(id).getPhoneNumber();
        Integer _loyaltyPoints = customerDAO.getLoyaltyPoints(id);
        List<Order> _orderHistory = customerDAO.getAllOrders(id);
        Set<Restaurant> _favouriteRestaurants = customerDAO.getAllFavouriteRestaurants(id);
        Set<Address> _addresses = customerDAO.getAllAddresses(id);
        Set<Card> _cards = customerDAO.getAllCards(id);
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
            profile.append(String.format("| - %-44s|\n", card.toString()));
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
            profile.append(String.format("| - Order #%d: %s\n", order.getOrderId(), order.getDeliveryStatus()));
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


    public List<Integer> getAllExistingIds() throws SQLException {
        if(customerDAO.getCustomerId() != null)
                return customerDAO.getCustomerId();
        return null;
    }

    public void editCustomer(Customer customer) throws SQLException {
        if(customer != null)
            userDAO.insertInUser(customer);
    }
}
