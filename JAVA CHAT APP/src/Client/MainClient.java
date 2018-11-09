package Client;

public class MainClient{
	public static void main(String[] args) {

		try {
			
			Client c = new Client();
			while(true) {
			c.startClient();
			c.TopicHandle();
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

	}

}
