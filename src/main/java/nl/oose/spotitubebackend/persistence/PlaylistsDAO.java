package nl.oose.spotitubebackend.persistence;

import nl.oose.spotitubebackend.dto.PlaylistDTO;
import nl.oose.spotitubebackend.dto.PlaylistsDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistsDAO {
    private List<PlaylistDTO> playlistArray = new ArrayList<>();
    public PlaylistsDTO getUserPlaylists(String username){
        PlaylistsDTO playlistsDTO = null;
        try
                (
                        Connection connection = new ConnectionFactory().getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("select playlist.playlist_id, playlist.name, playlist.owner from playlist " +
                                "INNER JOIN playlist_user pu on playlist.playlist_id = pu.playlist_id " +
                                "WHERE pu.user =  ?")
                ){
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                playlistArray.add(new PlaylistDTO(resultSet.getInt("playlist_id"), resultSet.getString("name"),  resultSet.getBoolean("owner")));
            }
            playlistsDTO = new PlaylistsDTO(playlistArray, 12334);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playlistsDTO;
    }

    public void removePlaylistFromDatabase(int playlistid) {
        try
                (
                        Connection connection = new ConnectionFactory().getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM playlist WHERE playlist_id = ?")

                ){
            preparedStatement.setInt(1, playlistid);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPlaylistToDatabase(String name) {
        try
                (
                        Connection connection = new ConnectionFactory().getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO playlist (playlist_id, name, owner) VALUES (?,?,?)")

                ){
            preparedStatement.setInt(1, -1);
            preparedStatement.setString(2, name);
            preparedStatement.setBoolean(3, false);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePlaylist(int id, String name) {

        try
                (
                        Connection connection = new ConnectionFactory().getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE playlist SET name = ? WHERE playlist_id = ?")
                ){
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
