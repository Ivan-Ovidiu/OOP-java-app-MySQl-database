package DAO;

import Connections.ConnectionClass;
import FoodGrabClasses.Restaurant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RestaurantDAO extends ConnectionClass {

    private Connection c;
   private PreparedStatement insertRestaurant;
   private PreparedStatement lastLine;
   private PreparedStatement statement;

//Constructor
public RestaurantDAO() throws SQLException {
    c = ConnectionClass.getConnection();
}


    public int getLastRestaurantId() throws SQLException {
        lastLine= c.prepareStatement("select restaurant_id from Restaurants order by restaurant_id desc limit 1;");

        ResultSet rs = lastLine.executeQuery();
        if(rs.next()){
            return rs.getInt(1);
        }
        return 0;
    }

    public void addToDataBase(Restaurant restaurant) throws SQLException {
        insertRestaurant = c.prepareStatement("INSERT INTO Restaurants VALUES (?,?,?,?)");

        int newRestaurantId = getLastRestaurantId() + 1;
        insertRestaurant.setInt(1, newRestaurantId);
        insertRestaurant.setString(2, restaurant.getName());
        insertRestaurant.setDouble(3, restaurant.getDeliveryPrice());
      //  System.out.println(restaurant.getAddress());
        insertRestaurant.setString(4, restaurant.getAddress());
        insertRestaurant.executeUpdate();


    }

}
