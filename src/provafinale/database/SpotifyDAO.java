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
	
	
	public List<String> getAllArtists(){
		
		final String sql = "SELECT DISTINCT artist FROM top10s ORDER BY artist";
		
		List<String> artisti = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				artisti.add(rs.getString("artist"));
			}
			conn.close();
			return artisti;
			
		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}
	
	
	public List<Song> getAllArtistSongs(String artist){
		
		final String sql = "SELECT DISTINCT * FROM top10s WHERE artist = ? GROUP BY title";
		
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
	
	
	public List<Song> getAllGenreSongs(String genre){
		
		final String sql = "SELECT DISTINCT * FROM top10s WHERE top_genre = ? ORDER BY artist";
		
		List<Song> songs = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, genre);

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
	
	public List<Integer> getAllYears(){
		
		final String sql = "SELECT DISTINCT year FROM top10s";
		
		List<Integer> anni = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				anni.add(rs.getInt("year"));
			}
			conn.close();
			return anni;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	public List<Song> getAllYearArtistSongs(String artist, int year) {
final String sql = "SELECT DISTINCT * FROM top10s WHERE artist = ? AND year = ? GROUP BY title";
		
		List<Song> songs = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, artist);
			st.setInt(2, year);

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

	public List<Song> getAllYearGenreSongs(String genre, int year) {
		
		final String sql = "SELECT DISTINCT * FROM top10s WHERE top_genre = ? AND year = ? ORDER BY artist";
		
		List<Song> songs = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, genre);
			st.setInt(2, year);

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

	public List<Song> getAllYearSongs(int year) {
		final String sql = "SELECT DISTINCT * FROM top10s WHERE year = ? ORDER BY artist";
		
		List<Song> songs = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, year);

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

	public List<Song> getCanzoniAffini(int durata, double popularity, double energy, double danceability) {
		List<Song> canzoniAffini = new ArrayList<>();
		final String sql = "SELECT * FROM top10s WHERE pop BETWEEN ? AND ? "
				+ "AND nrgy BETWEEN ? AND ? "
				+ "AND dnce BETWEEN ? AND ?";
		
		double popInf = popularity - 5;
		double popSup = popularity + 5;
		double nrgyInf = energy - 5;
		double nrgySup = energy + 5;
		double dnceInf = danceability - 5;
		double dnceSup = danceability + 5;
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setDouble(1, popInf);
			st.setDouble(2, popSup);
			st.setDouble(3, nrgyInf);
			st.setDouble(4, nrgySup);
			st.setDouble(5, dnceInf);
			st.setDouble(6, dnceSup);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Song s = new Song (rs.getInt("id"), rs.getString("title"), rs.getString("artist"), rs.getString("top_genre"), rs.getInt("year"),  rs.getInt("bpm"), rs.getInt("nrgy"), rs.getInt("dnce"), rs.getInt("dB"),
						rs.getInt("live"), rs.getInt("val"), rs.getInt("dur"), rs.getInt("acous"), rs.getInt("spch"), rs.getInt("pop"));
				canzoniAffini.add(s);
			}
			conn.close();
			return canzoniAffini;

		} catch (SQLException e) {
			 e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
	}

	
}
