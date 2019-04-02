package nl.oose.spotitubebackend.resources;

import nl.oose.spotitubebackend.dto.*;

import nl.oose.spotitubebackend.service.*;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlaylistResourceTest {

    @Mock
    private TrackService trackservice;

    @Mock
    private PlaylistService playlistService;


    @InjectMocks
    private PlaylistsResource sut;
    private TrackDTO track;
    private TracksDTO tracks;
    private PlaylistsDTO playlists;
    private PlaylistDTO playlist;
    private UserDTO user;

    @BeforeEach
    void setUp(){
        playlist = new PlaylistDTO(1, "Pop", true);
        track = new TrackDTO(1, "MyTitle",  "MyPerformer",
                2133, "MyAlbum", 2,
                "23-09-2012", "MyBestTrack", true);
        tracks = new TracksDTO();
        playlists = new PlaylistsDTO();
        user = new UserDTO("Jorrit", "Jorritpass");
        playlists.setLength(1233);
        playlists.addPlaylist(playlist);


    }

    @Test
    void testDefaultPlaylistResourceConstructor(){
        PlaylistsResource playlistsResource = new PlaylistsResource();
    }

    @Test
    void showPlayListWithCorrectToken(){
        String token = "1234-1234-1234";
        createPlaylistDAOMock(token);

        Response actualResult = sut.getAllPlaylists(token);
        assertEquals(Response.Status.OK.getStatusCode(), actualResult.getStatus());
       PlaylistsDTO actualPlaylist = (PlaylistsDTO) actualResult.getEntity();
        assertEquals(1233, actualPlaylist.getLength());
    }

    @Test
    void throwExceptionWithIncorrectToken(){

        String token ="1234-1234-231";
        when(playlistService.getPlaylistByUser(anyString())).thenThrow(
                new SpotitubeTokenException("Can't create playlist, "+token+" is invalid"));
        SpotitubeTokenException spotitubePlaylistException = assertThrows(SpotitubeTokenException.class, () -> {
            Response actualResult = sut.getAllPlaylists(token);
        });

        assertEquals("Can't create playlist, "+ token+ " is invalid", spotitubePlaylistException.getMessage());
    }

    @Test
    void removePlaylist(){
        String token = "1234-1234-1234";
        createPlaylistDAOMock(token);
        Response actualResult = sut.removePlaylist(token, playlist.getId());
        assertEquals(Response.Status.OK.getStatusCode(), actualResult.getStatus());
    }

    @Test
    void updatePlaylist(){
        String token = "1234-1234-1234";
        createPlaylistDAOMock(token);
        Response actualResult = sut.updatePlaylistName(token, playlist.getId(), playlist);
        playlistService.updatePlaylistName(token, playlist.getId(), playlist.getName());
        assertEquals(Response.Status.OK.getStatusCode(), actualResult.getStatus());
    }

    @Test
    void addPlaylist(){
        String token = "1234-1234-1234";
        createPlaylistDAOMock(token);
        Response actualResult = sut.addPlaylist(token, playlist);
        playlistService.addPlaylist(token, playlist.getName());
        assertEquals(Response.Status.OK.getStatusCode(), actualResult.getStatus());
        PlaylistsDTO actualPlaylist = (PlaylistsDTO) actualResult.getEntity();
        assertEquals(actualPlaylist, actualResult.getEntity());
    }


    @Test
    void getAllTracksForPlaylist(){
        String token = "1234-1234-1234";
        createTracklistDAOMock();
        Response actualResult = sut.getAllTracksForPlaylist(token, String.valueOf(playlist.getId()));
        assertEquals(Response.Status.OK.getStatusCode(), actualResult.getStatus());
        TracksDTO actualTracklist = (TracksDTO) actualResult.getEntity();
        assertEquals(actualTracklist, actualResult.getEntity());
    }

    @Test
    void removeTrackInPlaylist(){
        String token = "1234-1234-1234";
        createTracklistDAOMock();
        Response actualResult = sut.removeTrack(token, String.valueOf(playlist.getId()), String.valueOf(track.getId()));
        trackservice.removeTrack(token, String.valueOf(playlist.getId()), String.valueOf(track.getId()));
        assertEquals(Response.Status.OK.getStatusCode(), actualResult.getStatus());
        TracksDTO actualTracklist = (TracksDTO) actualResult.getEntity();
        assertEquals(actualTracklist, actualResult.getEntity());
    }
    @Test
    void addTrack(){
        String token = "1234-1234-1234";
        createTracklistDAOMock();
        Response actualResult = sut.addTrack(token, String.valueOf(playlist.getId()), track);
        trackservice.addTrack(token, String.valueOf(playlist.getId()), track.getId(), track.getOfflineAvailable());
        assertEquals(Response.Status.OK.getStatusCode(), actualResult.getStatus());
        TracksDTO actualTracklist = (TracksDTO) actualResult.getEntity();
        assertEquals(actualTracklist, actualResult.getEntity());
    }

    void createPlaylistDAOMock(String token){
        Mockito.when(playlistService.getPlaylistByUser(token)).thenReturn(playlists);
    }

    void createTracklistDAOMock(){
        Mockito.when(trackservice.getAllTracksFromPlaylist("1234-1234-1234", "1")).thenReturn(tracks);
    }




}
