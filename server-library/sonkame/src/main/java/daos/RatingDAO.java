package daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import models.Rating;


public class RatingDAO extends DAO{
	public RatingDAO() {
		super();
	}
	public List<Rating> getRatings(int id_book) {
		connectToMySQL();
		List<Rating> ls = new ArrayList<Rating>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String query = "SELECT * FROM library.rate WHERE id_book = ?;";
			ps = con.prepareStatement(query);
			ps.setInt(1, id_book);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				Rating r = new Rating(rs.getInt("id_user"), rs.getInt("id_book"), rs.getInt("number"));
				r.setId(rs.getInt("id"));
				ls.add(r);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ls;
	}
	
	public boolean addRating(Rating r) {
		connectToMySQL();
		PreparedStatement ps = null;
		boolean successful = true;
		try {
			String query = "INSERT INTO rate(number, id_user, id_book) VALUES (?, ?, ?);";

			String query_validate = "SELECT COUNT(*) as cnt FROM library.rate WHERE id_user = ? AND id_book = ?;";
			ps = con.prepareStatement(query_validate);
			ps.setInt(1, r.getId_user());
			ps.setInt(2, r.getId_book());
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				if(rs.getInt("cnt") > 0) {
					query = "UPDATE rate SET number = ? WHERE id_user = ? AND id_book = ?;";
				}
			}
			
			ps = con.prepareStatement(query);
			ps.setInt(1, r.getNumber());
			ps.setInt(2, r.getId_user());
			ps.setInt(3, r.getId_book());
			
			successful = ps.execute();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return !successful;
	}
}	
