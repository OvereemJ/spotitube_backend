package nl.oose.spotitubebackend.resources;




import nl.oose.spotitubebackend.dto.*;
import nl.oose.spotitubebackend.persistence.PlaylistsDAO;
import nl.oose.spotitubebackend.persistence.TokenDAO;
import nl.oose.spotitubebackend.persistence.UserDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Path("playlists")
public class PlaylistsResource {
    UserDAO userDAO = new UserDAO();
    PlaylistsDAO playlistsDAO = new PlaylistsDAO();


    public PlaylistsResource() {
//        playlist.add(new PlaylistDTO(1, "Death Metal", true));
//        playlist.add(new PlaylistDTO(2, "Pop", true));
//        playlistsMap.put("1", new PlaylistsDTO(playlist.subList(0,playlist.size()), 123445));
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylists(@QueryParam("token") String token){
        System.out.println(token);
        UserDTO validToken = userDAO.getUserByToken(token);
        if(validToken != null){
            return Response.ok(playlistsDAO.getUserPlaylists(validToken.getUser())).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("User token "+ token + " is invalid").build();
        }
    }

}
