package Server;

import Shared.AuthentificationRequest;
import Shared.AuthentificationResponse;
import Shared.CreateAccountRequest;
import Shared.JoinTopicRequest;
import Shared.NewTopicRequest;
import Shared.Request;
import Shared.SendMsgRequest;
import Shared.Topic;
import Shared.TopicsList;
import Shared.TopicsListResponse;
import Shared.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread { 

	private Socket client;
	private ArrayList<Topic> topics;
	
	public ClientHandler(Socket client,ArrayList topics) {
		this.client = client;
		this.topics=topics;
	}


	public void run() {

		try {
			handleClient();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}   

	}

	private void handleClient() throws IOException, Exception {
		
		ArrayList<User> Accounts = new ArrayList<User>();
		ArrayList<User> users = new ArrayList<User>(); //List of connected clients
		

		ObjectInputStream objin = new ObjectInputStream(this.client.getInputStream());//read clients' messages
		ObjectOutputStream objout = new ObjectOutputStream(this.client.getOutputStream());//send to clients
		OutputStreamWriter output = new OutputStreamWriter(this.client.getOutputStream());



		while (client.isConnected()) { 

			Request requete = (Request) objin.readObject();//reads the received object from the client, which is a request

////////////////////////////////////////////////////log in request //////////////////////////////////////////////////////////	

			if (requete instanceof AuthentificationRequest) { // If the client asks to log in
				AuthentificationRequest autentif = (AuthentificationRequest) requete;
				User u1 = new User(autentif.username,autentif.password);

				String username_password=u1.getUsername()+" "+ u1.getPassword();
				boolean userExist = false;

				File ClientFile = new File ("./ClientFile.txt");
				FileReader fr_ClientFile = new FileReader(ClientFile);
				BufferedReader br_ClientFile = new BufferedReader(fr_ClientFile);

				// on se place sur la premiere ligne du fichier
				String line = br_ClientFile.readLine();   

				// parcourir le fichier
				while (line != null) {
					// On lit la premiere ligne
					if (username_password.equals(line)) {
						userExist = true;
						break;
					}
					// On passe a la ligne suivante
					line = br_ClientFile.readLine();
				}
				
				// si clientTrouve
				if (userExist==true) {
					
					objout.writeObject(new AuthentificationResponse(true));//envoie au client
				} else {
					objout.writeObject(new AuthentificationResponse(false));//envoie au client//envoie au client
				}
		
			}
					
////////////////////////////////////////////////////New client request////////////////////////////////////////////////////////

					if (requete instanceof CreateAccountRequest) { // If the client asks to create an account
						
						CreateAccountRequest create = (CreateAccountRequest) requete;
						User u2 = new User(create.username,create.password);
						String username_password=u2.getUsername()+" "+ u2.getPassword();
						
						File ClientFile = new File ("./ClientFile.txt");
						FileWriter fw_ClientFile = new FileWriter(ClientFile);
						BufferedWriter bw = new BufferedWriter(fw_ClientFile);
                        bw.write(username_password);
                        bw.flush();
                        
						users.add(u2);
						System.out.println("The account has been successfully created");
					}

////////////////////////////////////////////////////New topic request////////////////////////////////////////////////////////
					
					if (requete instanceof NewTopicRequest) { // If the client asks to create an account
						NewTopicRequest newtop = (NewTopicRequest) requete;
						Topic t = new Topic(newtop.name);
						topics.add(t);	
						
					} 
					
////////////////////////////////////////////////////Joining topic request////////////////////////////////////////////////////////
					
					if (requete instanceof JoinTopicRequest) { // If the client asks to create an account
						TopicsListResponse resp = new TopicsListResponse (topics);
						objout.writeObject(resp);
				
					} 
					
////////////////////////////////////////////////////Sending msg request////////////////////////////////////////////////////////
					
					if (requete instanceof SendMsgRequest){
						SendMsgRequest msg = (SendMsgRequest) requete;
						
						
					}
				}

			}
		}
	

