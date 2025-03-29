package FoodGrabClasses;

public class Address {
    String city;
    String street;
    int number;
    String addressType;
    public Address(){}
    public Address(String _city, String _street, int _number, String _addressType) {
        city = _city;
        street = _street;
        number = _number;
        addressType = _addressType;
    }
//Setters
     public void setCity(String _city) {city = _city;}
    public void setStreet(String _street) {street = _street;}
    public void setNumber(int _number) {number = _number;}
    public void setAddressType(String _addressType) {addressType = _addressType;}

//Getters
public String getCity() {return city;}
public String getStreet() {return street;}
public int getNumber() {return number;}
public String getAddressType() {return addressType;}

    @Override
    public String toString() {

        return city + " " + street + " " + number;
    }

}
