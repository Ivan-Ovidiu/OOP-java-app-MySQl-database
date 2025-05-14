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

    public String getExpireDate(){
        return expire_date;
    }
    public int getCardCVV(){
        return cvv;
    }
    public String getGetCardNumber(){
        return number;
    }
    public void setNumber(String _number){number = _number;}
}