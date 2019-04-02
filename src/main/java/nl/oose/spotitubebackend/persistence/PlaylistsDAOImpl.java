package nl.oose.spotitubebackend.persistence;

import nl.oose.spotitubebackend.dto.PlaylistDTO;
import nl.oose.spotitubebackend.dto.PlaylistsDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistsDAOImpl implements PlaylistsDAOInterface {
    private List<PlaylistDTO> playlistArray = new ArrayList<>();
    @Override
    public PlaylistsDTO getUserPlaylists(String token){
        PlaylistsDTO playlistsDTO = null;
        try
                (
                        Connection connection = new ConnectionFactory().getConnection();
                        PreparedStatement preparedStatement = connection.prepareStatement("select playlist.playlist_id, playlist.name, playlist.owner from playlist " +
                                "INNER JOIN playlist_user pu on playlist.playlist_id = pu.playlist_id " +
                                "INNER JOIN token T ON pu.user = T.user " +
                                "WHERE T.auth_token =  ?")
                ){
            preparedStatement.setString(1, token);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                playlistArray.add(new PlaylistDTO(resultSet.getInt("playlist_id"), resultSet.getString("name"),  resultSet.getBoolean("owner")));
            }
            playlistsDTO = new PlaylistsDTO(playlistArray, getPlaylistsLength(token));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playlistsDTO;
    }

    @Override
    public void removePlaylistFromDatabase(int playlistid) {
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

    @Override
    public void addPlaylistToDatabase(String name, String user) {
        int last_id = getLastInsertedId();
        try
                (
                        PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO playlist (playlist_id, name, owner) VALUES (?,?,?)");
                ){
            preparedStatement.setInt(1, last_id);
            preparedStatement.setString(2, name);
            preparedStatement.setBoolean(3, true);
            preparedStatement.execute();

            insertUser(user, last_id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePlaylist(int id, String name) {

        try
                (
                        PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE playlist SET name = ? WHERE playlist_id = ?")
                ){
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getLastInsertedId() {
        int last_inserted = 1;
        try{

            PreparedStatement lasinsertedId = getConnection().prepareStatement("SELECT MAX(playlist_id) as playlist_id FROM playlist ");
            ResultSet result = lasinsertedId.executeQuery();
            while(result.next()){
                last_inserted += result.getInt("playlist_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return  last_inserted;
    }



    @Override
    public void insertUser(String name, int id){
        try {
            PreparedStatement insertUser = getConnection().prepareStatement("INSERT INTO playlist_user (user, playlist_id) VALUES (?,?)");
            insertUser.setString(1, name);
            insertUser.setInt(2, id);
            insertUser.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection(){
        Connection connection = new ConnectionFactory().getConnection();
        return connection;
    }

    @Override
    public int getPlaylistsLength(String token){
        int totalDuration = 0;
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT SUM(T.duration) AS duration FROM playlist_user PU INNER JOIN playlist_track PT ON \n" +
                    "PT.playlist_id = PU.playlist_id INNER JOIN tracks T \n" +
                    "ON T.id = PT.track_id " +
                    "INNER JOIN Token Tkn on Pu.user = Tkn.user WHERE Tkn.auth_token = ?");
            preparedStatement.setString(1, token);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                totalDuration += result.getInt("duration");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return totalDuration;
    }
}
