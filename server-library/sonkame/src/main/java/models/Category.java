package models;

public class Category {
	private String category_code;
	private String name;
	
	public Category(String category_code, String name) {
		this.category_code = category_code;
		this.name = name;
	}

	public String getCategory_code() {
		return category_code;
	}

	public void setCategory_code(String category_code) {
		this.category_code = category_code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Category [category_code=" + category_code + ", name=" + name + "]";
	}
}
