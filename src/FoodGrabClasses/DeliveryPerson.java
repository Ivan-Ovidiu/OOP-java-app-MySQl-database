package FoodGrabClasses;
import java.sql.SQLException;
import java.util.List;

public class DeliveryPerson extends User {
    Integer deliveryPersonId;
    String vehicle;
    String currentLocation;
    List<Customer> deliveryHistory;
    double earnings;



    public DeliveryPerson(String _name, String _email, String _phoneNumber, String _password) throws SQLException {
        super(_name, _email, _phoneNumber, _password);
        deliveryPersonId = userId;
        userType = "deliveryPerson";
    }

}
