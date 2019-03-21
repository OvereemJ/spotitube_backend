package nl.oose.spotitubebackend.resources;




import nl.oose.spotitubebackend.dto.*;
import nl.oose.spotitubebackend.persistence.UserDAO;
import nl.oose.spotitubebackend.persistence.UserDAOImpl;
import nl.oose.spotitubebackend.service.PlaylistService;
import nl.oose.spotitubebackend.service.PlaylistServiceImpl;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/playlists")
public class PlaylistsResource {

    private PlaylistService playlistService;
    private UserDAO userDAO = new UserDAOImpl();
    @Inject
    public PlaylistsResource(PlaylistService playlistService){
        this.playlistService = playlistService;
    }


    public PlaylistsResource() {
//        playlist.add(new PlaylistDTO(1, "Death Metal", true));
//        playlist.add(new PlaylistDTO(2, "Pop", true));
//        playlistsMap.put("1", new PlaylistsDTO(playlist.subList(0,playlist.size()), 123445));
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylists(@QueryParam("token") String token){
        System.out.println(token);
        UserDTO user = userDAO.getUserByToken(token);
        PlaylistsDTO playlist = playlistService.getPlaylistByUser(token, user.getUser());
        return Response.ok(playlist).build();
    }

    @POST
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removePlaylist(@QueryParam("token") String token, @PathParam("id") int playlist_id){
        playlistService.removePlaylist(token, playlist_id);
        return Response.ok("Playlist removed").build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePlaylistName(@QueryParam("token") String token, @PathParam("id") int playlist_id, PlaylistDTO playlist) {
        playlistService.updatePlaylistName(token, playlist_id, playlist.getName());
        UserDTO user = userDAO.getUserByToken(token);
        PlaylistsDTO playlists = playlistService.getPlaylistByUser(token, user.getUser());
        return Response.ok(playlists).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") String token, PlaylistDTO playlist){
        playlistService.addPlaylist(token, playlist.getName());
        return Response.ok("New playlist added").build();
    }

}
