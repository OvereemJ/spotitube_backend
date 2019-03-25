package nl.oose.spotitubebackend.service;

import nl.oose.spotitubebackend.dto.PlaylistDTO;
import nl.oose.spotitubebackend.dto.TracksDTO;

import java.util.List;


public interface TrackService {
     TracksDTO getAllAvailableTracks (String token, String playlist_id);

     TracksDTO getAllTracksFromPlaylist(String token, String playlist_id);

     void addTrack(String token, String playlist_id, int track_id);

     void removeTrack(String token, String playlist_id, String trackid);
}
