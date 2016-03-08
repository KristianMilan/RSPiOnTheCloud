import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

public class CommServer {
	public static void main( String argv[] ){
		
		//not sure about synchronized and it should be static
		HashMap<String, CommKeeper> threads = new HashMap<String, CommKeeper>();
		ServerSocket ss;
		try {
			ss = new ServerSocket(200);
			while(true){
				//all incoming connection will be served by a new thread
				new Thread(new CommHandler(ss.accept(), threads)).start();
 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
}
