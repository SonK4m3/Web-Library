package daos;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import models.User;

public class UserDAO extends DAO{
	
	private Set<Integer> setCurrentUser = null;
	
	public UserDAO() {
		setCurrentUser = new HashSet<Integer>();
	}
	
	public User findUserByEmail(String email) throws IOException{
		User user = null;
		connectToMySQL();
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT user.fullname, role_detail.role_name FROM user, role_detail WHERE user.email = ? AND user.role = role_detail.id;";
			ps = con.prepareStatement(query);
			ps.setString(1, email);
			resultSet = ps.executeQuery();
			
			if(resultSet.next()) {
				String fullname = resultSet.getString("fullname");
				String roleName = resultSet.getString("role_name");
				
				user = new User();
				user.setFullname(fullname);
				user.setEmail(email);
				user.setRoleName(roleName);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public User loginUser(String email, String password) {
		connectToMySQL();
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		try {
			String query = "SELECT user.id, user.email, user.fullname, role_detail.role_name FROM user, role_detail WHERE user.email = ? AND user.password = ?  AND user.role = role_detail.id";
			ps = con.prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, password);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setFullname(rs.getString("fullname"));
				user.setRoleName(rs.getString("role_name"));
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public int registerUser(String email, String encodePassword, String fullname) throws IOException {
		connectToMySQL();
		PreparedStatement ps = null;
		Boolean registerStatus = true;
		int registerUserId = -1;
		
		try {
			String query = "INSERT INTO user(email, password, fullname, role) VALUES (?, ?, ?, 0);";
			ps = con.prepareStatement(query);
			ps.setString(1, email);
			ps.setString(2, encodePassword);
			ps.setString(3, fullname);
			
			registerStatus = ps.execute();
			if(!registerStatus) {
				query = "SELECT * FROM library.user ORDER BY id DESC LIMIT 1;";
				ps = con.prepareStatement(query);
				
				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					registerUserId = rs.getInt("id");
				}
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return registerUserId;
	}
	
	public boolean storageUserLogin(int id) {
		if(setCurrentUser.contains(id)) 
			return false;
		setCurrentUser.add(id);
		return true;
	}
	
	public boolean removeUserLogin(int id) {
		if(setCurrentUser.contains(id)) {
			setCurrentUser.remove(id);
			return true;
		}
		return false;
	}
	
	public boolean isLogin(int id) {
		return setCurrentUser.contains(id);
	}
}
