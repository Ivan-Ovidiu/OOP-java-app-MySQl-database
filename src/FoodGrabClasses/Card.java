package FoodGrabClasses;

public class Card {
    private String number;
    private int  cvv;
    private String expire_date;

    public Card(String _number,int _cvv,String _expire_date) {
    number = _number;
    cvv = _cvv;
    expire_date = _expire_date;
    }
}