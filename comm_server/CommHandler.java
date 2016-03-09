import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;


public class CommHandler implements Runnable{
	
	Socket Sock;
	HashMap<String, CommKeeper> threads;
	HashMap<String, RSPi> raspberries;
	BufferedReader br;
	PrintWriter pw;
	
	//HashMap could be static
	public CommHandler(Socket s, HashMap<String, CommKeeper> threads, HashMap<String, RSPi> raspberries){
		this.Sock=s;
		this.threads=threads;
		this.raspberries=raspberries;
		
		try {
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			pw = new PrintWriter( 
					new BufferedWriter( new OutputStreamWriter(
							Sock.getOutputStream())), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
	public void run(){
		System.out.println("new connection");

		String line;
		try {
			while ((line = br.readLine()) != null)
			{
				System.out.println("line received: "+line);
				String[] messageParts=line.split("&");
				String source= messageParts[0];
				
				//this will be changed to regex that identifies our raspberries
				if(source.contains("RSPi")){
					//check with the database if the source exists send error message if the RSpi is not registered
					System.out.println("new incomming connection from "+ source);
					raspberries.put(source, new RSPi(source, 0));
					threads.put(source, new CommKeeper(source, raspberries.get(source),
							Sock, br, pw));
					new Thread(threads.get(source)).start();
					break;
				}
				else if(source.equals("WebServer")){
					String destination=messageParts[1];
					String data=messageParts[2];
					if(threads.containsKey(destination)){
						threads.get(destination).sendMessage(data);
						// log to database
						pw.println("Message sent to "+ destination);
					}
					else{
						pw.println("Device "+ destination + " does not exist");
					}
				}
				else {
					// client not known
					Sock.close();
					System.out.println("Connection refused, client not known");
				}
}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
} 