package nl.oose.spotitubebackend.dto;

import java.util.ArrayList;
import java.util.List;

public class PlaylistDTO {
    private int id;
    private String name;
    private boolean owner;
    private TracksDTO tracks;


    public PlaylistDTO() {
    }

    public PlaylistDTO(int id, String name, boolean owner, TracksDTO tracks) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.tracks = tracks;
    }

    public PlaylistDTO(int id, String name, boolean owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public TracksDTO getTracks() {
        return tracks;
    }

    public void setTracks(TracksDTO tracks) {
        this.tracks = tracks;
    }
}

