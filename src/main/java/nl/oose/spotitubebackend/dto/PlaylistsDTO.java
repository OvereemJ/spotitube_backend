package nl.oose.spotitubebackend.dto;



import java.util.ArrayList;
import java.util.List;

public class PlaylistsDTO {
    private List<PlaylistDTO> playlists = new ArrayList<>();
    private long length;

    public PlaylistsDTO() {

    }

    public PlaylistsDTO(List<PlaylistDTO> playlists, long length) {
        this.playlists = playlists;
        this.length = length;
    }

    public List<PlaylistDTO> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(List<PlaylistDTO> playlists) {
        this.playlists = playlists;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public void addPlaylist(PlaylistDTO playlist){
        playlists.add(playlist);
    }
}
