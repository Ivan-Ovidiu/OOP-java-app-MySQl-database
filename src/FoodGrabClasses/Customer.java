package FoodGrabClasses;

import DAO.CustomerDAO;
import java.util.*;
import java.sql.SQLException;

public class Customer extends User {
    private Set<Address> savedAddresses ;
    private Set<Card> cards;
    private int loyaltyPoints;
    private List<Order> ordersHistory;
    private Set<Restaurant> favouriteRestaurants;
    private Map<Food,Integer> selectedItems;

//Getters
    public int getLoyaltyPoints() {return loyaltyPoints;}
    public Set<Restaurant> getFavouriteRestaurants() {return favouriteRestaurants;}
    public Map<Food,Integer> getSelectedItems() {return selectedItems;}
    public Set<Address> getSavedAddresses() {return savedAddresses;}
    public Set<Card> getCards() {return cards;}
    public List<Order> getOrdersHistory() {return ordersHistory;}

//Constructors

    //Default constructor
    public Customer() {
        savedAddresses = new HashSet<>();
        cards = new HashSet<>();
        loyaltyPoints = 0;
        ordersHistory = new ArrayList<>();
        favouriteRestaurants = new HashSet<>();
        selectedItems = new HashMap<>();
        userType = "Customer";
    }
    //All parameters constructor
    public Customer(int _id,String _name, String _email, String _phoneNumber, String _password, Set<Address> _savedAddresses, Set<Card> _cards,
                    int _loyaltyPoints, List<Order> _ordersHistory, Set<Restaurant> _favouriteRestaurants,
                    Map<Food,Integer> _selectedFood)  throws SQLException {
        super(_id,_name, _email, _phoneNumber, _password); //This calls the constructor in the superclass (User)

//Empty initializing
        savedAddresses = new HashSet<>();
        cards = new HashSet<>();
        ordersHistory = new ArrayList<>();
        favouriteRestaurants = new HashSet<>();
        selectedItems = new HashMap<>();
        userType = "Customer";

        CustomerDAO customerStatement = new CustomerDAO();

        for (Address a : _savedAddresses)
            savedAddresses.add(a);
        for (Card c : _cards)
            cards.add(c);
        loyaltyPoints = _loyaltyPoints;
        for (Order o : _ordersHistory)
            ordersHistory.add(o);
        for (Restaurant r : _favouriteRestaurants)
            favouriteRestaurants.add(r);
        for (Map.Entry<Food, Integer> f : _selectedFood.entrySet())
            selectedItems.put(f.getKey(), f.getValue());
    }

}
