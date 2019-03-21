package nl.oose.spotitubebackend.service;

import nl.oose.spotitubebackend.dto.PlaylistsDTO;
import nl.oose.spotitubebackend.dto.UserDTO;
import nl.oose.spotitubebackend.persistence.PlaylistsDAO;
import nl.oose.spotitubebackend.persistence.UserDAOImpl;

public class PlaylistServiceImpl implements PlaylistService {

    UserDAOImpl userDAOImpl = new UserDAOImpl();
    PlaylistsDAO playlistsDAO = new PlaylistsDAO();

    @Override
    public PlaylistsDTO getPlaylistByUser(String token, String username) {
        //TODO Get playlist via token
        UserDTO validToken = userDAOImpl.getUserByToken(token);
        if(validToken != null){
            return playlistsDAO.getUserPlaylists(username);
        } else {
            throw new SpotitubePlaylistException("Can't create playlist, "+token+" is invalid");
        }
    }

    @Override
    public void removePlaylist(String token, int playlistid) {
        UserDTO validToken = userDAOImpl.getUserByToken(token);
        if(validToken != null){
            playlistsDAO.removePlaylistFromDatabase(playlistid);
        } else {
            throw new SpotitubePlaylistException("Can't create playlist, "+token+" is invalid");
        }
    }

    @Override
    public void addPlaylist(String token, String name) {
        UserDTO validToken = userDAOImpl.getUserByToken(token);
        if(validToken != null){
            playlistsDAO.addPlaylistToDatabase(name);
        } else {
            throw new SpotitubePlaylistException("Can't create playlist, "+token+" is invalid");
        }

    }

    @Override
    public void updatePlaylistName(String token, int playlist_id, String name) {
        UserDTO validToken = userDAOImpl.getUserByToken(token);
        if(validToken != null){
            playlistsDAO.updatePlaylist(playlist_id, name);
        } else {
            throw new SpotitubePlaylistException("Can't create playlist, "+token+" is invalid");
        }

    }
}
