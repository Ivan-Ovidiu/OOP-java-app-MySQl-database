package Services;

import DAO.RestaurantDAO;
import FoodGrabClasses.Address;
import FoodGrabClasses.Restaurant;

import java.sql.SQLException;
import java.util.Scanner;

public class RestaurantService {
    private RestaurantDAO restaurantDAO;
    public RestaurantService() {
        try
        {
            restaurantDAO = new RestaurantDAO();
        }catch(SQLException e){e.printStackTrace();}
    }


    public void deleteRestaurant(Scanner scanner) {
        System.out.println("Choose what restaurant to delete");
        System.out.println(restaurantDAO.getAllIds());
        int id = scanner.nextInt();
        scanner.nextLine();

        restaurantDAO.delete(id);

    }

    public void addRestaurant(Scanner scanner) {
        try {
            System.out.println("Add a new restaurant:");
            System.out.println("Name:");
            String name = scanner.nextLine();
            System.out.println("Delivery price:");
            double deliveryPrice = scanner.nextDouble();
            System.out.println("Restaurant address:");
            System.out.println("-  City:");
            String city = scanner.nextLine();
            System.out.println("- Street:");
            String street = scanner.nextLine();
            System.out.println("- Number:");
            int number = scanner.nextInt();
            scanner.nextLine();
            Restaurant restaurant = new Restaurant(city, street, number, name, deliveryPrice);

            restaurantDAO.addToDataBase(restaurant);
        }catch (SQLException e) {e.printStackTrace();}


    }

    public void updateRestaurant(Scanner scanner) {
        try {
            System.out.println("Choose what restaurant to update");
            System.out.println(restaurantDAO.getAllIds());
            int id = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Restaurant details");
            System.out.println(restaurantDAO.getRestaurantDetails(id));
            System.out.println("MODIFY RESTAURANT:");
            System.out.println("Name:");
            String name = scanner.nextLine();
            System.out.println("Delivery price:");
            double deliveryPrice = scanner.nextDouble();
            System.out.println("Restaurant address:");
            System.out.println("-  City:");
            String city = scanner.nextLine();
            System.out.println("- Street:");
            String street = scanner.nextLine();
            System.out.println("- Number:");
            int number = scanner.nextInt();
            scanner.nextLine();

            Restaurant restaurant = new Restaurant(city, street, number, name, deliveryPrice);
            restaurantDAO.update(restaurant,id);

        }catch (SQLException e) {e.printStackTrace();}


    }

    public void readRestaurant(Scanner scanner) {
        System.out.println("Choose what restaurant to print");
        System.out.println(restaurantDAO.getAllIds());
        int id = scanner.nextInt();
        scanner.nextLine();

        Restaurant restaurant = restaurantDAO.read(id);
        System.out.println(restaurant.getName()+ " " + restaurant.getDeliveryPrice() );

    }



}
