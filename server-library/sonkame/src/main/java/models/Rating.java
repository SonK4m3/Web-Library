package models;

public class Rating {
	private int id;
	private int id_user;
	private int id_book;
	private int number;
	
	public Rating() {
		this.id = -1;
		this.id_user = -1;
		this.id_book = -1;
		this.number = 0;
	}
	
	public Rating(int id_user, int id_book, int number) {
		this.id = -1;
		this.id_user = id_user;
		this.id_book = id_book;
		this.number = number;
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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "Rating [id=" + id + ", id_user=" + id_user + ", id_book=" + id_book + ", number=" + number + "]";
	}
}
