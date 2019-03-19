package nl.oose.spotitubebackend.resources;

import nl.oose.spotitubebackend.dto.ErrorDTO;
import nl.oose.spotitubebackend.dto.TokenDTO;
import nl.oose.spotitubebackend.dto.UserDTO;
import nl.oose.spotitubebackend.persistence.TokenDAO;
import nl.oose.spotitubebackend.persistence.UserDAO;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("login")
public class LoginResource {
    UserDAO userDAO = new UserDAO();
    TokenDAO tokenDAO = new TokenDAO();

    public LoginResource() {

    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginUser(UserDTO user){
        UserDTO authorizedUser = userDAO.getUser(user.getUser(), user.getPassword());
        tokenDAO.saveToken(user.getUser(), user.getNewUserToken(13));
        TokenDTO savedToken = tokenDAO.getTokenByUser(user.getUser());
        if(authorizedUser != null){
            return Response.ok(new TokenDTO(savedToken.getToken(), user.getName())).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Login failed for user" + user.getUser()).build();
        }

    }
}
