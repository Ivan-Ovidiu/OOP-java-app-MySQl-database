package FoodGrabClasses;
import DAO.RestaurantDAO;
import java.sql.SQLException;

public class Restaurant {
    private static Integer generatedRestaurantId;
    private Integer restaurantId;
    private Address address;
    private String name;
    private  double deliveryPrice;

//Constructors
    public Restaurant() {
        address = new Address();
        name = "Restaurant";
        deliveryPrice = 0.0;
    }

    public Restaurant(String _city, String _street, int _number, String _name,
                      double _deliveryPrice) throws SQLException {

        RestaurantDAO restaurantStatement = new RestaurantDAO();
        address = new Address(_city, _street, _number,"Restaurant");
        restaurantId = restaurantStatement.getLastRestaurantId();
        name = _name;
        deliveryPrice = _deliveryPrice;

        restaurantStatement.addToDataBase(this); //Adding to the database
    }


//Getters
    public String getName() {return name;}
    public double getDeliveryPrice() {return deliveryPrice;}
    public String getAddress() {return address.toString();}

//Setters
    public void setRestaurantId(Integer _restaurantId) { restaurantId = _restaurantId;}



}
