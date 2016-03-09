import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/* To be done:
 *    - handle exception whed the cloud refuses the connection (socket closed)
 *    - print error message if the RSPi is not registered
 */

public class RSPi_client {
	
	static Socket socket;
	static BufferedReader br;
	static PrintWriter pw;
	static HardwareCtrl HwCtrl;
	static String RSPiId;
	
	private static void newConnectionInit() throws UnknownHostException, IOException{
			socket = new Socket("192.168.1.15",200);
			br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw=new PrintWriter( 
				new BufferedWriter( new OutputStreamWriter(
						socket.getOutputStream())), true);
			RSPiId="RSPi"+GetNetworkAddress.GetAddress("mac");
			pw.println(RSPiId);
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
		if(action.equals("switchON")){
			//switch on the particular port
			int pinNo=Integer.parseInt(data);
			HwCtrl.setPinState(pinNo, true);
			// confirmation message format "RSPiId&action=update&data=1&status=true"
			sendMessage("action=update&data="+pinNo+"&state="+HwCtrl.getPinStateInfo(pinNo));
		}
		else if(action.equals("switchOFF")){
			//switch off the particular port
			int pinNo=Integer.parseInt(data);
			HwCtrl.setPinState(pinNo, false);
			sendMessage("action=update&data="+pinNo+"&state="+HwCtrl.getPinStateInfo(pinNo));
		}
		else if(action.equals("giveUpdate")){
			// send info about all pins
			if(data.equals("all")){
				sendMessage("action=update&data=0&state="+HwCtrl.getPinStateInfo(0));
				sendMessage("action=update&data=1&state="+HwCtrl.getPinStateInfo(1));
				sendMessage("action=update&data=2&state="+HwCtrl.getPinStateInfo(2));
			}
		}
		else if(action.equals("connection-confirmed")){
			System.out.println("Raspberry Pi is connected to the Cloud");
		}
	}
	
	private static void sendMessage(String message){
		pw.println(RSPiId+"&"+message);
	}
	
	public static void main( String argv[] ){
		
		// get unique id based on mac address
		if(argv.length != 0){
			if(argv[0].equals("-id")){
				System.out.println("This device Id:"+GetNetworkAddress.GetAddress("mac"));
				System.exit(0);
			}
		}

		HwCtrl= new HardwareCtrl();
		
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
		
			
			try {
				String line;
				while(( line = br.readLine()) != null){
					messageHandler(line);
				}
			} catch (IOException e) {
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
