package Statements;

import java.util.Scanner;
import Connections.ConnectionClass;
import FoodGrabClasses.Restaurant;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RestaurantStatement extends ConnectionClass {


   private PreparedStatement insertRestaurant;
   private PreparedStatement lastLine;

//Constructor
public RestaurantStatement() throws SQLException {}

    {
        insertRestaurant = c.prepareStatement("INSERT INTO Restaurants VALUES (?,?,?,?)");
        lastLine= c.prepareStatement("select restaurant_id from Restaurants order by restaurant_id desc limit 1;");
    }
    public int getLastRestaurantId() throws SQLException {
        ResultSet rs = lastLine.executeQuery();
        if(rs.next()){
            return rs.getInt(1);
        }
        return 0;
    }

    public void addToDataBase(Restaurant restaurant) throws SQLException {
        int newRestaurantId = getLastRestaurantId() + 1;
        insertRestaurant.setInt(1, newRestaurantId);
        insertRestaurant.setString(2, restaurant.getName());
        insertRestaurant.setDouble(3, restaurant.getDeliveryPrice());
      //  System.out.println(restaurant.getAddress());
        insertRestaurant.setString(4, restaurant.getAddress());
        insertRestaurant.executeUpdate();


    }

}
