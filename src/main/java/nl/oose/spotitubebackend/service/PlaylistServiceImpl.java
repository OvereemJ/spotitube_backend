package nl.oose.spotitubebackend.service;

import nl.oose.spotitubebackend.dto.PlaylistsDTO;
import nl.oose.spotitubebackend.dto.UserDTO;
import nl.oose.spotitubebackend.persistence.PlaylistsDAOImpl;
import nl.oose.spotitubebackend.persistence.TokenDAOImpl;
import nl.oose.spotitubebackend.persistence.UserDAOImpl;

import javax.inject.Inject;

public class PlaylistServiceImpl implements PlaylistService {
    TokenDAOImpl tokenDAOImpl = new TokenDAOImpl();
    UserDAOImpl userDAOImpl = new UserDAOImpl();
    PlaylistsDAOImpl playlistsDAO;


    @Inject
    public PlaylistServiceImpl(PlaylistsDAOImpl playlistsDAO){
        this.playlistsDAO = playlistsDAO;
    }

    @Override
    public PlaylistsDTO getPlaylistByUser(String token) {
        //TODO Get playlist via token
        UserDTO validToken = userDAOImpl.getUserByToken(token);
        if(validToken != null){
            return playlistsDAO.getUserPlaylists(token);
        } else {
            throw new SpotitubeTokenException("Can't create playlist, "+token+" is invalid");
        }
    }

    @Override
    public void removePlaylist(String token, int playlistid) {
        UserDTO user  = userDAOImpl.getUserByToken(token);
        if(user != null || tokenDAOImpl.tokenExpired(token) == false){
            playlistsDAO.removePlaylistFromDatabase(playlistid);
        } else {
            throw new SpotitubeTokenException("Invalid token or token is expired");
        }
    }

    @Override
    public void addPlaylist(String token, String name) {
        UserDTO user = userDAOImpl.getUserByToken(token);
        if(user != null || tokenDAOImpl.tokenExpired(token) == false){
            playlistsDAO.addPlaylistToDatabase(name, user.getUser());
        } else {
            throw new SpotitubeTokenException("Invalid token or token is expired");
        }

    }

    @Override
    public void updatePlaylistName(String token, int playlist_id, String name) {
        UserDTO user = userDAOImpl.getUserByToken(token);
        if(user != null || tokenDAOImpl.tokenExpired(token) == false){
            playlistsDAO.updatePlaylist(playlist_id, name);
        } else {
            throw new SpotitubeTokenException("Invalid token or token is expired");
        }

    }
}
