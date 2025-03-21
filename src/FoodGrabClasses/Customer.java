package FoodGrabClasses;
import Statements.CustomerStatement;
import Statements.UserStatements;

import java.util.*;
import java.sql.SQLException;

public class Customer extends User {
    Set<Address> savedAddresses ;
    Set<String> card;
    Integer loyaltyPoints;
    List<Order> ordersHistory;
    Set<Restaurant> favouriteRestaurants;
    Map<Food,Integer> selectedItems;

//Getters
    public int getLoyaltyPoints() {return loyaltyPoints;}
    public Set<Restaurant> getFavouriteRestaurants() {return favouriteRestaurants;}

//Constructors
    public Customer() {
        savedAddresses = new HashSet<>();
        card = new HashSet<>();
        loyaltyPoints = 0;
        ordersHistory = new ArrayList<>();
        favouriteRestaurants = new HashSet<>();
        selectedItems = new HashMap<>();
        userType = "Customer";
    }
    public Customer(String _name, String _email, String _phoneNumber, String _password,
                    int _loyaltyPoints, List<Order> _ordersHistory, Set<Restaurant> _favouriteRestaurants
                    )  throws SQLException {
        super(_name, _email, _phoneNumber, _password); //This calls the constructor in the superclass (User)
//Empty initializing
        savedAddresses = new HashSet<>();
        card = new HashSet<>();
        ordersHistory = new ArrayList<>();
        favouriteRestaurants = new HashSet<>();
        selectedItems = new HashMap<>();
        userType = "Customer";

        CustomerStatement customerStatement = new CustomerStatement();

        loyaltyPoints = _loyaltyPoints;

        for (Order o : _ordersHistory)
            ordersHistory.add(o);
        for (Restaurant r : _favouriteRestaurants)
            favouriteRestaurants.add(r);


        //customerStatement.addToDataBaseCustomer(this);
       // customerStatement.addToDataBaseFavouriteRestaurant(this); //Add to the databse all the favourite restaurants that a customers has added to their list
    }

    public Customer(String _name, String _email, String _phoneNumber, String _password, Set<Address> _savedAddresses, Set<String> _cards,
                    int _loyaltyPoints, List<Order> _ordersHistory, Set<Restaurant> _favouriteRestaurants,
                    Map<Food,Integer> _selectedFood)  throws SQLException { super(_name,_email,_phoneNumber,_password); //This calls the constructor in the superclass (User)
//Empty initializing

        savedAddresses = new HashSet<>();
        card = new HashSet<>();
        ordersHistory = new ArrayList<>();
        favouriteRestaurants = new HashSet<>();
        selectedItems = new HashMap<>();
        userType = "Customer";

        CustomerStatement customerStatement = new CustomerStatement();
if(!customerStatement.verifyingUserExistence(_name)) {
    for (Address a : _savedAddresses)
        savedAddresses.add(a);
    for (String c : _cards)
        card.add(c);
    loyaltyPoints = _loyaltyPoints;
    for (Order o : _ordersHistory)
        ordersHistory.add(o);
    for (Restaurant r : _favouriteRestaurants)
        favouriteRestaurants.add(r);
    for (Map.Entry<Food, Integer> f : _selectedFood.entrySet())
        selectedItems.put(f.getKey(), f.getValue());


    customerStatement.addToDataBaseCustomer(this);
    customerStatement.addToDataBaseFavouriteRestaurant(this); //Add to the databse all the favourite restaurants that a customers has added to their list
}
else
    System.out.println("A customer with that name already exists!");


    }

}
