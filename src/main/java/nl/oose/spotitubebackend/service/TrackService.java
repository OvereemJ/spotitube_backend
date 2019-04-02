package nl.oose.spotitubebackend.service;


import nl.oose.spotitubebackend.dto.TracksDTO;



public interface TrackService {
     TracksDTO getAllAvailableTracks (String token, String playlist_id);

     TracksDTO getAllTracksFromPlaylist(String token, String playlist_id);

     void addTrack(String token, String playlist_id, int track_id, boolean offlineAvailable);

     void removeTrack(String token, String playlist_id, String trackid);
}
