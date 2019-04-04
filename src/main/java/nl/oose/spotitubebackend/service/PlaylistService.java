package nl.oose.spotitubebackend.service;

import nl.oose.spotitubebackend.dto.PlaylistsDTO;

public interface PlaylistService {
     PlaylistsDTO getPlaylistByToken(String token);

     void removePlaylist(String token, int playlistid);

     void addPlaylist(String token, String name);

     void updatePlaylistName(String token, int playlist_id, String name);
}
