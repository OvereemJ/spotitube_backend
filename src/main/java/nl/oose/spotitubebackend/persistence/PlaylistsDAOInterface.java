package nl.oose.spotitubebackend.persistence;

import nl.oose.spotitubebackend.dto.PlaylistsDTO;

import java.sql.Connection;

public interface PlaylistsDAOInterface {
    PlaylistsDTO getUserPlaylists(String token);

    void removePlaylistFromDatabase(int playlistid);

    void addPlaylistToDatabase(String name, String user);

    void updatePlaylist(int id, String name);

    int getLastInsertedId();

    void insertUser(String name, int id);

    Connection getConnection();

    int getPlaylistsLength(String token);
}
