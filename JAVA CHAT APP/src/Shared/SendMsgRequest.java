package Shared;

import java.io.Serializable;

public class SendMsgRequest extends Request implements Serializable {
    
	private String msgcontent,topicname;
	private Message msg = new Message (msgcontent);
	private Topic t = new Topic(topicname);
	
	
	public SendMsgRequest(Message msg, Topic t) {
		super();
		this.msg = msg;
		this.t = t;
	}
	
	
	
}
