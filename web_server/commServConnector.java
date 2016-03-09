import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/*
 * To be done:
 * 	  -recognise wrong command such as a wrong pin number
 */
public class commServConnector {
	String id;
	Socket socket;
	BufferedReader br;
	PrintWriter pw;
	
	public commServConnector(){
		this.id="WebServer";
		
		try {
			socket = new Socket("127.0.0.1",200);
			pw=new PrintWriter( 
					new BufferedWriter( new OutputStreamWriter(
							socket.getOutputStream())), true);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// returns true if the command is received properly by
	// the communication server and resent to the Raspberry
	public boolean setPin(String RSPi_id, int pinNo, boolean state){
		if(state==true)
			pw.println(id+"&"+RSPi_id+"&action=switchON?data="+pinNo);
		else
			pw.println(id+"&"+RSPi_id+"&action=switchOFF?data="+pinNo);
		
		try {
			String line;
			line=br.readLine();
			if(line.equals("Message sent to "+ RSPi_id)){
				return true;
			}
			else return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
