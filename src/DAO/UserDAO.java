package DAO;

import FoodGrabClasses.Customer;
import FoodGrabClasses.User;
import Connections.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO<T extends User> implements InterfaceDAO<T> {

    protected static Connection c;
    private PreparedStatement insertUser;
    private PreparedStatement lastLine;
    private PreparedStatement selectUserInfo1;
    private PreparedStatement selectUserInfo;
    private PreparedStatement deleteUser;

//Default Constructor
    public UserDAO() throws SQLException { c = ConnectionClass.getConnection();}


    //Verifying if a user with a given name already exists in the database
    public boolean verifyingUserExistence(String username) throws SQLException {
        selectUserInfo = c.prepareStatement("SELECT COUNT(*) FROM users WHERE name=?");

        selectUserInfo.setString(1, username);
        ResultSet rs = selectUserInfo.executeQuery();
        if(rs.next())
            return true;
        return false;
    }

    //Retrieving all the base information for a use from the database
    public User getUserInfo(int user_id) throws SQLException {
        selectUserInfo1 = c.prepareStatement("SELECT user_id,name,email,phoneNumber,password FROM users WHERE user_id=?");
        selectUserInfo1.setInt(1, user_id);
        ResultSet rs = selectUserInfo1.executeQuery();

        if(rs.next()) {
            User user = new User(user_id,rs.getString("name"),rs.getString("email"),rs.getString("phoneNumber"),rs.getString("password"));
            return user;
        }
        else
            return null;

    }
    //Getting the last user id from the database so I can set the next one for the next user
    public int getLastUserId() throws SQLException {
        lastLine= c.prepareStatement("select user_id from Users order by user_id desc limit 1;"); // select last id from the table user

        ResultSet rs = lastLine.executeQuery();
            if(rs.next()) {
                return rs.getInt(1); //I verify if there is at least 1 user
            }
            return 0; //If not return 0

    }

    //Adding the base user to the database

   public void add(T user) throws SQLException {
       insertUser = c.prepareStatement("insert into Users values(?,?,?,?,?,?)");

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
public boolean verifyUserExistence(String username,String password) throws SQLException {
        selectUserInfo = c.prepareStatement("SELECT password FROM users WHERE name=?");
        selectUserInfo.setString(1, username);
        ResultSet rs = selectUserInfo.executeQuery();
        if(rs.next()) {
            if (rs.getString("password").equals(password)) {
                return true;
            }
            else
            {
            return false;
            }
        }
        else
            return false;
}

public void insertInUser(User user) throws SQLException {
        insertUser = c.prepareStatement("UPDATE Users SET name = ?, email = ?, phoneNumber = ?, password = ? WHERE user_id = ?");
    insertUser.setString(1, user.getName());
    System.out.println(user.getName());
    insertUser.setString(2, user.getEmail());
    insertUser.setString(3, user.getPhoneNumber());
    insertUser.setString(4, user.getPassword());
    insertUser.setInt(5, user.getUserId());
    System.out.println(user.getUserId());
    insertUser.executeUpdate();
}

public void delete(int id) throws SQLException {
        deleteUser = c.prepareStatement("DELETE FROM users WHERE user_id=?");
        deleteUser.setInt(1, id);
        deleteUser.executeUpdate();
}


}
