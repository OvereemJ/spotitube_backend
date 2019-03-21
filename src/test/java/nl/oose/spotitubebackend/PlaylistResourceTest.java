package nl.oose.spotitubebackend;

import nl.oose.spotitubebackend.dto.*;

import nl.oose.spotitubebackend.persistence.PlaylistsDAO;
import nl.oose.spotitubebackend.resources.PlaylistsResource;
import nl.oose.spotitubebackend.service.AuthenticationService;
import nl.oose.spotitubebackend.service.PlaylistService;
import nl.oose.spotitubebackend.service.SpotitubeLoginException;
import nl.oose.spotitubebackend.service.SpotitubePlaylistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlaylistResourceTest {

    @Mock
    private PlaylistService playlistService;

    @Mock
    private PlaylistsDAO playlistDAOstub;

    @InjectMocks
    private PlaylistsResource sut;
    private PlaylistsDTO playlists;
    private PlaylistDTO playlist;

    @BeforeEach
    void setUp(){
        playlist = new PlaylistDTO(2, "Pop", "Jorrit");
        playlists = new PlaylistsDTO();
        playlists.setLengthInSeconds(12334);
        playlists.addPlaylist(playlist);
    }


    @Test
    void showPlayListWithCorrectToken(){
        createPlaylistDAOMock();
        String token = "1234-1234-1234";
        Response actualResult = sut.getAllPlaylists(token);
        assertEquals(Response.Status.OK.getStatusCode(), actualResult.getStatus());
        PlaylistsDTO actualPlaylist = (PlaylistsDTO) actualResult.getEntity();
        assertEquals(playlists.getLengthInSeconds(), actualPlaylist.getLengthInSeconds());
    }

    @Test
    void showPlayListWithIncorrectToken(){
        String token = "1234-12398-1234";
        when(playlistService.getPlaylistByToken(anyString())).thenThrow(
                new SpotitubePlaylistException("Can't create playlist, "+token+" is invalid"));
        SpotitubePlaylistException spotitubePlaylistException = assertThrows(SpotitubePlaylistException.class, () -> {
            Response actualResult = sut.getAllPlaylists(token);
        });

        assertEquals("Can't create playlist, "+token+" is invalid", spotitubePlaylistException.getMessage());
    }

    void createPlaylistDAOMock(){
        Mockito.when(playlistDAOstub.getUserPlaylists("1234-1234-1234")).thenReturn(playlists);
    }

}
