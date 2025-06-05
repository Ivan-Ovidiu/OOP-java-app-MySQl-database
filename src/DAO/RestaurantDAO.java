package DAO;

import Connections.ConnectionClass;
import FoodGrabClasses.Address;
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

    public String getAllIds(){
        String restaurants = "";

        try
        {
            statement = c.prepareStatement("SELECT restaurant_id,name FROM Restaurants");
            ResultSet rs = statement.executeQuery();
            while(rs.next())
            {
                restaurants += rs.getString("name") + "|" + rs.getInt("restaurant_id") + "\n";
            }

        }catch(SQLException e){e.printStackTrace();}
        return restaurants;
    }

    public String getRestaurantDetails(int id) {
    String restaurant = "";
    try
    {
        statement = c.prepareStatement("SELECT * FROM Restaurants WHERE restaurant_id=?");
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        if(rs.next())
        {
            restaurant += rs.getString("name") + "|" +  "|" + rs.getDouble("deliveryPrice") + "|" + rs.getString("address")+ "\n";
            return restaurant;
        }

    }catch(SQLException e){e.printStackTrace();}
        return restaurant;

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

        if (statement != null) {        //golesc statement ul
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        AuditDAO.writeAudit("Restaurant has been added");

    }

    public void delete(int restaurant_id) {
    try {
        statement =  c.prepareStatement("DELETE FROM Restaurants WHERE restaurant_id = ?");
        statement.setInt(1, restaurant_id);
        statement.executeUpdate();

    }catch(SQLException e){e.printStackTrace();}

        if (statement != null) {        //golesc statement ul
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    AuditDAO.writeAudit("Restaurant has been deleted");
    }

    public void update(Restaurant restaurant,int restaurant_id)  {
    try {
        statement = c.prepareStatement("UPDATE restaurants SET name = ?, deliveryPrice = ?, address = ? WHERE restaurant_id = ? ");
        statement.setString(1, restaurant.getName());
        statement.setDouble(2, restaurant.getDeliveryPrice());
        statement.setString(3, restaurant.getAddress());
        statement.setInt(4, restaurant_id);
        statement.executeUpdate();
    }catch(SQLException e){e.printStackTrace();}

        if (statement != null) {        //golesc statement ul
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    AuditDAO.writeAudit("Restaurant has been updated");
    }

    public Restaurant read(int restaurant_id) {
    try {
        statement = c.prepareStatement("SELECT * FROM Restaurants WHERE restaurant_id = ?");
        statement.setInt(1, restaurant_id);
        ResultSet rs = statement.executeQuery();
        Restaurant restaurant ;
        if(rs.next()){
            String[] words = rs.getString("address").split(" ", 3);
            String city = words[0], street = words[1], number = words[2];
            restaurant = new Restaurant(city,street,Integer.parseInt(number),rs.getString("name"),rs.getFloat("deliveryPrice"));
            return restaurant;
        }

    }catch (SQLException e){e.printStackTrace();}

        if (statement != null) {        //golesc statement ul
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        AuditDAO.writeAudit("Information about a card has been retrieved");
        return null;
    }




}
