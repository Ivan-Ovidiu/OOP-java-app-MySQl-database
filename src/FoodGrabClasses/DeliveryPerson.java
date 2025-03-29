package FoodGrabClasses;
import java.sql.SQLException;
import java.util.List;

public class DeliveryPerson extends User {
    private Integer deliveryPersonId;
    private String vehicle;
    private String currentLocation;
    private List<Customer> deliveryHistory;
    private double earnings;



    public DeliveryPerson(String _name, String _email, String _phoneNumber, String _password) throws SQLException {
        super(_name, _email, _phoneNumber, _password);
        deliveryPersonId = userId;
        userType = "deliveryPerson";
    }

}
