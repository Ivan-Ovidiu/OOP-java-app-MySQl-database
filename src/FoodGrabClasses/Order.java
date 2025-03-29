package FoodGrabClasses;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private static Integer orderId;
    private String date;
    private  List<Food> savedFoods;
    private double price;
    private String deliveryStatus;

//Constructors
    public Order(){}
    public Order(String _date, List<Food> _savedFoods, double _price) {
        savedFoods = new ArrayList<Food>();
        date = _date;
        for(Food f : _savedFoods)
            savedFoods.add(f);
        price = _price;
        deliveryStatus = "Placed";
    }

//Getters
public String getDate() {return date;}
public List<Food> getSavedFoods() {return savedFoods;}
public double getPrice() {return price;}
public int getOrderId() {return orderId;}
    public String getDeliveryStatus() {return deliveryStatus;}
}
