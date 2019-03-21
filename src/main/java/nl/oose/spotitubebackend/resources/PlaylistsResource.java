package nl.oose.spotitubebackend.resources;




import nl.oose.spotitubebackend.dto.*;
import nl.oose.spotitubebackend.persistence.PlaylistsDAO;
import nl.oose.spotitubebackend.persistence.TokenDAO;
import nl.oose.spotitubebackend.persistence.UserDAO;
import nl.oose.spotitubebackend.service.PlaylistServiceImpl;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Path("playlists")
public class PlaylistsResource {

    PlaylistServiceImpl playlistService = new PlaylistServiceImpl();


    public PlaylistsResource() {
//        playlist.add(new PlaylistDTO(1, "Death Metal", true));
//        playlist.add(new PlaylistDTO(2, "Pop", true));
//        playlistsMap.put("1", new PlaylistsDTO(playlist.subList(0,playlist.size()), 123445));
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylists(@QueryParam("token") String token){
        PlaylistsDTO playlist = playlistService.getPlaylistByToken(token);
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
        PlaylistsDTO playlists = playlistService.getPlaylistByToken(token);
        return Response.ok(playlists).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylist(@QueryParam("token") String token, PlaylistDTO playlist){
        playlistService.addPlaylist(token, playlist.getId(), playlist.getName(),playlist.getUser());
        return Response.ok("New playlist added").build();
    }

}
