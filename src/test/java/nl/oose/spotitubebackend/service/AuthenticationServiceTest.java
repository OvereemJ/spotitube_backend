package nl.oose.spotitubebackend.service;

import nl.oose.spotitubebackend.dto.TokenDTO;
import nl.oose.spotitubebackend.dto.UserDTO;
import nl.oose.spotitubebackend.persistence.TokenDAO;
import nl.oose.spotitubebackend.persistence.TokenDAOImpl;
import nl.oose.spotitubebackend.persistence.UserDAO;
import nl.oose.spotitubebackend.persistence.UserDAOImpl;
import nl.oose.spotitubebackend.util.TokenGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private TokenDAOImpl tokenDAOStub;

    @Mock
    private UserDAOImpl userDAOStub;

    @Mock
    private TokenGenerator generatorStub;

    @InjectMocks
    private AuthenticationServiceImpl sut;

    @Test
    void testDefaultLoginResourceConstructor(){
        AuthenticationServiceImpl authenticationService = new AuthenticationServiceImpl(tokenDAOStub, userDAOStub, generatorStub);
    }
    @Test
    void loginSuccess() {
        String token = "12388233-2333";
        Mockito.when(userDAOStub.getUser("jorrit", "jorrit123"))
                    .thenReturn(new UserDTO());
            Mockito.when(generatorStub.generateToken())
                    .thenReturn(token);
            doNothing().when(tokenDAOStub).saveToken("jorrit", token);

            TokenDTO actualResult = sut.login("jorrit", "jorrit123");

            assertEquals(token, actualResult.getToken());
    }



    @Test
    void loginFailure() {
        Mockito.when(userDAOStub.getUser(anyString(), anyString()))
                .thenThrow(new SpotitubeLoginException("Login failed for user."));
        UserDTO userDTO = new UserDTO("Uwe", "WrongPassword");
        SpotitubeLoginException spotitubeLoginException = assertThrows(SpotitubeLoginException.class, () -> {
        });

        assertEquals("Login failed for user.", spotitubeLoginException.getMessage());
    }


}
