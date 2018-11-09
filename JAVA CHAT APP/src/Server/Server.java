package Server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Shared.Topic;
import Shared.TopicsList;

public class Server {
    private ServerSocket s;
    private ArrayList<Topic> TopicsList= new ArrayList<Topic>();

    public Server() throws IOException {
        this.s = new ServerSocket(9888); //Creation de la socket
    }

    public void WaitConnection() throws IOException {
    	
    	//permet que le client puisse se connecter
        //le serveur ne fait rien tant qu'un client n'est pas connecte

        while (true) { //tant que lutilisateur n'a pas demande de fermer lapplication
        	
            System.out.println("Ready to accept client connection...");
            Socket Client = s.accept();
            System.out.println("Accepted connection from "+ Client);
            ClientHandler handler = new ClientHandler(Client,TopicsList);// gere le client responsable de la connection
            handler.start();
           
            // des qu'un client se connecte on ouvre un nouveau client handler
        }
    }



    
}
