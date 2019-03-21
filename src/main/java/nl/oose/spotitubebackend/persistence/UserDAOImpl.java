package nl.oose.spotitubebackend.persistence;

import nl.oose.spotitubebackend.dto.UserDTO;

import javax.enterprise.inject.Default;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Default
public class UserDAOImpl implements UserDAO {

    @Override
    public UserDTO getUser(String username, String password){
        UserDTO foundUser = null;
        try
                (
                        Connection connection = new ConnectionFactory().getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM account where user = ? AND password = ?")

                ){
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                foundUser = new UserDTO();
                foundUser.setUser(resultSet.getString("user"));
                foundUser.setPassword(resultSet.getString("password"));
                foundUser.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foundUser;
    }

    @Override
    public UserDTO getUserByToken(String token){
        UserDTO userDTO = null;
        try
                (
                        Connection connection = new ConnectionFactory().getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("SELECT user FROM token where auth_token = ?")

                ){
            preparedStatement.setString(1, token);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                userDTO = new UserDTO();
                userDTO.setUser(resultSet.getString("user"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userDTO;
    }
}
