package nl.oose.spotitubebackend;

import nl.oose.spotitubebackend.dto.PlaylistsDTO;
import nl.oose.spotitubebackend.dto.PlaylistDTO;

import nl.oose.spotitubebackend.dto.TokenDTO;
import nl.oose.spotitubebackend.dto.TrackDTO;
import nl.oose.spotitubebackend.resources.PlaylistsResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlaylistResourceTest {

    private PlaylistsResource sut;

    @BeforeEach
    void setUp(){
        sut = new PlaylistsResource();
        List<PlaylistDTO> playlist = new ArrayList<>();
        List<TrackDTO> tracklist = new ArrayList<>();
        Map<String, PlaylistsDTO> playlistsMap = new HashMap<>();
        playlist.add(new PlaylistDTO(1, "Death Metal", true));
        playlist.add(new PlaylistDTO(2, "Pop", true));
        playlistsMap.put("1", new PlaylistsDTO(playlist.subList(0,playlist.size()), 123445));
    }


    @Test
    void showPlayListWithCorrectToken(){
        String token = "1234-1234-1234";
        Response actualResult = sut.getAllPlaylists(token);
        assertEquals(Response.Status.OK.getStatusCode(), actualResult.getStatus());


    }

    @Test
    void showPlayListWithIncorrectToken(){
        String token = "1234-12398-1234";
        Response actualResult = sut.getAllPlaylists(token);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), actualResult.getStatus());
    }

}
