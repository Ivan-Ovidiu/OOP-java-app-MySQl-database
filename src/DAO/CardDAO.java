package DAO;

import Connections.ConnectionClass;
import FoodGrabClasses.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class CardDAO extends ConnectionClass {

        private Connection c;
        private PreparedStatement statement;

        //Constructor
        public CardDAO() throws SQLException {
            c = ConnectionClass.getConnection();
        }

    int getLastCardId(){
            try
            {
                statement = c.prepareStatement("select card_id from Cards order by card_id desc limit 1;");
                ResultSet rs = statement.executeQuery();
                if (rs.next())
                    return rs.getInt(1) + 1;
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }

        if (statement != null) {        //golesc statement ul
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public void setCard(Card card,int customer_id) {
        try {
            int id = getLastCardId();
            statement = c.prepareStatement("INSERT INTO Cards(card_id,customer_id,expire_date,cvv,number) VALUES (?,?,?,?,?)");
            statement.setInt(1, id);
            statement.setInt(2, customer_id);
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsedDate = sdf.parse(card.getExpireDate());
                statement.setDate(3, new java.sql.Date(parsedDate.getTime()));
            } catch (ParseException e) {
            } catch (Exception e) {
                e.printStackTrace();
            }
            statement.setInt(4, card.getCardCVV());
            statement.setString(5, card.getGetCardNumber());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


        if (statement != null) {        //golesc statement ul
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        AuditDAO.writeAudit("A new card has been added to an user");
    }

    public List<Card> getCard(int customer_id){
            try
            {
             statement = c.prepareStatement("select expire_date,cvv,number from Cards where customer_id=?");
             statement.setInt(1, customer_id);
             ResultSet rs = statement.executeQuery();
             List<Card> cards = new ArrayList<Card>();
             while(rs.next())
             {
                 Card card ;
                 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                card = new Card(rs.getString("Number"),rs.getInt("cvv"),sdf.format( rs.getDate("expire_date")));
                cards.add(card);
             }
            return cards;
            }catch(SQLException e) { e.printStackTrace(); }

        if (statement != null) {        //golesc statement ul
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        AuditDAO.writeAudit("Information about a card has been retrieved");
        return null;
    }


    public void delete(int id){
            try
            {
                statement = c.prepareStatement("delete from Cards where card_id=?");
                statement.setInt(1, id);
                statement.executeUpdate();

            }catch (SQLException e) {e.printStackTrace();}

        if (statement != null) {        //golesc statement ul
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        AuditDAO.writeAudit("Card has been deleted");
    }

    public void update(int card_id,Card card){
            try {
                statement = c.prepareStatement("UPDATE cards SET expire_date=?,cvv=?,number=? WHERE card_id=? ");
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date parsedDate = sdf.parse(card.getExpireDate());
                    statement.setDate(1, new java.sql.Date(parsedDate.getTime()));
                } catch (ParseException e) {
                } catch (Exception e) {
                    e.printStackTrace();
                }
                statement.setInt(2, card.getCardCVV());
                statement.setString(3, card.getGetCardNumber());
                statement.setInt(4, card_id);
                statement.executeUpdate();

            }catch(SQLException e) {e.printStackTrace();}

        if (statement != null) {        //golesc statement ul
            try {
                statement.close();
            } catch (SQLException e) {e.printStackTrace();}
        }
        AuditDAO.writeAudit("Card has been updated");
    }

    public void getAllCardIds(int customer_id){
            List<Integer> ids = new ArrayList<>();
            try{
                statement = c.prepareStatement("select card_id from Cards where customer_id=?");
                statement.setInt(1, customer_id);
                ResultSet rs = statement.executeQuery();
                while(rs.next())
                {
                    int id = rs.getInt(1);
                    ids.add(id);
                }
                System.out.println(ids);

            }catch(SQLException e) {e.printStackTrace();}

        if (statement != null) {        //golesc statement ul
            try {
                statement.close();
            } catch (SQLException e) {e.printStackTrace();}
        }
    }



}
