package nl.oose.spotitubebackend.service;

import nl.oose.spotitubebackend.dto.PlaylistDTO;
import nl.oose.spotitubebackend.dto.PlaylistsDTO;
import nl.oose.spotitubebackend.dto.UserDTO;
import nl.oose.spotitubebackend.persistence.PlaylistsDAO;
import nl.oose.spotitubebackend.persistence.UserDAO;

import javax.ws.rs.core.Response;

public class PlaylistServiceImpl implements PlaylistService {

    UserDAO userDAO = new UserDAO();
    PlaylistsDAO playlistsDAO = new PlaylistsDAO();

    @Override
    public PlaylistsDTO getPlaylistByToken(String token) {
        //TODO Get playlist via token
        UserDTO validToken = userDAO.getUserByToken(token);
        if(validToken != null){
            return playlistsDAO.getUserPlaylists(token);
        } else {
            throw new SpotitubePlaylistException("Can't create playlist, "+token+" is invalid");
        }
    }

    @Override
    public void removePlaylist(String token, int playlistid) {
        UserDTO validToken = userDAO.getUserByToken(token);
        if(validToken != null){
            playlistsDAO.removePlaylist(playlistid);
        } else {
            throw new SpotitubePlaylistException("Can't create playlist, "+token+" is invalid");
        }
    }

    @Override
    public void addPlaylist(String token, int playlistid, String name, String user) {
        UserDTO validToken = userDAO.getUserByToken(token);
        if(validToken != null){
            playlistsDAO.addPlaylist(playlistid, name, user);
        } else {
            throw new SpotitubePlaylistException("Can't create playlist, "+token+" is invalid");
        }

    }

    @Override
    public void updatePlaylistName(String token, int playlist_id, String name) {
        UserDTO validToken = userDAO.getUserByToken(token);
        if(validToken != null){
            playlistsDAO.updatePlaylistName(playlist_id, name);
        } else {
            throw new SpotitubePlaylistException("Can't create playlist, "+token+" is invalid");
        }

    }
}
