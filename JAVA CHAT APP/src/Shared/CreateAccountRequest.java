package Shared;

import java.io.Serializable;

public class CreateAccountRequest extends Request implements Serializable{

	public String username;
	public String password;
	
	public CreateAccountRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}


	public String getPassword() {
		return password;
	}
	
	
}
