import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

/*
 * To be done:
 *    - handle exceptions if the client is disconnected 
 *    - fix the problem when the client is disconnected and the socket is not closed
 */
public class CommServer {
	public static void main( String argv[] ){
		
		//not sure about synchronized and it should be static
		HashMap<String, CommKeeper> threads = new HashMap<String, CommKeeper>();
		HashMap<String, RSPi> raspberries= new HashMap<String, RSPi>();
		ServerSocket ss;
		try {
			ss = new ServerSocket(200);
			while(true){
				//all incoming connection will be served by a new thread
				new Thread(new CommHandler(ss.accept(), threads, raspberries)).start();
 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
}
