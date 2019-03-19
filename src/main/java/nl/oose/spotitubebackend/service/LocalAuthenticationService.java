package nl.oose.spotitubebackend.service;


import nl.oose.spotitubebackend.dto.TokenDTO;

import javax.enterprise.inject.Alternative;

@Alternative
public class LocalAuthenticationService implements AuthenticationService {

    @Override
    public TokenDTO login(String username, String password) {
        return new TokenDTO("1234", username);
    }
}
