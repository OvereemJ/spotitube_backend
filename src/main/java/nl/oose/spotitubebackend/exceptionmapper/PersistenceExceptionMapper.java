package nl.oose.spotitubebackend.exceptionmapper;

import nl.oose.spotitubebackend.dto.ErrorDTO;
import nl.oose.spotitubebackend.persistence.SpotitubePersistenceException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PersistenceExceptionMapper implements ExceptionMapper<SpotitubePersistenceException> {

    @Override
    public Response toResponse(SpotitubePersistenceException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorDTO("Database connection error. Please try again later."))
                .build();
    }
}
