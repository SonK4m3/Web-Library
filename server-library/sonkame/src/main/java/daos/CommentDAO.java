package daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import models.Comment;

public class CommentDAO extends DAO{
	public CommentDAO() {
		super();
	}
	
	public List<Comment> getCommnetByBookId(int id_book) {
		connectToMySQL();
		List<Comment> ls = new ArrayList<Comment>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String query = "SELECT comment.*, user.fullname FROM library.comment, user WHERE id_book = ? AND id_user = user.id;";
			ps = con.prepareStatement(query);
			ps.setInt(1, id_book);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				Comment cm = new Comment(rs.getInt("id_user"), rs.getInt("id_book"), rs.getString("comment"));
				cm.setId(rs.getInt("id"));
				cm.setDate(rs.getDate("time"));
				cm.setTime(rs.getTime("time"));
				cm.setUsername(rs.getString("fullname"));
				ls.add(cm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ls;
	}
	
	public boolean addComment(Comment cm) {
		connectToMySQL();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean successfull = true;
		
		try {
			String query = "INSERT INTO comment(id_user, id_book, comment, time) VALUES (?, ?, ?, now());";
			ps = con.prepareStatement(query);
			ps.setInt(1, cm.getId_user());
			ps.setInt(2, cm.getId_book());
			ps.setString(3, cm.getComment());
			
			successfull = ps.execute();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return !successfull;
	}
	
}
