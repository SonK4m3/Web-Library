package daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import models.Category;

public class CategoryDAO extends DAO{
	public CategoryDAO() {
		super();
	}
	
	public List<Category> getCategories() {
		connectToMySQL();
		List<Category> categories = new ArrayList<Category>();
		PreparedStatement ps = null;
		ResultSet resultSet = null;
		try {
			String query = "SELECT * FROM library.category;";
			ps = con.prepareStatement(query);
			resultSet = ps.executeQuery();
			
			while(resultSet.next()) {
				categories.add(new Category(resultSet.getString("category_code"), resultSet.getString("name")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return categories;
	}
}
