package FoodGrabClasses;
import DAO.UserDAO;

import java.sql.SQLException;

public class User {
    static Integer generateduserId ;
    protected Integer userId;
    protected String name;
    protected String email;
    protected String phoneNumber;
    protected String password;
    protected String userType ;

    //Getters
    public int getUserId() {return userId;}
    public String getName() {return name;}
    public String getEmail() {return email;}
    public String getPhoneNumber() {return phoneNumber;}
    public String getPassword() {return password;}
    public String getUserType() {return userType;}

    //Setters
    public void setUserId(int _userId) {userId = _userId;}
    public void setGeneratedUserId(int _generatedUserId) {generateduserId = _generatedUserId;}
    public void setName(String name) {this.name = name;}
    public void setEmail(String email) {this.email = email;}
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}
    public void setPassword(String password) {this.password = password;}
    public void setUserType(String userType) {this.userType = userType;}

    //Constructors
    public User(){
        userType="user";
    }
    public User(int _id,String _name, String _email, String _phoneNumber, String _password) throws SQLException {
        UserDAO userStatements = new UserDAO();
        generateduserId = userStatements.getLastUserId() + 1;
        userId = _id;
        name = _name;
        email = _email;
        phoneNumber = _phoneNumber;
        password = _password;
    if(!userStatements.verifyingUserExistence(_name))
        userStatements.add(this);
    }


}

