package Shared;

import java.io.Serializable;
import java.util.ArrayList;

public class NewTopicRequest extends Request implements Serializable{

	public String name;
	
	public NewTopicRequest(String name) {
		this.name = name;
	
		
	}
	
	

}
