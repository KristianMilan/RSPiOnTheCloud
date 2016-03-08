import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class RSPi_client {
	
	static Socket socket;
	static BufferedReader br;
	static PrintWriter pw;
	
	private static void newConnectionInit() throws UnknownHostException, IOException{
			socket = new Socket("127.0.0.1",200);
			br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw=new PrintWriter( 
				new BufferedWriter( new OutputStreamWriter(
						socket.getOutputStream())), true);
			pw.println("RSPi-100");
	}
	
	private static void messageHandler(String message){
		System.out.println("message to handle: "+ message);
		String[] messageSegments = message.split("\\?");
		String action;
		String data;
		
		//message format "action=switchON?data=1"
		if(messageSegments[0].contains("action=")){
			action=messageSegments[0].split("=")[1];
			data=messageSegments[1].split("=")[1];
			doTask(action,data);
		}
		else ; //wrong format
	} 
	
	private static void doTask(String action, String data){
		if(action=="switchON"){
			//switch on the particular port
			// confirmation message format "RSPiId&action=update&data=1&status=switchedON?"
			sendMessage("action=update&data=1&status=switchedON");
		}
		else if(action.equals("switchOFF")){
			//switch off the particular port
		}
		else if(action.equals("giveUpdate")){
			// send info about all pins
		}
		else if(action.equals("connection-confirmed")){
			System.out.println("Raspberry Pi is connected to the Cloud");
		}
	}
	
	private static void sendMessage(String message){
		pw.println("RSPiId&"+message);
	}
	
	public static void main( String argv[] ){
		
		while(true){
			
			try {
				newConnectionInit();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			String line;
			try {
				while(( line = br.readLine()) != null){
					messageHandler(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Connection Error");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
