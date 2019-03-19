package nl.oose.spotitubebackend;


import nl.oose.spotitubebackend.dto.TokenDTO;
import nl.oose.spotitubebackend.dto.UserDTO;
import nl.oose.spotitubebackend.persistence.TokenDAO;
import nl.oose.spotitubebackend.persistence.UserDAO;
import nl.oose.spotitubebackend.resources.LoginResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static  org.junit.jupiter.api.Assertions.*;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

@ExtendWith(MockitoExtension.class)
public class LoginResourceTest {

    @Mock
    private UserDAO userDAOStub;
    private TokenDAO tokenStub;

    @InjectMocks
    private LoginResource sut;

   /* @BeforeEach
    void setUp(){
        sut = new LoginResource();
    }*/


    @Test
    void loginSucces(){
        UserDTO mockedUser = new UserDTO();
        mockedUser.setUser("jorrit");
        mockedUser.setPassword("jorritPass");

        TokenDTO testToken = new TokenDTO();
        testToken.setToken("At512vfd9LeaF56");
        testToken.setUser("jorrit");

        Mockito.when(userDAOStub.getUser("jorrit", "jorritPass")).thenReturn(mockedUser);

        UserDTO userDTO = new UserDTO("jorrit", "jorritPass");

        Response actualResult = sut.loginUser(userDTO);
        assertEquals(Response.Status.OK.getStatusCode(), actualResult.getStatus());

        assertEquals("jorrit",  userDTO.getUser());
        assertEquals("At512vfd9LeaF56", testToken.getToken());

    }

    @Test
    void loginFailure(){
        UserDTO userDTO = new UserDTO("Jorit", "MyPass!23");
        Response actualResult = sut.loginUser(userDTO);
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), actualResult.getStatus());
        assertEquals("Login failed for user" + userDTO.getUser(), actualResult.getEntity());
    }
}