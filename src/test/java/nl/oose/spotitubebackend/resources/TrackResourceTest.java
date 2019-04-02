package nl.oose.spotitubebackend.resources;

import nl.oose.spotitubebackend.dto.*;
import nl.oose.spotitubebackend.service.TrackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TrackResourceTest {

    @Mock
    private TrackService trackservice;


    @InjectMocks
    private TrackResource sut;
    private TrackDTO track;
    private TracksDTO tracks;
    private PlaylistsDTO playlists;
    private PlaylistDTO playlist;

    @BeforeEach
    void setUp(){
        playlist = new PlaylistDTO(1, "Pop", true);
        track = new TrackDTO(1, "MyTitle",  "MyPerformer",
                2133, "MyAlbum", 2,
                "23-09-2012", "MyBestTrack", true);
        tracks = new TracksDTO();
    }

    @Test
    void testDefaultTrackResourceConstructor(){
        TrackResource trackResource = new TrackResource();
    }


    @Test
    void getAllTracksForPlaylist(){
        String token = "1234-1234-1234";
        createTracklistDAOMock();
        Response actualResult = sut.getAllTracks(token, String.valueOf(playlist.getId()));
        assertEquals(Response.Status.OK.getStatusCode(), actualResult.getStatus());
        TracksDTO actualTracklist = (TracksDTO) actualResult.getEntity();
        assertEquals(actualTracklist, actualResult.getEntity());
    }

    void createTracklistDAOMock(){
        Mockito.when(trackservice.getAllAvailableTracks("1234-1234-1234", "1")).thenReturn(tracks);
    }




}
