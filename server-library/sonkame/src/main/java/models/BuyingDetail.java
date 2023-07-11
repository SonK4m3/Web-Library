package models;

public class BuyingDetail {
	private int id;
	private int id_buying;
	private int id_book;
	private int quantity;
	private String title;
	private String coverPath;
	private int price;
	
	public BuyingDetail() {
		this.id = -1;
		this.id_buying = -1;
		this.id_book = -1;
		this.quantity =0;
		this.title = "";
		this.coverPath = "";
		this.price = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_buying() {
		return id_buying;
	}

	public void setId_buying(int id_buying) {
		this.id_buying = id_buying;
	}

	public int getId_book() {
		return id_book;
	}

	public void setId_book(int id_book) {
		this.id_book = id_book;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCoverPath() {
		return coverPath;
	}

	public void setCoverPath(String coverPath) {
		this.coverPath = coverPath;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "BuyingDetail [id=" + id + ", id_buying=" + id_buying + ", id_book=" + id_book + ", quantity=" + quantity
				+ ", title=" + title + ", coverPath=" + coverPath + ", price=" + price + "]";
	}
}
