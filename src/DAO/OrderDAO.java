package DAO;

import Connections.ConnectionClass;
import FoodGrabClasses.Order;
import FoodGrabClasses.Food;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class OrderDAO extends ConnectionClass {

    private Connection c;
    private PreparedStatement selectLastId;
    private PreparedStatement insertOrder;
    private PreparedStatement verifyFoodExistence;
    private PreparedStatement insertOrderFood;
    private PreparedStatement selectAllFood;

    public OrderDAO()throws SQLException {
        c = ConnectionClass.getConnection();
    }

//Get all the food asigned to an order from the database
    public List<Food> getAllFood(int orderId) throws SQLException {
        selectAllFood =  c.prepareStatement(
                 "SELECT f.food_id,f.grams,f.name,f.price FROM Foodorder fo " +
                     "JOIN Foods f ON fo.food_id = f.food_id " +
                     "WHERE order_id = ?");
        selectAllFood.setInt(1, orderId);
        ResultSet rs = selectAllFood.executeQuery();
        List<Food> foods = new ArrayList<>();
        while (rs.next()) {
            Food food = new Food(rs.getString("name"),rs.getDouble("grams"),rs.getDouble("price"));
            food.setFoodId(rs.getInt("food_id"));

            foods.add(food);
        }
        return foods;
    }




    public int getLastOrderId() throws SQLException {
        selectLastId = c.prepareStatement("select order_id from Orders order by order_id desc limit 1;");
        ResultSet rs = selectLastId.executeQuery();
        if(rs.next()){
            return rs.getInt(1);
        }
        return 0;
    }

    public Integer verifyFoodExistence(String nume) throws SQLException {
        verifyFoodExistence = c.prepareStatement("SELECT food_id FROM Foods WHERE name = ?");

        verifyFoodExistence.setString(1, nume);
        ResultSet rs = verifyFoodExistence.executeQuery();
        if(rs.next()){
            return rs.getInt(1);
        }
        return 0;
    }

    public void addToDataBaseFoodOrder(Order order) throws SQLException {
        insertOrderFood = c.prepareStatement("INSERT INTO foodorder VALUES (?,?)");

        for(Food food : order.getSavedFoods()) {
                Integer foodId = verifyFoodExistence(food.getName());
                if(foodId != 0){
                    insertOrderFood.setInt(1, order.getOrderId());
                    insertOrderFood.setInt(2, foodId);
                    insertOrderFood.executeUpdate();

                }
            }
    }

    public void addToDataBase(Order order,int customerId) throws SQLException {
        insertOrder = c.prepareStatement("INSERT INTO Orders VALUES (?,?,?,?,?)");

        int orderId = order.getOrderId();
        insertOrder.setInt(1, orderId);
        insertOrder.setInt(2,customerId);
        insertOrder.setDate(3,java.sql.Date.valueOf(LocalDate.now()));
        insertOrder.setString(4,order.getDeliveryStatus());
        insertOrder.setDouble(5,order.getPrice());
    }

}
