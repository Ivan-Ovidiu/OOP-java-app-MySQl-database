package DAO;
import FoodGrabClasses.User;
import Connections.ConnectionClass;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends ConnectionClass {

    private PreparedStatement insertUser;
    private PreparedStatement lastLine;
    private PreparedStatement selectUserInfo1;
    private PreparedStatement selectUserInfo;

    private PreparedStatement selectSearchName;

//Default Constructor ( Whenever an UserStatement is created the function prepareSattements is being called )
    public UserDAO() throws SQLException {
    }



    {
        //retrieve info from database
        selectUserInfo1 = c.prepareStatement("SELECT user_id,name,email,phoneNumber,password FROM users WHERE user_id=?");
        lastLine= c.prepareStatement("select user_id from Users order by user_id desc limit 1;"); // select last id from the table user
        selectUserInfo = c.prepareStatement("SELECT COUNT(*) FROM users WHERE name=?");
        //insert info into the database
        insertUser = c.prepareStatement("insert into Users values(?,?,?,?,?,?)");
    }

    //Verifying if a user with a given name already exists in the database
    public boolean verifyingUserExistence(String username) throws SQLException {
        selectUserInfo.setString(1, username);
        ResultSet rs = selectUserInfo.executeQuery();
        if(rs.next())
            return true;
        return false;
    }

    //Retrieving all the base information for a use from the database
    public User getUserInfo(int user_id) throws SQLException {
        selectUserInfo1.setInt(1, user_id);
        ResultSet rs = selectUserInfo1.executeQuery();

        if(rs.next()) {
            User user = new User(rs.getString("name"),rs.getString("email"),rs.getString("phoneNumber"),rs.getString("password"));
            return user;
        }
        else
            return null;

    }
    //Getting the last user id from the database so i can set the next one for the next user
    public int getLastUserId() throws SQLException {
            ResultSet rs = lastLine.executeQuery();
            if(rs.next()) {
                return rs.getInt(1); //I verify if there is at least 1 user
            }
            return 0; //If not return 0

    }

    //Adding the base user to the database
   public void addToDataBase(User user) throws SQLException {
        int newUserId = getLastUserId() + 1; //Increment to reach the last user , because it starts on -1

        insertUser.setInt(1, newUserId);
        insertUser.setString(2, user.getName());
        insertUser.setString(3, user.getEmail());
        insertUser.setString(4, user.getPhoneNumber());
        insertUser.setString(5, user.getPassword());
        insertUser.setString(6, user.getUserType());

        insertUser.executeUpdate();
        System.out.println("User added successfully!");
    }



}
