package DAO;

import java.util.*;

import FoodGrabClasses.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;


public class CustomerDAO extends UserDAO<Customer> {
    private PreparedStatement insertCustomerInfo;
    private PreparedStatement insertOrderHistory;
    private PreparedStatement insertUser;
    private PreparedStatement insertfavouriteRestaurant;
    private PreparedStatement verifyItemExistence;
    private PreparedStatement selectcustomerNumber;
    private PreparedStatement selectFavouriteRestaurants;
    private PreparedStatement selectCustomerId;
    private PreparedStatement selectOrders;
    private PreparedStatement selectLoyaltyPoints;
    private PreparedStatement selectAddresses;
    private PreparedStatement selectCards;
    private PreparedStatement selectSelectedItems;
    private PreparedStatement selectCustomerProfile;
    private PreparedStatement deleteCustomer;
    private PreparedStatement selectPrices;


//Default constructor
   public CustomerDAO()throws SQLException {}


    //Get the number of customer that exist in the database
    public Integer getNumberOfCustomers() throws SQLException {
        selectcustomerNumber = c.prepareStatement("SELECT COUNT(*) FROM Customers");
        ResultSet rs = selectcustomerNumber.executeQuery();
       if(rs.next()) {                                                         //verific daca exista macar un customer
           return rs.getInt(1);
       }
       return 0;
    }

    //Get all customers ids using a list
    public List<Integer> getCustomerId() throws SQLException {
        selectCustomerId = c.prepareStatement("SELECT customer_id FROM Customers");
        ResultSet rs = selectCustomerId.executeQuery();
       List<Integer> Ids = new ArrayList<>();
       while(rs.next()) {
            Ids.add(rs.getInt(1));
       }
       return Ids;
    }
    //Get the loyalty points assigned to a customer
    public Integer getLoyaltyPoints(int id) throws SQLException {
        selectLoyaltyPoints = c.prepareStatement("SELECT loyaltyPoints FROM customers WHERE customer_id = ?");
        selectLoyaltyPoints.setInt(1, id);
       ResultSet rs = selectLoyaltyPoints.executeQuery();
       if(rs.next()) {
           return rs.getInt(1);
       }
       else
           return 0;
    }

    //Get all the orders that a customer has placed
    public List<Order> getAllOrders(int customer_id) throws SQLException
    {
        selectOrders = c.prepareStatement("SELECT order_id,date,deliveryStatus,price FROM Orders WHERE customer_id = ? ");
        selectOrders.setInt(1, customer_id);
        ResultSet rs = selectOrders.executeQuery();
        List<Order> orders = new ArrayList<>();
        while(rs.next()) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            OrderDAO orderStatement = new OrderDAO();
            List<Food> foods = orderStatement.getAllFood(rs.getInt("order_id"));
            Order order = new Order(formatter.format(rs.getDate("date")),foods,rs.getDouble("price"));

            orders.add(order);
        }
        return orders;
    }


    //Get all the favourite restaurants from the database
    public Set<Restaurant>  getAllFavouriteRestaurants(int customer_id) throws SQLException {
        selectFavouriteRestaurants = c.prepareStatement(
                "SELECT r.restaurant_id,r.name,r.deliveryPrice,r.address FROM favouriteRestaurants fr " +
                        "JOIN Restaurants r ON r.restaurant_id = fr.restaurant_id" +
                        " WHERE customer_id = ?");
       selectFavouriteRestaurants.setInt(1, customer_id);
        ResultSet rs = selectFavouriteRestaurants.executeQuery();
        Set<Restaurant> favouriteRestaurants = new HashSet<Restaurant>();
       while(rs.next()) {
           String date = rs.getString("address");
           String[] dateParts = date.split(" ");

           Restaurant restaurant = new Restaurant(dateParts[0],dateParts[1],Integer.parseInt(dateParts[2]),rs.getString("name"),rs.getDouble("deliveryPrice"));
           restaurant.setRestaurantId(rs.getInt("restaurant_id"));

           favouriteRestaurants.add(restaurant);
       }
       return favouriteRestaurants;
    }

    //Get all addresses from the database asigned to an user by their id
    public Set<Address> getAllAddresses(int customer_id) throws SQLException {
        selectAddresses = c.prepareStatement("SELECT city,street,number,addressType FROM Addresses WHERE customer_id = ?");
        selectAddresses.setInt(1, customer_id);
       ResultSet rs = selectAddresses.executeQuery();
       Set<Address> addresses =new HashSet<>();
       while(rs.next()) {
           Address address = new Address(rs.getString("city"),rs.getString("street"),rs.getInt("number"),rs.getString("addressType"));
           addresses.add(address);
       }
        return addresses;
    }

    //Get all cards from the database that are asigned to an user by their id
    public Set<Card> getAllCards(int customer_id) throws SQLException {
        selectCards = c.prepareStatement("SELECT expire_date,cvv,number  FROM Cards WHERE customer_id = ?");
        selectCards.setInt(1, customer_id);
       ResultSet rs = selectCards.executeQuery();
       Set<Card> cards = new HashSet<>();
       while(rs.next()) {
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           Card card = new Card(rs.getString("number"),rs.getInt("cvv"),sdf.format(rs.getDate("expire_date")));
           cards.add(card);
       }
       return cards;
    }

    //Get all the selected items from the database that a customer has
    public Map<Food,Integer> getAllSelectedItems(int customer_id) throws SQLException {
        selectSelectedItems = c.prepareStatement("SELECT food_id,number FROM selectedItems WHERE customer_id = ?");
        selectSelectedItems.setInt(1, customer_id);
        ResultSet rs = selectSelectedItems.executeQuery();
        Map<Food,Integer> selectedItems = new HashMap<>();
        FoodDAO foodDAO = new FoodDAO();
        while(rs.next()) {
           Food food  =  foodDAO.getFoodInfo(rs.getInt("food_id"));
           if(!selectedItems.containsKey(food)) {
               selectedItems.put(food,1);
           }
           else {
               selectedItems.put(food,selectedItems.get(food)+1);
           }
        }
        return selectedItems;
    }

   //Add the customer to the database
    public void addToDataBaseCustomer(Customer customer) throws SQLException {
        insertUser = c.prepareStatement("INSERT INTO Customers VALUES (?,?)");

       insertUser.setInt(1,customer.getUserId());
       insertUser.setDouble(2,customer.getLoyaltyPoints());
       insertUser.executeUpdate();

    }



    //Verifying if the restaurant exists in the database
    public Integer verifyRestaurantExistence(String nume) throws SQLException {
        verifyItemExistence = c.prepareStatement("SELECT restaurant_id FROM Restaurants WHERE name= ?");
        verifyItemExistence.setString(1,nume);
        ResultSet rs = verifyItemExistence.executeQuery();
        if(rs.next()) {
            return rs.getInt(1); //I verify if there is at least 1 restaurant
        }
        return 0; //If not return 0
    }

    //Add to the database all the favourite restaurants that a customer has added to their list
    public void addToDataBaseFavouriteRestaurant(Customer customer) throws SQLException {
        insertfavouriteRestaurant = c.prepareStatement("INSERT INTO favouriteRestaurants VALUES (?,?)");
        for (Restaurant restaurant : customer.getFavouriteRestaurants()) {
            int restaurant_id = verifyRestaurantExistence(restaurant.getName());
            if (restaurant_id != 0) {
                insertfavouriteRestaurant.setInt(1, customer.getUserId());
                insertfavouriteRestaurant.setInt(2, restaurant_id);
                insertfavouriteRestaurant.executeUpdate();
            }
        }
    }
    public void addOrderHistory(Customer customer) throws SQLException {
        insertOrderHistory =c.prepareStatement("INSERT INTO ORDERS VALUES (?,?,?,?,?)");
        for(Order order : customer.getOrdersHistory())
        {

        }
    }

    @Override
    public void add(Customer customer) {
       try {
           super.add(customer);

           // Retrieving the last inserted user ID
           int userId = getLastUserId();
           customer.setUserId(userId); // Setting the user ID for the customer

           //insert customer details
           insertCustomerInfo = c.prepareStatement("INSERT INTO Customers VALUES (?, ?)");
           insertCustomerInfo.setInt(1, userId);
           insertCustomerInfo.setDouble(2, customer.getLoyaltyPoints());
           insertCustomerInfo.executeUpdate();
       }catch (SQLException e) {e.printStackTrace();}

    }

    @Override
    public void delete(int id) throws SQLException {
        super.delete(id);

            deleteCustomer = c.prepareStatement("DELETE FROM customers WHERE customer_id = ?");
            deleteCustomer.setInt(1, id);
            deleteCustomer.executeUpdate();
            deleteCustomer = c.prepareStatement("DELETE FROM favouriterestaurants WHERE customer_id = ?");
            deleteCustomer.setInt(1, id);
            deleteCustomer.executeUpdate();
            deleteCustomer = c.prepareStatement("DELETE FROM orders WHERE customer_id = ?");
            deleteCustomer.setInt(1, id);
            deleteCustomer.executeUpdate();
            deleteCustomer = c.prepareStatement("DELETE FROM cards WHERE customer_id = ?");
            deleteCustomer.setInt(1, id);
            deleteCustomer.executeUpdate();
            deleteCustomer = c.prepareStatement("DELETE FROM addresses WHERE customer_id = ?");
            deleteCustomer.setInt(1, id);
            deleteCustomer.executeUpdate();


    }


    public double getAllOrdersPrice (int customer_id) throws SQLException {
       selectPrices = c.prepareStatement("SELECT sum(price) FROM orders WHERE customer_id = ?");
       selectPrices.setInt(1, customer_id);
       ResultSet rs = selectPrices.executeQuery();
       if(rs.next()) {
           return rs.getDouble(1);
       }
       return 0;
    }






}
