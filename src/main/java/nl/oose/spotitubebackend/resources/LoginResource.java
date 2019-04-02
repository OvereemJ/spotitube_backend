package nl.oose.spotitubebackend.resources;

import nl.oose.spotitubebackend.dto.TokenDTO;
import nl.oose.spotitubebackend.dto.UserDTO;
import nl.oose.spotitubebackend.service.AuthenticationService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/login")
public class LoginResource {

    private AuthenticationService authenticationService;


    public LoginResource() {

    }

    @Inject
    public LoginResource(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(UserDTO user){
        TokenDTO token = authenticationService.login(user.getUser(), user.getPassword());
        return Response.ok(token).build();

    }
}
