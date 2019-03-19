package nl.oose.spotitubebackend.persistence;

import nl.oose.spotitubebackend.dto.TokenDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TokenDAO {

    public void saveToken(String username, String token){
        try
                (
                        Connection connection = new ConnectionFactory().getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO token (auth_token, user) VALUES (?,?)")

                ){
            preparedStatement.setString(1, token);
            preparedStatement.setString(2, username);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public TokenDTO getTokenByUser(String user){
        TokenDTO tokenDTO = null;
        try
                (
                        Connection connection = new ConnectionFactory().getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("SELECT auth_token FROM token where user = ?")

                ){
            preparedStatement.setString(1, user);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                tokenDTO = new TokenDTO();
                tokenDTO.setToken(resultSet.getString("auth_token"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tokenDTO;
    }

}
