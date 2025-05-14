package FoodGrabClasses;
import java.sql.*;
import Menu.*;
import java.util.Scanner;

import Services.*;


public class Main {

   public static void main(String[] args)  {
      try {

         Scanner scanner = new Scanner(System.in);
         SingletonInteractiveMenu menu = SingletonInteractiveMenu.getInstance();
         menu.Start(scanner);

      }catch(SQLException e) {e.printStackTrace();}
   }

}