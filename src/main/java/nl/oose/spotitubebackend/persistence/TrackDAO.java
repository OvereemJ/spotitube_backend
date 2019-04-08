package nl.oose.spotitubebackend.persistence;

import nl.oose.spotitubebackend.dto.TrackDTO;
import nl.oose.spotitubebackend.dto.TracksDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface TrackDAO {
    TracksDTO getAllTracksNotInPlaylist(int playlist_id);

    TracksDTO getAllTracksInPlaylist(int playlist_id);

    void removeTrackFromPlaylist(int playlistid, int trackid);

    void addTrackToPlaylist(int playlist_id, int track_id, boolean offlineAvailable);

    List<TrackDTO> setTracks(ResultSet result) throws SQLException;
}
