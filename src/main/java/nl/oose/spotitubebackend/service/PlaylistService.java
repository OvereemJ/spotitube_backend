package nl.oose.spotitubebackend.service;

import nl.oose.spotitubebackend.dto.PlaylistsDTO;

public interface PlaylistService {
     PlaylistsDTO getPlaylistByToken(String token);

     void removePlaylist(String token, int playlistid);

     void addPlaylist(String token, int playlistid, String name, String user);

     void updatePlaylistName(String token, int playlist_id, String name);
}
