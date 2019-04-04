package nl.oose.spotitubebackend.exceptionmapper;

import nl.oose.spotitubebackend.dto.ErrorDTO;
import nl.oose.spotitubebackend.persistence.SpotitubePersistenceException;
import nl.oose.spotitubebackend.service.SpotitubeLoginException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExceptionMapperTest {
    private PersistenceExceptionMapper sut;
    private LoginExceptionMapper sutLogin;

    @BeforeEach
    void setUp() {
        sut = new PersistenceExceptionMapper();
        sutLogin = new LoginExceptionMapper();
    }

    @Test
    void persistenceExceptionsReturnInternalServerErrorAndNoFoundErrormessage() {
        Response actualResult = sutLogin.toResponse(new SpotitubeLoginException("User not authorized"));
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), actualResult.getStatus());

        ErrorDTO actualError = (ErrorDTO) actualResult.getEntity();
        assertEquals("User not authorized", actualError.getMessage());
    }

    @Test
    void persistenceExceptionsReturnInternalServerErrorAndDatabaseConnectionErrormessage() {
        Response actualResult = sut.toResponse(new SpotitubePersistenceException(new SQLException("Ingewikkelde message")));
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), actualResult.getStatus());

        ErrorDTO actualError = (ErrorDTO) actualResult.getEntity();
        assertEquals("Database connection error. Please try again later.", actualError.getMessage());
    }
}
