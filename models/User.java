package com.parlorstore.models;
public class User 
{
    private  int userId;
    private String password;
    private String name;
    private String email;
    private String phone;

    // Constructor
    public User(int userId, String name, String email, String phone)
    {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password=password;
        
        
    }

	public int getUserid() {
		return userId;
	}

	public void setUserid(int userid) {
		userId = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}       
