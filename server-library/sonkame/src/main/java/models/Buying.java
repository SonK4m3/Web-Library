package models;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Buying {
	private int id;
	private int id_user;
	private Date date;
	private Time time;
	private Date receiving;
	private int state;
	private List<BuyingDetail> buyings;
	
	public Buying() {
		this.id = -1;
		this.id_user = -1;
		buyings = new ArrayList<BuyingDetail>();
	}
	
	public void addNewBuyingDetail(BuyingDetail bt) {
		this.buyings.add(bt);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public Date getReceiving() {
		return receiving;
	}

	public void setReceiving(Date receiving) {
		this.receiving = receiving;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public List<BuyingDetail> getBuyings() {
		return buyings;
	}

	public void setBuyings(List<BuyingDetail> buyings) {
		this.buyings = buyings;
	}

	@Override
	public String toString() {
		return "Buying [id=" + id + ", id_user=" + id_user + ", date=" + date + ", time=" + time + ", receiving="
				+ receiving + ", state=" + state + ", buyings=" + buyings + "]";
	}
}
