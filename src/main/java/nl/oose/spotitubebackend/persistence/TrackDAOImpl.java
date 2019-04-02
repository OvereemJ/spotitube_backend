package nl.oose.spotitubebackend.persistence;

import nl.oose.spotitubebackend.dto.PlaylistDTO;
import nl.oose.spotitubebackend.dto.TrackDTO;
import nl.oose.spotitubebackend.dto.TracksDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*

SELECT T.* FROM tracks T LEFT OUTER JOIN (
    SELECT *FROM tracks T1
    INNER JOIN playlist_track TIP1 ON
    T1.id = TIP1.track_id
    WHERE PLAYLIST_ID = 1
)
AS TIP ON T.id = TIP.track_id WHERE TIP.track_id IS NULL
 */
public class TrackDAOImpl implements TrackDAO {
    private List<TrackDTO> tracksArray = new ArrayList<>();
    @Override
    public TracksDTO getAllTracksNotInPlaylist(int playlist_id) {
        TracksDTO tracksDTO = null;
        try {
            Connection connection = new ConnectionFactory().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM tracks T LEFT OUTER JOIN (" +
                    "SELECT * FROM tracks T1 " +
                    "    INNER JOIN playlist_track PT ON " +
                    "    T1.id = PT.track_id" +
                    "    WHERE PT.playlist_id = ?" +
                    ") AS TrackInPlaylist ON T.id = TrackInPlaylist.track_id WHERE TrackInPlaylist.track_id IS NULL");

            preparedStatement.setInt(1, playlist_id);
            ResultSet result = preparedStatement.executeQuery();

        tracksDTO = new TracksDTO(setTracks(result));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tracksDTO;
    }

    @Override
    public TracksDTO getAllTracksInPlaylist(String playlist_id) {
        TracksDTO tracksDTO = null;
        try {
            Connection connection = new ConnectionFactory().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT T.* FROM tracks T " +
                    "    INNER JOIN playlist_track PT ON " +
                    "    T.id = PT.track_id" +
                    "    WHERE PT.playlist_id = ?");

            preparedStatement.setString(1, playlist_id);
            ResultSet result = preparedStatement.executeQuery();

            tracksDTO = new TracksDTO(setTracks(result));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tracksDTO;
    }

    @Override
    public void removeTrackFromPlaylist(int playlistid, int trackid) {
        try
                (
                        Connection connection = new ConnectionFactory().getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM playlist_track " +
                                "WHERE playlist_id = ? AND track_id = ?")

                ){
            preparedStatement.setInt(1, playlistid);
            preparedStatement.setInt(2, trackid);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addTrackToPlaylist(int playlist_id, int track_id, boolean offlineAvailable) {
        try {
            Connection connection = new ConnectionFactory().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO playlist_track" +
                    "(playlist_id, track_id) VALUES (?,?)");
            PreparedStatement setBooleanOfflinevailable = connection.prepareStatement("UPDATE tracks SET offlineAvailable = ? " +
                    "WHERE id = ?");

            preparedStatement.setInt(1, playlist_id);
            preparedStatement.setInt(2, track_id);
            preparedStatement.execute();

            setBooleanOfflinevailable.setBoolean(1, offlineAvailable);
            setBooleanOfflinevailable.setInt(2, track_id);
            setBooleanOfflinevailable.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<TrackDTO> setTracks(ResultSet result) throws SQLException {
        while (result.next()) {
            tracksArray.add(new TrackDTO(result.getInt("id"),
                    result.getString("title"),
                    result.getString("performer"),
                    result.getLong("duration"),
                    result.getString("album"),
                    result.getLong("playcount"),
                    result.getString("publicationDate"),
                    result.getString("description"),
                    result.getBoolean("offlineAvailable")));
        }
        return tracksArray;
    }



}


