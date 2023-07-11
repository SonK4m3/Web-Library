package daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Utils.AppUtils;
import models.Buying;
import models.BuyingDetail;


public class BuyingDAO extends DAO{
	public BuyingDAO() {
		super();
	}
	
	public List<Buying> getBuyingByUser(int id) {
		connectToMySQL();
		List<Buying> buyings = new ArrayList<>();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String query = "SELECT y.*, book.title, book.price, book.cover_url FROM (SELECT buying_detail.id, buying_detail.id_buying, buying_detail.id_book, buying_detail.quantity, x.id_user, x.time, x.receiving, x.state FROM library.buying_detail JOIN (SELECT * FROM buying WHERE id_user = ?) AS x ON buying_detail.id_buying = x.id) AS y JOIN book ON y.id_book = book.id;\r\n"
					+ "";
			ps = con.prepareStatement(query);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			int tmp = -1;
			Buying buying = null;
			
			while(rs.next()) {
				if(tmp == -1) {
					buying = new Buying();
					buying.setId(rs.getInt("id_buying"));
					buying.setId_user(rs.getInt("id_user"));
					buying.setDate(rs.getDate("time"));
					buying.setTime(rs.getTime("time"));
					buying.setState(rs.getInt("state"));
					buying.setReceiving(rs.getDate("receiving"));
					
					tmp = buying.getId();
					buyings.add(buying);
				} else if(tmp != rs.getInt("id_buying")) {
					buying = new Buying();
					buying.setId(rs.getInt("id_buying"));
					buying.setId_user(rs.getInt("id_user"));
					buying.setDate(rs.getDate("time"));
					buying.setTime(rs.getTime("time"));
					buying.setState(rs.getInt("state"));
					buying.setReceiving(rs.getDate("receiving"));
					
					tmp = buying.getId();
					buyings.add(buying);
				}
				
				BuyingDetail bd = new BuyingDetail();
				bd.setId(rs.getInt("id"));
				bd.setId_book(rs.getInt("id_book"));
				bd.setQuantity(rs.getInt("quantity"));
				bd.setPrice(rs.getInt("price"));
				bd.setTitle(rs.getString("title"));
				bd.setCoverPath(rs.getString("cover_url"));
				buying.addNewBuyingDetail(bd);				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buyings;
	}
	
	public boolean buying(Buying buying) {
		connectToMySQL();
		PreparedStatement ps = null;
		boolean successful = true;
		ResultSet rs = null;
		try {
			String queryInsert = "INSERT INTO buying(id_user, time, receiving, state) VALUES (?, now(), ?,  0);";
			ps = con.prepareStatement(queryInsert);
			ps.setInt(1, buying.getId_user());
			ps.setDate(2,AppUtils.utilDate2sqlDate(AppUtils.getExceptionReceivingDate()));
			
			successful = ps.execute();
			if(!successful) {
				queryInsert = "SELECT * FROM library.buying ORDER BY id DESC LIMIT 1;";
				ps = con.prepareStatement(queryInsert);
				rs = ps.executeQuery();
				if(rs.next()) {
					buying.setId(rs.getInt("id"));
					if(buying.getId() == -1) return true;
				}
				for(BuyingDetail bd : buying.getBuyings()) {
					String queryUpdate = "UPDATE book_detail SET sold = sold + ? WHERE id_book = ?;";
					String queryInsertBd = "INSERT INTO buying_detail(id_buying, id_book, quantity) VALUES (?, ?, ?);";
					
					ps = con.prepareStatement(queryUpdate);
					ps.setInt(1, bd.getQuantity());
					ps.setInt(2, bd.getId_book());
					successful = ps.execute();
					
					ps = con.prepareStatement(queryInsertBd);
					ps.setInt(1, buying.getId());
					ps.setInt(2, bd.getId_book());
					ps.setInt(3, bd.getQuantity());
					successful = ps.execute();
				}
				con.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return !successful;
	}
	
	public boolean cancel(Buying buying) {
		connectToMySQL();
		PreparedStatement ps = null;
		boolean successful = true;
		try {
			String query = "UPDATE buying SET state = -1 WHERE id = ?;";
			ps = con.prepareStatement(query);
			ps.setInt(1, buying.getId());
			
			successful = ps.execute();
			if(!successful) {
				for(BuyingDetail bd : buying.getBuyings()) {
					String queryUpdate = "UPDATE book_detail SET sold = sold - ? WHERE id_book = ?;";
					
					ps = con.prepareStatement(queryUpdate);
					ps.setInt(1, bd.getQuantity());
					ps.setInt(2, bd.getId_book());
					successful = ps.execute();
				}
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return !successful;
	} 
}
