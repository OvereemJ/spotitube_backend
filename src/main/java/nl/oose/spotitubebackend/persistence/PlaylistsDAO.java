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
    public PlaylistsDTO getUserPlaylists(String token){
        PlaylistsDTO playlistsDTO = null;
        try
                (
                        Connection connection = new ConnectionFactory().getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("select playlist.playlist_id, playlist.name, playlist.user from playlist INNER JOIN account on playlist.user = account.user INNER JOIN token on account.user = token.user WHERE token.auth_token =  ?")
                ){
            preparedStatement.setString(1, token);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                playlistArray.add(new PlaylistDTO(resultSet.getInt("playlist_id"), resultSet.getString("name"),  resultSet.getString("user")));
            }
            playlistsDTO = new PlaylistsDTO(playlistArray, 12334);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playlistsDTO;
    }

    public void removePlaylist(int playlistid) {
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

    public void addPlaylist(int playlistid, String name, String user) {
        try
                (
                        Connection connection = new ConnectionFactory().getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO playlist (playlist_id, name, user) VALUES (?,?,?)")

                ){
            preparedStatement.setInt(1, playlistid);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, user);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePlaylistName(int id, String name) {
        try
                (
                        Connection connection = new ConnectionFactory().getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE playlist SET name = ? WHERE playlist_id = ?)")
                ){
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
