package nl.oose.spotitubebackend.service;


import nl.oose.spotitubebackend.dto.TokenDTO;
import nl.oose.spotitubebackend.dto.UserDTO;
import nl.oose.spotitubebackend.persistence.TokenDAOImpl;
import nl.oose.spotitubebackend.persistence.UserDAOImpl;
import nl.oose.spotitubebackend.util.TokenGenerator;

import javax.enterprise.inject.Default;
import javax.inject.Inject;

@Default
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserDAOImpl userDAO;
    private TokenDAOImpl tokenDAOImpl;
    private TokenGenerator tokenGenerator;



    @Inject
    public AuthenticationServiceImpl(TokenDAOImpl tokenDAO, UserDAOImpl userDAO, TokenGenerator tokenGen){
        this.userDAO = userDAO;
        this.tokenDAOImpl = tokenDAO;
        this.tokenGenerator = tokenGen;
    }

    @Override
    public TokenDTO login(String username, String password) {
        String generatedToken = tokenGenerator.generateToken();
        if (generatedToken != null) {
            tokenDAOImpl.saveToken(username, generatedToken);
            return new TokenDTO(generatedToken, getNameOfUser(username, password));
        } else {
            throw new SpotitubeLoginException("Login failed for user.");
        }
    }

    public String getNameOfUser(String username, String password){
        UserDTO user = userDAO.getUser(username, password);
        System.out.println(user.getName());
        return user.getName();
    }




}
