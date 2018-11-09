package Shared;

import java.util.ArrayList;

public class TopicsList {

	private ArrayList<Topic> topics;

	public TopicsList(ArrayList<Topic> topics) {
		super();
		this.topics = topics;
	}
	
    public void addTopic(Topic t) {
    	this.topics.add(t);
    }
	
    
}
