package nl.oose.spotitubebackend.resources;


import nl.oose.spotitubebackend.dto.TokenDTO;
import nl.oose.spotitubebackend.dto.UserDTO;
import nl.oose.spotitubebackend.service.AuthenticationService;
import nl.oose.spotitubebackend.service.SpotitubeLoginException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static  org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import javax.ws.rs.core.Response;

@ExtendWith(MockitoExtension.class)
public class LoginResourceTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private LoginResource sut;

    @Test
    void testDefaultLoginResourceConstructor(){
        LoginResource loginResource = new LoginResource();
    }

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void loginSuccess() {
        when(authenticationService.login("Jorrit", "uwepass"))
                .thenReturn(new TokenDTO("1234", "Testuser"));

        UserDTO userDTO = new UserDTO("Jorrit", "uwepass");
        Response actualResult = sut.loginUser(userDTO);

        assertEquals(Response.Status.OK.getStatusCode(), actualResult.getStatus());

        TokenDTO actualToken = (TokenDTO) actualResult.getEntity();
        assertEquals("Testuser", actualToken.getUser());
        assertEquals("1234", actualToken.getToken());
    }

    @Test
    void loginFailure() {
        when(authenticationService.login(anyString(), anyString()))
                .thenThrow(new SpotitubeLoginException("Login failed for user."));
        UserDTO userDTO = new UserDTO("Uwe", "WrongPassword");
        SpotitubeLoginException spotitubeLoginException = assertThrows(SpotitubeLoginException.class, () -> {
            Response actualResult = sut.loginUser(new UserDTO("uwe", "uwepass"));
        });

        assertEquals("Login failed for user.", spotitubeLoginException.getMessage());
    }

}