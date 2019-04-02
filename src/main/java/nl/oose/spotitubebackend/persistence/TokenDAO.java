package nl.oose.spotitubebackend.persistence;

public interface TokenDAO {
    void saveToken(String username, String token);

    boolean tokenExpired(String token);
}
