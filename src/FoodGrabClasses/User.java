package FoodGrabClasses;
import Statements.UserStatements;
import Statements.UserStatements;
import java.sql.SQLException;

public class User {
    static Integer generateduserId ;
    protected Integer userId;
    String name;
    String email;
    String phoneNumber;
    String password;
    String userType ;

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

    //Constructors
    public User(){
        userType="user";
    }
    public User(String _name, String _email, String _phoneNumber, String _password) throws SQLException {
        UserStatements userStatements = new UserStatements();
        generateduserId = userStatements.getLastUserId() + 1;
        userId = generateduserId;
        name = _name;
        email = _email;
        phoneNumber = _phoneNumber;
        password = _password;
    if(!userStatements.verifyingUserExistence(_name))
        userStatements.addToDataBase(this);
    }


}

