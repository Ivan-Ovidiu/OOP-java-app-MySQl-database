package Menu;
import Services.*;
import java.sql.SQLException;
import java.util.Scanner;

public class SingletonInteractiveMenu {

    private CustomerService customerService;
    private CardService cardService;

    static SingletonInteractiveMenu menu;

    static {
            menu = new SingletonInteractiveMenu();
    }

    private SingletonInteractiveMenu() {
        try {
            customerService = new CustomerService();
            cardService = new CardService();
        }catch (Exception e) {e.printStackTrace();}
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
                System.out.println("1)Manage ~Customers~" +
                                   "\n2)Manage ~Restaurants~" +
                                   "\n3)Manage ~Couriers~ "+
                                   "\n--->");

                boolean exit = false;  //Ma asigur ca mereu cand intru intr un sub-meniu valoarea exitului va fi false si voi sta acolo pana cand dau exit
                String choice = scanner.nextLine();
                switch(choice) {
                    case "1":
                    while (!exit) {
                        System.out.println("Select an option" +
                                "\n1)Edit profile" +
                                "\n2)Delete customer" +
                                "\n3)Display sorted food by id" +
                                "\n4)Display favourite restaurants" +
                                "\n5)Display all loyalty points" +
                                "\n6)Display total costs of an order of a customer" +
                                "\n ~Cards~" +
                                "\n7)Add a new card to a customer" +
                                "\n8)Delete a card"  +
                                "\n9)Modify card" +
                                "\n10)Exit"+
                                "\n--->");

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
                                customerService.AddCustomerCard(scanner);
                                break;
                            case "8":
                                customerService.deleteUserCard(scanner);
                                break;
                            case "9":
                                customerService.modifyUserCard(scanner);
                                break;
                            case "10":
                                exit = true;
                                break;

                            default:
                                System.out.println("Invalid choice");
                        }
                    }
                }
            }
        }
    }
}
