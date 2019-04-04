package nl.oose.spotitubebackend.service;

import nl.oose.spotitubebackend.dto.*;
import nl.oose.spotitubebackend.persistence.TokenDAOImpl;
import nl.oose.spotitubebackend.persistence.TrackDAOImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class TrackServiceTest {

    @Mock
    private TrackDAOImpl trackDAOStub;

    @Mock
    private TokenDAOImpl tokenDAOstub;

    @InjectMocks
    private TrackServiceImpl sut;
    private TrackDTO track;
    private TracksDTO tracks;


    @BeforeEach
    void setUp(){

    }

    @Test
    void testDefaultTrackserviceConstructor(){
        TrackServiceImpl trackService = new TrackServiceImpl(trackDAOStub, tokenDAOstub);
    }
    @Test
    void getAllAvailableTracksWithValidUserToken() {
        String token = "12388233-2333";
        TracksDTO tracksList = sut.getAllTracksFromPlaylist(token, "1");
        Mockito.when(trackDAOStub.getAllTracksNotInPlaylist(1)).thenReturn(tracksList);
        TracksDTO actualResult = sut.getAllAvailableTracks(token, "1");
        assertEquals(tracksList, actualResult.getTracks());
    }

    @Test
    void getAllAvailableTrackWithUnValidToken() {
        Mockito.when(trackDAOStub.getAllTracksNotInPlaylist(anyInt()))
                .thenThrow(new SpotitubeTokenException("Invalid token or token is expired"));
        TokenDTO token = new TokenDTO("12333002", "Karelen");
        SpotitubeTokenException spotitubeTokenException = assertThrows(SpotitubeTokenException.class, () -> {
            TracksDTO actualResult = sut.getAllAvailableTracks(token.getToken(), "1");
        });

        assertEquals("Invalid token or token is expired", spotitubeTokenException.getMessage());
    }

    @Test
    void getAllTracksInPlaylist() {
        String token = "12388233-2333";
        TracksDTO tracksList = sut.getAllTracksFromPlaylist(token,"1");
        Mockito.when(trackDAOStub.getAllTracksInPlaylist("1")).thenReturn(tracksList);
        TracksDTO actualResult = sut.getAllTracksFromPlaylist(token, "1");
        assertEquals(tracksList, actualResult.getTracks());
    }

    @Test
    void getAllTracksInPlaylistWithUnvalidToken() {
        Mockito.when(trackDAOStub.getAllTracksInPlaylist(anyString()))
                .thenThrow(new SpotitubeLoginException("Invalid token or token is expired"));
        TokenDTO token = new TokenDTO("12333002", "Karelen");
        SpotitubeTokenException spotitubeTokenException = assertThrows(SpotitubeTokenException.class, () -> {
            TracksDTO actualResult = sut.getAllAvailableTracks(token.getToken(), "1");
        });

        assertEquals("Invalid token or token is expired", spotitubeTokenException.getMessage());
    }

    @Test
    void removeTrack() {
        String token = "12388233-2333";
        Mockito.when(tokenDAOstub.tokenExpired(token)).thenReturn(false);
        sut.removeTrack(token, "1", "1");
        Mockito.verify(trackDAOStub).removeTrackFromPlaylist(1,1);
    }

    @Test
    void addTrack() {
        String token = "12388233-2333";
        Mockito.when(tokenDAOstub.tokenExpired(token)).thenReturn(false);
        sut.addTrack(token, "1", 1, true);
        Mockito.verify(trackDAOStub).addTrackToPlaylist(1,1, true);
    }
}