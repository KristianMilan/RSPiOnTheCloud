import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class CommKeeper implements Runnable{
	
	Socket socket;
	BufferedReader br;
	PrintWriter pw;
	String threadId;
	RSPi raspberry;
	
	public CommKeeper(String threadId, RSPi raspberry, Socket socket) throws IOException{
		// write online to database
		setThreadId(threadId);
		setRaspberry(raspberry);
		setSocket(socket);
		setBr(new BufferedReader(new InputStreamReader(socket.getInputStream())));
		connConformation();
		askForPinsUpdate();
	}
	
	public CommKeeper(String threadId, RSPi raspberry, Socket socket, BufferedReader br) throws IOException{
		//write online to database
		setThreadId(threadId);
		setRaspberry(raspberry);
		setSocket(socket);
		setBr(br);
		setPw(new PrintWriter( 
				new BufferedWriter( new OutputStreamWriter(
						socket.getOutputStream())), true));
		connConformation();
		askForPinsUpdate();
	}
	
	public CommKeeper(String threadId, RSPi raspberry, Socket socket, BufferedReader br, PrintWriter pw) throws IOException{
		//write online to database
		setThreadId(threadId);
		setRaspberry(raspberry);
		setSocket(socket);
		setBr(br);
		setPw(pw);
		connConformation();
		askForPinsUpdate();
	}
	
	private void connConformation(){
		sendMessage("action=connection-confirmed?data=0");
	}
	//return true if the message is successfully sent and received
	public void sendMessage(String message){
		//exception to handle failure?
		pw.println(message);
	}	
	
	public void askForPinsUpdate(){
		sendMessage("action=giveUpdate?data=all");
	}
	
	public void setThreadId(String threadId){
		this.threadId=threadId;
	}
	
	public String getThreadId(){
		return threadId;
	}
	
	public void setRaspberry(RSPi raspberry){
		this.raspberry=raspberry;
	}
	
	public RSPi getRaspberry(){
		return raspberry;
	}
	
	public void setSocket(Socket socket) throws IOException {
		this.socket = socket;
		//start the thread again ?
	}

	public void setBr(BufferedReader br) {
		this.br = br;
	}
	
	public void setPw(PrintWriter pw){
		this.pw=pw;
	}
	
	private void messageHandler(String message){
		String[] messageSegments = message.split("&");
		String action=null;
		String data=null;
		
		if(messageSegments[0].contains(threadId)){
			if(messageSegments[1].contains("action=")){
				action=messageSegments[1].split("=")[1];
				data=messageSegments[2].split("=")[1];
			
				if(action.equals("update")){
					String state=null;
					state=messageSegments[3].split("=")[1];
					raspberry.setPinState(Integer.parseInt(data),
							Boolean.valueOf(state));
					System.out.println(threadId+": pin "+data+" state="+state);
					// update database
				}
				else ; //wrong format
			}
			else ;//wrong format
		}
		else ; //wrong format
	} 
	
	public void run(){
		String line;
		try { 
			while ((line = br.readLine()) != null){
				System.out.println("this was received from: "
						+threadId+": "+line);
				messageHandler(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Connection closed with "+threadId);
		//write offline to database
		//is it gonna be always executed here if it throws an exception?
	}
}
