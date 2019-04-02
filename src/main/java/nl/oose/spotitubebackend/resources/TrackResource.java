package nl.oose.spotitubebackend.resources;

import nl.oose.spotitubebackend.dto.TracksDTO;
import nl.oose.spotitubebackend.service.TrackService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tracks")
public class TrackResource {

    private TrackService trackService;

    @Inject
    public TrackResource(TrackService trackService){
        this.trackService = trackService;
    }


    public TrackResource() {

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTracks(@QueryParam("token") String token, @QueryParam("forPlaylist") String playlist_id){
        TracksDTO tracksDTO = trackService.getAllAvailableTracks(token, playlist_id);
        return Response.ok(tracksDTO).build();
    }

}
