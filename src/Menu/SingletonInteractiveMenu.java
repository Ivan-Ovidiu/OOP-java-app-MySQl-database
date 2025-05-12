package Menu;
import Services.*;
import java.sql.SQLException;
import java.util.Scanner;

public class SingletonInteractiveMenu {

    private CustomerService customerService;

    static SingletonInteractiveMenu menu;

    static {
        try {
            menu = new SingletonInteractiveMenu();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private SingletonInteractiveMenu() throws SQLException {
        customerService = new CustomerService();
    }
    public static SingletonInteractiveMenu getInstance()
    {
        return menu;  //I'll always get the same instance of the class
    }


    public void Start(Scanner scanner) throws SQLException {
        System.out.println("FOODGRAB");
        while(true) {
            System.out.println("1)LOGIN");
            System.out.println("2)Create account");
            String choice1 = scanner.nextLine();
            switch(choice1)
            {
                case "1":
                    if(customerService.loginUser(scanner))
                        break;
                    else
                        continue;
                case "2":
                    customerService.createCustomer(scanner);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
            while (true) {
                System.out.println("   ___               _                   _     \n" +
                        "  / __\\__   ___   __| |   __ _ _ __ __ _| |__  \n" +
                        " / _\\/ _ \\ / _ \\ / _` |  / _` | '__/ _` | '_ \\ \n" +
                        "/ / | (_) | (_) | (_| | | (_| | | | (_| | |_) |\n" +
                        "\\/   \\___/ \\___/ \\__,_|  \\__, |_|  \\__,_|_.__/ \n" +
                        "                         |___/                 ");
                System.out.println("1)Manage customers" +
                                   "\n2)Manage restaurants" +
                                   "\n3)Manage Couriers ");
                String choice = scanner.nextLine();
                switch(choice) {
                    case "1":
                    while (true) {
                        System.out.println("Select an option" +
                                "\n1)Edit profile" +
                                "\n2)Delete customer" +
                                "\n3)Display sorted food by id" +
                                "\n4)Display favourite restaurants" +
                                "\n5)Display all loyalty points" +
                                "\n6)Display total costs of an order of a customer" +
                                "\n7)Exit");

                        String choice2 = scanner.nextLine();
                        switch (choice2) {
                            case "1":
                                customerService.editProfile(scanner);
                                break;
                            case "2":
                                customerService.deleteCustomer(scanner);
                                break;
                            case "3":
                                customerService.displayFoodList(scanner);
                            case "4":
                                customerService.displayAllFavouriteRestaurants(scanner);
                                break;
                            case "5":
                                customerService.displayAllLoyaltyPoints();
                                break;
                            case "6":
                                customerService.displayAllOrderCost(scanner);
                                break;
                            case "7":
                                continue;
                            default:
                                System.out.println("Invalid choice");
                        }
                    }
                }
            }
        }
    }
}
