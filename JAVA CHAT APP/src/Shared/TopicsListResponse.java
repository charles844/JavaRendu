package Shared;

import java.io.Serializable;
import java.util.ArrayList;

public class TopicsListResponse extends Response implements Serializable{
	
	private ArrayList<Topic> list;

	public TopicsListResponse(ArrayList<Topic> list) {
		super();
		this.list = list;
	}

	public ArrayList<Topic> getList() {
		return list;
	}

	public void setList(ArrayList<Topic> list) {
		this.list = list;
	}
	
	

}
