package Shared;

import java.io.Serializable;
import java.util.ArrayList;

public class Topic implements Serializable {

	private String name;
	private ArrayList<Message>messages; 

	public Topic(String name) {
		this.name = name;
		this.messages=new ArrayList();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public ArrayList<Message> getMessagesList() {
		return messages;
	}

	public String getMessages(ArrayList messages) {
		int i;
		String content = null;
		for(i=0;i<messages.size();i++) {
			content += messages.get(i) + "\n";
	}
		return content;
	}

	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}

	public void addMsg(Message msg) {
		this.messages.add(msg);
	}
	
	



}