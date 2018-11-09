package Client;

import Shared.AuthentificationRequest;

import Shared.AuthentificationResponse;
import Shared.CreateAccountRequest;
import Shared.JoinTopicRequest;
import Shared.Message;
import Shared.Request;
import Shared.Response;
import Shared.SendMsgRequest;
import Shared.Topic;
import Shared.NewTopicRequest;
import Shared.TopicsListResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import Server.Server;

public class Client {

	private Socket clientsocket; //Client socket
	public ObjectInputStream ois; //Client output stream
	public ObjectOutputStream oos; //Client input stream
	public BufferedReader read;
	private ArrayList<Topic> topics = new ArrayList<Topic>();
	private ArrayList<Message> Messages = new ArrayList<Message>();


	public Client(){

		try {
			this.clientsocket = new Socket(InetAddress.getLocalHost(),9888);
			this.oos = new ObjectOutputStream(this.clientsocket.getOutputStream());//send objects to the server
			this.ois = new ObjectInputStream(this.clientsocket.getInputStream());//reads responses from the server
		}
		catch (Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}

	
////////////////////////////////////////////////////Authenfication & account creation////////////////////////////////////////////////////////
	public void startClient() throws IOException, ClassNotFoundException {

		this.clientsocket = new Socket(InetAddress.getLocalHost(),9888);
		this.oos = new ObjectOutputStream(this.clientsocket.getOutputStream());//send objects to the server
		this.ois = new ObjectInputStream(this.clientsocket.getInputStream());//reads responses from the server

		int choice;

		do {
			String str = JOptionPane.showInputDialog("choose an option: type 1 to log in or 2 to create an account");
			choice = Integer.parseInt(str);

		}while((choice != 1) && (choice != 2)); //

		int log;

		if (choice == 1) { //the client wants to log in
			do {
				String username = JOptionPane.showInputDialog("enter your username please:");
				String password = JOptionPane.showInputDialog("enter your password please:");

				AuthentificationRequest a = new AuthentificationRequest(username, password); 
				oos.writeObject(a);//send the authentification request to the server
				

				Response resp = (Response) ois.readObject(); //the client reads the server's response to his request
				if (((AuthentificationResponse)resp).login == true) {
					JOptionPane.showMessageDialog(null,"Congratulations, you're logged in");
					log = 1;}
				else {
					JOptionPane.showMessageDialog(null,"Login error, please try again");
					log=0;
				} 
			}while (log != 1);
			
		}


		if (choice == 2) { //the client wants to create an account
			String user = JOptionPane.showInputDialog("choose a username please:");
			String pass = JOptionPane.showInputDialog("choose a password please:");

			CreateAccountRequest c = new CreateAccountRequest(user, pass); 
			oos.writeObject(c);//send the creation account request to the server

			read = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));

		}

	}

////////////////////////////////////////////////////Creating/joining topics////////////////////////////////////////////////////////
	public void TopicHandle() throws IOException, ClassNotFoundException {

		int choice;
		int a = 0;

		do {
			String str2 = JOptionPane.showInputDialog("You're now ready to chat: \n" +
					"1 ==> Join an existing topic \n"+
					"2 ==> Create a new topic");
			choice = Integer.parseInt(str2);
			}
		while ((choice != 1 ) && (choice != 2));

		if (choice == 1) {
			JoinTopicRequest join = new JoinTopicRequest();
			oos.writeObject(join);
			
			Response resp = (Response) ois.readObject();
			topics = ((TopicsListResponse)resp).getList();
			
////////////////////////////////////////////////////if list is void////////////////////////////////////////////////////////
			
			if(topics.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Sorry, there are no topics yet, please create a new one");
				a = 1 ; //The ArrayList is empty
			}
			else {
				String output = "";
				for(int i = 0; i<topics.size(); i++){
					String save = topics.get(i).getName();
					output += i+1 + "- " + save + "\n";       
				}
				String str3 = JOptionPane.showInputDialog("Please type a topic name \n" + output );
				SendMsg(str3);
			}
		}


		if ((choice == 2) || (a == 1))  {
			String str4 = JOptionPane.showInputDialog("Please enter the topic name:");
			NewTopicRequest t = new NewTopicRequest(str4);
			oos.writeObject(t);//send the created topic to the server
			SendMsg(str4);
		}
	}
			
////////////////////////////////////////////////////Sending messages into topics////////////////////////////////////////////////////////
		public void SendMsg(String topicname) throws IOException, ClassNotFoundException {
	        Topic t = new Topic(topicname);
			int choice;
			do {
			String str5 = JOptionPane.showInputDialog("The " + topicname + " has been successfully created. What's next?\n" + 
                                                      "1 ==> Start chatting in <<" +topicname +">> topic?\n" +
                                                      "2 ==> disconnet?");
			choice = Integer.parseInt(str5);
			}
			while (choice != 1 && choice !=2);
			
			if (choice == 1) {
				
				String str6; 
				str6 = JOptionPane.showInputDialog("Please write down your msg or type <<done>> to exit:");
				Message m = new Message(str6); 
	    		SendMsgRequest s = new SendMsgRequest(m,t);// requete pour envoyer le message au autres clients 
			    t.addMsg(m);
			    oos.writeObject(s);
			    JOptionPane.showMessageDialog(null, "your msg has been sent"+t.getMessages(t.getMessagesList()));
			}
				
			    else
					JOptionPane.showMessageDialog(null,"Thank you. See you next time");
				    //quit the application
				
			
				
				}
		
				
			
			
		}	
		
	








