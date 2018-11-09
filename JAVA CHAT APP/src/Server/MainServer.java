package Server;

public class MainServer {
	public static void main(String[] args) {

        try {
            Server s= new Server();
            s.WaitConnection();
            
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        
    }
}
