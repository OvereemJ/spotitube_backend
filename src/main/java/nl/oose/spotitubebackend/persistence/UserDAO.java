package nl.oose.spotitubebackend.persistence;

import nl.oose.spotitubebackend.dto.UserDTO;

public interface UserDAO {
    UserDTO getUser(String username, String password);

    UserDTO getUserByToken(String token);
}
