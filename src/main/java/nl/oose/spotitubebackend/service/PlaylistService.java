package nl.oose.spotitubebackend.service;

import nl.oose.spotitubebackend.dto.PlaylistDTO;

public interface PlaylistService {
     PlaylistDTO getPlaylistByToken(String token);

     PlaylistDTO removePlaylist(String token, int playlistid);

     PlaylistDTO addPlaylist(String token, int playlistid, String name, String user);

     PlaylistDTO updatePlaylistName(String token, String name);
}
