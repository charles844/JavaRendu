package Shared;

import java.io.Serializable;
import java.util.Scanner;

public class AuthentificationRequest extends Request implements Serializable {

	
    public String username;
    public String password;
  
   
    public AuthentificationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
    
    
    
}

