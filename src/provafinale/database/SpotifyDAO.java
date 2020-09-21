package provafinale.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import provafinale.model.Song;

public class SpotifyDAO {
	
	public List<String> getAllGenres(){
		
		final String sql = "SELECT DISTINCT top_genre FROM top10s";
		
		List<String> generi = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				generi.add(rs.getString("top_genre"));
			}
			conn.close();
			return generi;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	public List<Song> getAllArtistSong(String artist){
		
		final String sql = "SELECT * FROM top10s WHERE artist = ?";
		
		List<Song> songs = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, artist);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Song s = new Song (rs.getInt("id"), rs.getString("title"), rs.getString("artist"), rs.getString("top_genre"), rs.getInt("year"),  rs.getInt("bpm"), rs.getInt("nrgy"), rs.getInt("dnce"), rs.getInt("dB"),
						rs.getInt("live"), rs.getInt("val"), rs.getInt("dur"), rs.getInt("acous"), rs.getInt("spch"), rs.getInt("pop"));
				songs.add(s);
			}
			conn.close();
			return songs;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
}
