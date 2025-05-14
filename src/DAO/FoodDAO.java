package DAO;
import FoodGrabClasses.Food;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FoodDAO extends UserDAO{

    private PreparedStatement selectFoodInfo;

    public FoodDAO() throws SQLException{}

    //Getting all the info from the databaase for a food item
    public Food getFoodInfo(int food_id) {

        try{
            selectFoodInfo = c.prepareStatement("SELECT * FROM Foods WHERE food_id = ?");
            selectFoodInfo.setInt(1, food_id);
            ResultSet rs = selectFoodInfo.executeQuery();
            if (rs.next()) {
                return new Food(rs.getString("name"), rs.getDouble("grams"), rs.getDouble("price"));
            } else {
                System.out.println("Food item with ID " + food_id + " not found.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
