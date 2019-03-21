package nl.oose.spotitubebackend.service;


import nl.oose.spotitubebackend.dto.TokenDTO;
import nl.oose.spotitubebackend.dto.UserDTO;
import nl.oose.spotitubebackend.persistence.TokenDAO;
import nl.oose.spotitubebackend.persistence.UserDAO;
import nl.oose.spotitubebackend.persistence.UserDAOImpl;
import nl.oose.spotitubebackend.util.TokenGenerator;

import javax.enterprise.inject.Default;
import javax.inject.Inject;

@Default
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserDAOImpl userDAO;
    private TokenDAO tokenDAO = new TokenDAO();
    private TokenGenerator tokenGenerator = new TokenGenerator();

    public AuthenticationServiceImpl(){

    }

    @Inject
    public AuthenticationServiceImpl(UserDAOImpl userDAO){
        this.userDAO = userDAO;
    }
    @Override
    public TokenDTO login(String username, String password) {
        UserDTO user = userDAO.getUser(username, password);
        String generatedToken = tokenGenerator.generateToken();
        if (user != null) {
            tokenDAO.saveToken(username, generatedToken);
            return new TokenDTO(generatedToken, user.getName());
        } else {
            throw new SpotitubeLoginException("Login failed for user " + username);
        }
    }


}
