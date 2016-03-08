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
	BufferedReader br;
	PrintWriter pw;
	
	//HashMap could be static
	public CommHandler(Socket s, HashMap<String, CommKeeper> threads){
		this.Sock=s;
		this.threads=threads;
		
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
		System.out.println("conn accepted");
		
		
		String line;
		try {
			while ((line = br.readLine()) != null)
			{
				System.out.println("line received: "+line);
				String[] messageParts=line.split("&");
				String source= messageParts[0];
				
				//this will be changed to regex that identifies our raspberries
				if(source.contains("RSPi")){
					System.out.println("new incomming connection from a RaspberyPi:"+ source);
					threads.put(source, new CommKeeper(source, Sock, br, pw));
					new Thread(threads.get(source)).start();
					break;
					//check with the database if the source exists
					//check if the thread for communication with this one is running
					// if yes update the fields of the class that handles connection with this Raspberry
					// if not create instance of the class that handles connection and save it to hashmap
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
					//read message and pass it
					//threads.get(line.split(";")[1]).sendMessage(line.split(";")[2]);
					
			}	
}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
} 