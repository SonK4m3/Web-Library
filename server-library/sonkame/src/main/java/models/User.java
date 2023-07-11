package models;

import com.google.gson.annotations.SerializedName;

public class User {
	@SerializedName("id")
	private int id;
	@SerializedName("fullname")
	private String fullname;
	@SerializedName("email")
	private String email;
	@SerializedName("password")
	private String password;
	@SerializedName("role_name")
	private String roleName;
	public static final String UNKNOWN = "unknown";
	
	public User() {
		this.id = -1;
		this.email = "";
		this.password = "";
		this.fullname = "";
		this.roleName = UNKNOWN;
	}
	
	public User(String email, String password, String fullname) {
		this.id = -1;
		this.email = email;
		this.password = password;
		this.fullname = fullname;
		this.roleName = UNKNOWN;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", fullname=" + fullname + ", email=" + email + ", password=" + password
				+ ", roleName=" + roleName + "]";
	}	
}
