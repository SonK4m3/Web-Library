package daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Utils.AppUtils;
import models.Book;

public class BookDAO extends DAO{
	public BookDAO() {
		super();
	}
	
	public List<Book> getBooks() {
		connectToMySQL();
		List<Book> books = new ArrayList<Book>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String query = "SELECT book.*, book_detail.sold FROM book LEFT JOIN book_detail ON book.id = book_detail.id_book;";
			ps = con.prepareStatement(query);
			
			rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String des = rs.getString("description");
				Date release = rs.getDate("release");
				int pages = rs.getInt("pages");
				String category = rs.getString("id_category");
				String image = rs.getString("cover_url");
				int sold = rs.getInt("sold");
				long price = rs.getLong("price");
				int availability = rs.getInt("availability");
				
				Book book = new Book(title, author, des, image, release, pages, category, price);
				book.setAvailability(availability == 1 ? true : false);
				book.setSold(sold);
				book.setId(id);
				
				books.add(book);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return books;
	}
	
	public Book getBookById(int id) {
		connectToMySQL();
		Book book = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String query = "SELECT book.*, book_detail.sold FROM book LEFT JOIN book_detail ON book.id = book_detail.id_book WHERE book.id = ?;";
			ps = con.prepareStatement(query);
			ps.setInt(1, id);;
			
			rs = ps.executeQuery();
			while(rs.next()) {
				String title = rs.getString("title");
				String author = rs.getString("author");
				String des = rs.getString("description");
				Date release = rs.getDate("release");
				int pages = rs.getInt("pages");
				String category = rs.getString("id_category");
				String image = rs.getString("cover_url");
				int sold = rs.getInt("sold");
				long price = rs.getLong("price");
				int availability = rs.getInt("availability");
				
				book = new Book(title, author, des, image, release, pages, category, price);
				book.setAvailability(availability == 1 ? true : false);
				book.setSold(sold);
				book.setId(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return book;
	}
	
	public int upload(Book book) {
		connectToMySQL();
		PreparedStatement ps = null;
		boolean successful = true;
		int addedBookId = book.getId();
		try {
			System.out.println(book.toString());
			String query_validate = "SELECT COUNT(*) as cnt FROM book WHERE title = ? AND author = ? AND id != ?;";
			ps = con.prepareStatement(query_validate);
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setInt(3, book.getId());
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				if(rs.getInt("cnt") > 0)
					return -2;
			}
			String query = "INSERT INTO book(title, author, price, pages, book.release, id_category, availability, cover_url, book.description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
			ps = con.prepareStatement(query);

			if(book.getId() > -1) {
				System.out.println("update");
				query = "UPDATE book SET title = ?, author = ?, price = ?, pages = ?, book.release = ?, id_category = ?, availability = ?, cover_url=?, book.description = ? WHERE id = ?;";
				ps = con.prepareStatement(query);
				ps.setInt(10, book.getId());
			}
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthor());
			ps.setInt(3, (int) book.getPrice());
			ps.setInt(4, book.getPages());
			ps.setDate(5, AppUtils.utilDate2sqlDate(book.getRelease()));
			ps.setString(6, book.getCategory());
			ps.setInt(7, book.isAvailability() ? 1 : 0);
			ps.setString(8, book.getCoverPath());
			ps.setString(9, book.getDescription());
			
			successful = ps.execute();
			if(!successful && book.getId() == -1) {
				query = "SELECT * FROM library.book ORDER BY id DESC LIMIT 1;";
				ps = con.prepareStatement(query);
				rs = ps.executeQuery();
				if(rs.next()) {
					addedBookId = rs.getInt("id");
				}
				if(addedBookId > -1) {
					query = "INSERT INTO book_detail(id_book, sale, is_sale, sold) VALUES (?, 0, 1, 0);";
					ps = con.prepareStatement(query);
					ps.setInt(1, addedBookId);
					
					successful = ps.execute();
					
					if(successful) return -1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return addedBookId;
	}
	
	public boolean delete(int id) {
		connectToMySQL();
		PreparedStatement ps = null;
		boolean successful = true;
		try {
			String query_validate = "DELETE FROM book WHERE id = ?;";
			ps = con.prepareStatement(query_validate);
			ps.setInt(1, id);
			
			successful = ps.execute();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return !successful;
	}
}
