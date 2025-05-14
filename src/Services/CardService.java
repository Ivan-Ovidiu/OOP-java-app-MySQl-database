package Services;

import DAO.CardDAO;
import FoodGrabClasses.Card;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;



//Encryption
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CardService {
    private static CardDAO cardDAO ;
    private static final String SECRET_KEY = "CheieSecretaBuna";

    public CardService() {
        try {
         cardDAO = new CardDAO();
        }catch(SQLException e) {e.printStackTrace();}
    }

    private static String encrypt(String strToEncrypt) throws Exception {
        // Create secret key
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

        // Prepare cipher
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        // Encrypt and encode
        byte[] encryptedBytes = cipher.doFinal(strToEncrypt.getBytes());
        return Base64.getUrlEncoder().encodeToString(encryptedBytes);
    }


    private static String decrypt(String strToDecrypt) throws Exception {
        System.out.println(">>>> strToDecrypt = '" + strToDecrypt + "'"); // VERY first thing

        if (strToDecrypt == null) {
            throw new IllegalArgumentException("Input to decrypt() is null!");
        }
        if (strToDecrypt.trim().isEmpty()) {
            throw new IllegalArgumentException("Input to decrypt() is empty!");
        }

        // Create secret key
        SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");

        // Prepare cipher
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        // Decode and decrypt
        byte[] decryptedBytes = cipher.doFinal(Base64.getUrlDecoder().decode(strToDecrypt));
        return new String(decryptedBytes);
    }


    public static String hashAllButLastThree(String input) throws Exception {

        if (input == null || input.length() <= 3) {
            return input;
        }

        // Separate the last three digits
        String lastThreeDigits = input.substring(input.length() - 3);
        String toEncrypt = input.substring(0, input.length() - 3);

        // Encrypt the part before last three digits
        String encryptedPart = encrypt(toEncrypt);

        // Combine encrypted part with last three digits
        return encryptedPart + lastThreeDigits;
    }

    public static String deHashNumber(String hashedInput) throws Exception {
        // Validate input
        if (hashedInput == null || hashedInput.length() <= 3) {
            return hashedInput;
        }

        // Separate the last three digits
        String lastThreeDigits = hashedInput.substring(hashedInput.length() - 3);
        String encryptedPart = hashedInput.substring(0, hashedInput.length() - 3);

        // Decrypt the encrypted part
        String decryptedPart = decrypt(encryptedPart);

        // Combine decrypted part with last three digits
        return decryptedPart + lastThreeDigits;
    }


    public static void createCard(Scanner scanner,int customer_id)  {
        try {

            System.out.println("         ~Set your new card~ ");
            System.out.println("Number: ");
            String number = scanner.nextLine();
            Pattern datePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
            String expireDate;
            while (true) {
                System.out.println("Expire Date (yyyy-mm-dd): ");
                expireDate = scanner.nextLine();
                if (datePattern.matcher(expireDate).matches()) {
                    break;
                } else {
                    System.out.println("Invalid format. Please enter date as yyyy-mm-dd.");
                }
            }
            System.out.println("CVV: ");
            int cvv = Integer.parseInt(scanner.nextLine());
            String hashed_code = hashAllButLastThree(number);
            Card card = new Card(hashed_code,cvv,expireDate);

            cardDAO.setCard(card,customer_id);
        }catch(Exception e) {e.printStackTrace();}
    }

    public static   List<Card> readCard(int user_id) throws Exception {

       List<Card> cards = cardDAO.getCard(user_id);
       if(cards != null ) {
           for (int i = 0; i < cards.size(); i++) {
               String number = cards.get(i).getGetCardNumber();
               String dehashed_number = deHashNumber(number);
               cards.get(i).setNumber(dehashed_number);
           }
           return cards;
       }
       else {
           List<Card> cardsNull = new ArrayList<>();
           return cardsNull;
       }


    }

    public static void deleteCard(Scanner scanner,int user_id)
    {
        System.out.println("Choose the ID of the card you want to delete: ");
        System.out.println(cardDAO.getCard(user_id));
        int id = scanner.nextInt();
        scanner.nextLine();

        cardDAO.delete(id);

    }

    public static void updateCard(Scanner scanner,int user_id)  {
        try {
            System.out.println("Choose the ID of the card you want to update: ");
            System.out.println(cardDAO.getCard(user_id));
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.println("         ~Update the card~ ");
            System.out.println("Number: ");
            String number = scanner.nextLine();
            Pattern datePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
            String expireDate;
            while (true) {
                System.out.println("Expire Date (yyyy-mm-dd): ");
                expireDate = scanner.nextLine();
                if (datePattern.matcher(expireDate).matches()) {
                    break;
                } else {
                    System.out.println("Invalid format. Please enter date as yyyy-mm-dd.");
                }
            }
            System.out.println("CVV: ");
            int cvv = Integer.parseInt(scanner.nextLine());

            String hashed_code = hashAllButLastThree(number);

            Card card = new Card(hashed_code, cvv, expireDate);

            cardDAO.update(id,card);

        }catch (Exception e) {e.printStackTrace();}

    }


}
