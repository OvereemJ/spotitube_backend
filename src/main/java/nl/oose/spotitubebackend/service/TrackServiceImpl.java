package nl.oose.spotitubebackend.service;

import nl.oose.spotitubebackend.dto.PlaylistDTO;
import nl.oose.spotitubebackend.dto.TrackDTO;
import nl.oose.spotitubebackend.dto.TracksDTO;
import nl.oose.spotitubebackend.persistence.TrackDAO;

import java.util.List;

public class TrackServiceImpl implements TrackService{
    private TrackDAO trackdao = new TrackDAO();

    @Override
    public TracksDTO getAllAvailableTracks(String token, String playlist_id) {
        System.out.println(Integer.parseInt(playlist_id));
        return trackdao.getAllTracksNotInPlaylist(Integer.parseInt(playlist_id));
    }

    @Override
    public TracksDTO getAllTracksFromPlaylist(String token, String playlist_id) {
        return trackdao.getAllTracksInPlaylist(playlist_id);
    }

    @Override
    public void addTrack(String token, String playlist_id, int track_id) {
        trackdao.addTrackToPlaylist(Integer.parseInt(playlist_id),track_id);
    }

    @Override
    public void removeTrack(String token, String playlist_id, String trackid) {
        trackdao.removeTrackFromPlaylist(Integer.parseInt(playlist_id), Integer.parseInt(trackid));
    }
}
