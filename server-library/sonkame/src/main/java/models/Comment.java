package models;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;

public class Comment {
	private int id;
	private int id_user;
	private int id_book;
	private String comment;
	private Date date;
	private Time time;
	private String username;
	
	public Comment() {
		this.id = - 1;
		this.id_user = -1;
		this.id_book = -1;
		this.comment = "";
		this.date = new Date();
	}
	
	public Comment(int id_user, int id_book, String comment) {
		this.id = -1;
		this.id_user = id_user;
		this.id_book = id_book;
		this.comment = comment;
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

	public int getId_book() {
		return id_book;
	}

	public void setId_book(int id_book) {
		this.id_book = id_book;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time2) {
		this.time = time2;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", id_user=" + id_user + ", id_book=" + id_book + ", comment=" + comment
				+ ", date=" + date + ", time=" + time + "]";
	}
}
