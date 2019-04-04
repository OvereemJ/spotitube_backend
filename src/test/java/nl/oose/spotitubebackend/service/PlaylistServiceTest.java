package nl.oose.spotitubebackend.service;

import nl.oose.spotitubebackend.dto.PlaylistDTO;
import nl.oose.spotitubebackend.dto.PlaylistsDTO;
import nl.oose.spotitubebackend.dto.TokenDTO;
import nl.oose.spotitubebackend.persistence.PlaylistsDAOImpl;
import nl.oose.spotitubebackend.persistence.TokenDAO;
import nl.oose.spotitubebackend.persistence.TokenDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class PlaylistServiceTest {

    @Mock
    private PlaylistsDAOImpl playlistsStub;

    @Mock
    private TokenDAOImpl tokenDAO;

    @InjectMocks
    private PlaylistServiceImpl sut;


    @Test
    void testDefaultPlaylistServiceConstructor(){
        PlaylistServiceImpl playlistService = new PlaylistServiceImpl(playlistsStub, tokenDAO);
    }

    @Test
        void getPlaylistWithValidUserToken() {
        String token = "5f8f8bc7-d2c9-4523-be98-d4848b8f9be6";
        Mockito.when(tokenDAO.tokenExpired(token)).thenReturn(false);
        PlaylistsDTO playlist = new PlaylistsDTO();
        Mockito.when(playlistsStub.getUserPlaylists(token)).thenReturn(playlist);
        PlaylistsDTO actualResult = sut.getPlaylistByToken(token);
        assertEquals(playlist, actualResult);
    }

    @Test
    void getPlaylistWithUnValidUserToken() {
        Mockito.when(playlistsStub.getUserPlaylists(anyString()))
                .thenThrow(new SpotitubeTokenException("Invalid token or token is expired"));
        TokenDTO token = new TokenDTO("12333002", "Jorrit Overeem");
        SpotitubeTokenException spotitubeTokenException = assertThrows(SpotitubeTokenException.class, () -> {
            PlaylistsDTO actualResult = sut.getPlaylistByToken(token.getToken());
        });

        assertEquals("Invalid token or token is expired", spotitubeTokenException.getMessage());
    }

    @Test
    void removePlaylist() {
        String token = "12388233-2333";
        Mockito.when(tokenDAO.tokenExpired(token)).thenReturn(false);
        sut.removePlaylist(token,1);
        Mockito.verify(playlistsStub).removePlaylistFromDatabase(1);
    }

    @Test
    void addPlaylist() {
        String token = "12388233-2333";
        Mockito.when(tokenDAO.tokenExpired(token)).thenReturn(false);
        sut.addPlaylist(token, "jorrit");
        Mockito.verify(playlistsStub).addPlaylistToDatabase(token, "jorrit");

    }

    @Test
    void updatePlaylistName() {
        String token = "12388233-2333";
        Mockito.when(tokenDAO.tokenExpired(token)).thenReturn(false);
        sut.updatePlaylistName(token,1, "ArimbaList");
        Mockito.verify(playlistsStub).updatePlaylist(1, "ArimbaList");
    }




}
