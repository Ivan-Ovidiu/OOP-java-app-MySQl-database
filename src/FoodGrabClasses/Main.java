package FoodGrabClasses;
import java.util.*;
import java.sql.*;


public class Main {

public static void main(String[] args) throws SQLException {
    Restaurant restaurant = new Restaurant("Bucuresti","Tomis",10,"Dristor Kebab",10.99);
    Set<Address> addresses = new HashSet<Address>();
    Set<String> cards = Set.of("3423526534545","235245345435");
    List<Order> orders = new ArrayList<Order>();
    Set<Restaurant> restaurants = Set.of(restaurant);
    Map<Food,Integer> foods = new HashMap<>();
        //User a = new User("Ovidiu Ivan", "admin@gmail.com", "+40770316652", "Ygvujmk0", "Admin");

    Customer customer = new Customer("Ovidiu Ivan", "admin@gmail.com", "+40770316652",
            "Ygvujmk0",addresses,cards,40,orders,restaurants,foods);


  // SingletonInteractiveMenu menu =  SingletonInteractiveMenu.getInstance();
  // menu.Start();

}
}