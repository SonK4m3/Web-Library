package models;

import java.util.Date;

public class Book {
	private int id;
	private String title;
	private String author;
	private String coverPath;
	private String description;
	private Date release;
	private long price;
	private int pages;
	private String category;
	private int sold;
	private boolean availability;
	private boolean isSale;
	private int sale;
	
	public Book() {
		this.id = -1;
		this.title = "";
		this.author = "";
		this.coverPath = "";
		this.description = "";
		this.release = new Date();
		this.pages = 0;
		this.price = 0;
		this.sold = 0;
		this.availability = true;
		this.isSale = false;
		this.sale = 0;
	}
	
	public Book(String title, String author, String description, String coverPath, Date release, int pages, String category, long price) {
		this.id = -1;
		this.title = title;
		this.author = author;
		this.description = description;
		this.coverPath = coverPath;
		this.release = release;
		this.pages = pages;
		this.price = price;
		this.category = category;
		this.availability = true;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCoverPath() {
		return coverPath;
	}

	public void setCoverPath(String coverPath) {
		this.coverPath = coverPath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getRelease() {
		return release;
	}

	public void setRelease(Date release) {
		this.release = release;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getSold() {
		return sold;
	}

	public void setSold(int sold) {
		this.sold = sold;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public boolean isSale() {
		return isSale;
	}

	public void setSale(boolean isSale) {
		this.isSale = isSale;
	}

	public int getSale() {
		return sale;
	}

	public void setSale(int sale) {
		this.sale = sale;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", coverPath=" + coverPath
				+ ", description=" + description + ", release=" + release + ", price=" + price + ", pages=" + pages
				+ ", category=" + category + ", sold=" + sold + ", availability=" + availability + ", isSale=" + isSale
				+ ", sale=" + sale + "]";
	}
}
