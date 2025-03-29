package Services;
import DAO.CustomerDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import FoodGrabClasses.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomerService {
    private CustomerDAO customerDAO;
    UserDAO userStatements ;
    public CustomerService() throws SQLException {
        customerDAO = new CustomerDAO();
        userStatements = new UserDAO();
    }



//Creating and returning a customer based on a given id, from the database
    public Customer getCustomer(int id)throws SQLException {

        String _name = userStatements.getUserInfo(id).getName();
        String _email = userStatements.getUserInfo(id).getEmail();
        String _phoneNumber = userStatements.getUserInfo(id).getPhoneNumber();
        String _password = userStatements.getUserInfo(id).getPassword();
        Integer _loyaltyPoints = customerDAO.getLoyaltyPoints(id);
        List<Order> _orderHistory = customerDAO.getAllOrders(id);
        Set<Restaurant> _favouriteRestaurants = customerDAO.getAllFavouriteRestaurants(id);
        Set<Address> _addresses = customerDAO.getAllAddresses(id);
        Set<Card> _cards = customerDAO.getAllCards(id);
        Map<Food,Integer> _selectedFood = customerDAO.getAllSelectedItems(id);
        Customer customer = new Customer(_name,_email,_phoneNumber,_password,_addresses,_cards,_loyaltyPoints,_orderHistory,_favouriteRestaurants,_selectedFood);
        return customer;
    }


}
