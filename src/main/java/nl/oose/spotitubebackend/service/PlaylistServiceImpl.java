package nl.oose.spotitubebackend.service;

import nl.oose.spotitubebackend.dto.PlaylistsDTO;
import nl.oose.spotitubebackend.dto.UserDTO;
import nl.oose.spotitubebackend.persistence.PlaylistsDAOImpl;
import nl.oose.spotitubebackend.persistence.TokenDAOImpl;
import nl.oose.spotitubebackend.persistence.UserDAOImpl;

import javax.inject.Inject;
import java.awt.image.TileObserver;

public class PlaylistServiceImpl implements PlaylistService {
    TokenDAOImpl tokenDAOImpl;
    PlaylistsDAOImpl playlistsDAO;


    @Inject
    public PlaylistServiceImpl(PlaylistsDAOImpl playlistsDAO, TokenDAOImpl tokenDAO){
        this.playlistsDAO = playlistsDAO;
        this.tokenDAOImpl = tokenDAO;
    }

    @Override
    public PlaylistsDTO getPlaylistByToken(String token) {
        //TODO Get playlist via token
        if(tokenDAOImpl.tokenExpired(token) == false){
            return playlistsDAO.getUserPlaylists(token);
        } else {
            throw new SpotitubeTokenException("Can't create playlist, "+token+" is invalid");
        }
    }

    @Override
    public void removePlaylist(String token, int playlistid) {
        if(!tokenDAOImpl.tokenExpired(token)){
            playlistsDAO.removePlaylistFromDatabase(playlistid);
        } else {
            throw new SpotitubeTokenException("Invalid token or token is expired");
        }
    }

    @Override
    public void addPlaylist(String token, String name) {
        if( tokenDAOImpl.tokenExpired(token) == false){
            playlistsDAO.addPlaylistToDatabase(token, name);
        } else {
            throw new SpotitubeTokenException("Invalid token or token is expired");
        }

    }

    @Override
    public void updatePlaylistName(String token, int playlist_id, String name) {
        if( tokenDAOImpl.tokenExpired(token) == false){
            playlistsDAO.updatePlaylist(playlist_id, name);
        } else {
            throw new SpotitubeTokenException("Invalid token or token is expired");
        }

    }
}
