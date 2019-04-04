package nl.oose.spotitubebackend.resources;




import nl.oose.spotitubebackend.dto.*;
import nl.oose.spotitubebackend.persistence.UserDAO;
import nl.oose.spotitubebackend.persistence.UserDAOImpl;
import nl.oose.spotitubebackend.service.PlaylistService;
import nl.oose.spotitubebackend.service.PlaylistServiceImpl;
import nl.oose.spotitubebackend.service.TrackService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/playlists")
public class PlaylistsResource {

    private PlaylistService playlistService;
    private TrackService trackService;

    @Inject
    public PlaylistsResource(PlaylistService playlistService, TrackService trackService){
        this.trackService = trackService;
        this.playlistService = playlistService;
    }


    public PlaylistsResource() {

    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylists(@QueryParam("token") String token){
        PlaylistsDTO playlist = playlistService.getPlaylistByToken(token);
        return Response.ok(playlist).build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removePlaylist(@QueryParam("token") String token, @PathParam("id") int playlist_id){
        playlistService.removePlaylist(token, playlist_id);
        PlaylistsDTO playlist = playlistService.getPlaylistByToken(token);
        return Response.ok(playlist).build();
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
        playlistService.addPlaylist(token, playlist.getName());
        PlaylistsDTO playlists = playlistService.getPlaylistByToken(token);
        return Response.ok(playlists).build();
    }


    @GET
    @Path("{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracksForPlaylist(@QueryParam("token") String token, @PathParam("id") String playlist_id){
        TracksDTO tracksDTO = trackService.getAllTracksFromPlaylist(token, playlist_id);
        return Response.ok(tracksDTO).build();
    }

    @DELETE
    @Path("{playlist_id}/tracks/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeTrack(@QueryParam("token") String token, @PathParam("playlist_id") String playlist_id, @PathParam("id") String track_id){
        trackService.removeTrack(token, playlist_id, track_id);
        TracksDTO tracksDTO = trackService.getAllTracksFromPlaylist(token, playlist_id);
        return Response.ok(tracksDTO).build();
    }

    @POST
    @Path("{id}/tracks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrack(@QueryParam("token") String token, @PathParam("id") String playlist_id ,TrackDTO track){
        trackService.addTrack(token, playlist_id, track.getId(), track.getOfflineAvailable());
        TracksDTO tracksDTO = trackService.getAllTracksFromPlaylist(token, playlist_id);
        return Response.ok(tracksDTO).build();
    }

}
