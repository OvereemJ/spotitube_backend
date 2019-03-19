package nl.oose.spotitubebackend.persistence;

import nl.oose.spotitubebackend.dto.PlaylistDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistsDAO {

    public List<PlaylistDTO> getUserPlaylists(String user){
        List<PlaylistDTO> playlist = new ArrayList<>();

        try
                (
                        Connection connection = new ConnectionFactory().getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("SELECT PL.playlist_id, PL.user, PL.name FROM playlist PL where PL.user = ?")
                ){
            preparedStatement.setString(1, user);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                playlist.add(new PlaylistDTO(resultSet.getInt("playlist_id"), resultSet.getString("name"), resultSet.getBoolean("owner")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playlist;
    }

    public PlaylistDTO getPlaylistTotalLength(String token){
        return null;
    }
}
