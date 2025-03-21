package Statements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import FoodGrabClasses.Order;
import FoodGrabClasses.Address;
import FoodGrabClasses.Customer;
import FoodGrabClasses.Food;
import Statements.OrderStatement;
import Connections.ConnectionClass;
import FoodGrabClasses.Restaurant;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;


public class CustomerStatement extends UserStatements {

    private PreparedStatement insertUser;
    private PreparedStatement insertfavouriteRestaurant;
    private PreparedStatement verifyItemExistence;
    private PreparedStatement selectcustomerNumber;
    private PreparedStatement selectFavouriteRestaurants;
    private PreparedStatement selectCustomerId;
    private PreparedStatement selectOrders;
    private PreparedStatement selectLoyaltyPoints;


//Cosntructor
   public CustomerStatement ()throws SQLException {}

    {
        //Retrieve info from databse queries
        selectcustomerNumber = c.prepareStatement("SELECT COUNT(*) FROM Customers");
        selectFavouriteRestaurants = c.prepareStatement(
                "SELECT r.restaurant_id,r.name,r.deliveryPrice,r.address FROM favouriteRestaurants fr " +
                    "JOIN Restaurants r ON r.restaurant_id = fr.restaurant_id" +
                    " WHERE customer_id = ?");
        selectOrders = c.prepareStatement("SELECT order_id,date,deliveryStatus,price FROM Orders WHERE customer_id = ? ");
        selectLoyaltyPoints = c.prepareStatement("SELECT loyaltyPoints FROM customers WHERE customer_id = ?");
        verifyItemExistence = c.prepareStatement("SELECT restaurant_id FROM Restaurants WHERE name= ?");
        selectCustomerId = c.prepareStatement("SELECT customer_id FROM Customers");

        //Insert into databse queries
        insertUser = c.prepareStatement("INSERT INTO Customers VALUES (?,?)");
        insertfavouriteRestaurant = c.prepareStatement("INSERT INTO favouriteRestaurants VALUES (?,?)");

    }

    //Get the number of customer that exist in the database
    public Integer getNumberOfCustomers() throws SQLException {
       ResultSet rs = selectcustomerNumber.executeQuery();
       if(rs.next()) {                                                           //verific daca exista macar un customer
           return rs.getInt(1);
       }
       return 0;
    }

    //Get all customers ids using a list
    public List<Integer> getCustomerId() throws SQLException {
       ResultSet rs = selectCustomerId.executeQuery();
       List<Integer> Ids = new ArrayList<>();
       while(rs.next()) {
            Ids.add(rs.getInt(1));
       }
       return Ids;
    }
    //Get the loyalty points assigned to a customer
    public Integer getLoyaltyPoints(Integer id) throws SQLException {
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
        selectOrders.setInt(1, customer_id);
        ResultSet rs = selectOrders.executeQuery();
        List<Order> orders = new ArrayList<>();
        while(rs.next()) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            OrderStatement orderStatement = new OrderStatement();
            List<Food> foods = orderStatement.getAllFood(rs.getInt("order_id"));
            Order order = new Order(formatter.format(rs.getDate("date")),foods,rs.getDouble("price"));

            orders.add(order);
        }
        return orders;
    }


    //Get all the favourite restaurants from the database
    public Set<Restaurant>  getAllFavouriteRestaurants(int customer_id) throws SQLException {
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

   //Add the customer to the database
    public void addToDataBaseCustomer(Customer customer) throws SQLException {

       insertUser.setInt(1,customer.getUserId());
       insertUser.setDouble(2,customer.getLoyaltyPoints());
       insertUser.executeUpdate();

    }

    //Verifying if the restaurant exists in the database
    public Integer verifyRestaurantExistence(String nume) throws SQLException {
       verifyItemExistence.setString(1,nume);
        ResultSet rs = verifyItemExistence.executeQuery();
        if(rs.next()) {
            return rs.getInt(1); //I verify if there is at least 1 restaurant
        }
        return 0; //If not return 0
    }

    //Add to the database all the favourite restaurants that a customer has added to their list
    public void addToDataBaseFavouriteRestaurant(Customer customer) throws SQLException {
            for(Restaurant restaurant : customer.getFavouriteRestaurants()) {
                Integer restaurant_id = verifyRestaurantExistence(restaurant.getName());
                if(restaurant_id != 0) {
                    insertfavouriteRestaurant.setInt(1, customer.getUserId());
                    insertfavouriteRestaurant.setInt(2, restaurant_id);
                    insertfavouriteRestaurant.executeUpdate();
                }
            }
    }

}
