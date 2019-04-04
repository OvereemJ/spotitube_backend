package nl.oose.spotitubebackend.persistence;

import nl.oose.spotitubebackend.dto.TokenDTO;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
public class TokenDAOImpl implements TokenDAO {

    @Override
    public void saveToken(String username, String token){

        try
                (
                        Connection connection = new ConnectionFactory().getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO token (auth_token, user, creationDate) VALUES (?,?,?)")

                ){
            preparedStatement.setString(1, token);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, getCurrentTimeStamp());
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean tokenExpired(String token){
        boolean expired = false;
        Date creationDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try
                (
                        Connection connection = new ConnectionFactory().getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("SELECT creationDate FROM token where auth_token = ?")
                ){
            preparedStatement.setString(1, token);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                creationDate = sdf.parse(resultSet.getString("creationDate"));
            }

            if(creationDate.compareTo(sdf.parse(getCurrentTimeStamp())) < 0){
                expired = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return expired;
    }

    public String getCurrentTimeStamp(){
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date = new Date();
        return dateFormat.format(date);
    }

}
