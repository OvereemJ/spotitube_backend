package nl.oose.spotitubebackend.service;

import nl.oose.spotitubebackend.dto.TracksDTO;
import nl.oose.spotitubebackend.dto.UserDTO;
import nl.oose.spotitubebackend.persistence.TokenDAOImpl;
import nl.oose.spotitubebackend.persistence.TrackDAOImpl;
import nl.oose.spotitubebackend.persistence.UserDAOImpl;

import javax.inject.Inject;

public class TrackServiceImpl implements TrackService{
    private TrackDAOImpl trackDAOImpl;
    TokenDAOImpl tokenDAOImpl = new TokenDAOImpl();
    UserDAOImpl userDAOImpl = new UserDAOImpl();

    @Inject
    public TrackServiceImpl(TrackDAOImpl trackDAOImpl){
        this.trackDAOImpl = trackDAOImpl;
    }

    @Override
    public TracksDTO getAllAvailableTracks(String token, String playlist_id) {
        UserDTO user = userDAOImpl.getUserByToken(token);
        if(user != null || tokenDAOImpl.tokenExpired(token) == false){
            return trackDAOImpl.getAllTracksNotInPlaylist(Integer.parseInt(playlist_id));
        } else {
            throw new SpotitubeTokenException("Invalid token or token is expired");
        }
    }

    @Override
    public TracksDTO getAllTracksFromPlaylist(String token, String playlist_id) {
        UserDTO user = userDAOImpl.getUserByToken(token);
        if(user != null || tokenDAOImpl.tokenExpired(token) == false){
            return trackDAOImpl.getAllTracksInPlaylist(playlist_id);
        } else {
            throw new SpotitubeTokenException("Invalid token or token is expired");
        }
    }

    @Override
    public void addTrack(String token, String playlist_id, int track_id, boolean offlineAvailable) {
        UserDTO user = userDAOImpl.getUserByToken(token);
        if(user != null || tokenDAOImpl.tokenExpired(token) == false){
            trackDAOImpl.addTrackToPlaylist(Integer.parseInt(playlist_id),track_id, offlineAvailable);
        } else {
            throw new SpotitubeTokenException("Invalid token or token is expired");
        }
    }

    @Override
    public void removeTrack(String token, String playlist_id, String trackid) {
        UserDTO user = userDAOImpl.getUserByToken(token);
        if(user != null || tokenDAOImpl.tokenExpired(token) == false){
            trackDAOImpl.removeTrackFromPlaylist(Integer.parseInt(playlist_id), Integer.parseInt(trackid));
        } else {
            throw new SpotitubeTokenException("Invalid token or token is expired");
        }
    }

}
