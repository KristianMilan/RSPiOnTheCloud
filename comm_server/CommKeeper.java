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
	
	public CommKeeper(String threadId, Socket socket) throws IOException{
		setThreadId(threadId);
		setSocket(socket);
		setBr(new BufferedReader(new InputStreamReader(socket.getInputStream())));
		connConformation();
	}
	
	public CommKeeper(String threadId, Socket socket, BufferedReader br) throws IOException{
		setThreadId(threadId);
		setSocket(socket);
		setBr(br);
		setPw(new PrintWriter( 
				new BufferedWriter( new OutputStreamWriter(
						socket.getOutputStream())), true));
		connConformation();
	}
	
	public CommKeeper(String threadId, Socket socket, BufferedReader br, PrintWriter pw) throws IOException{
		setThreadId(threadId);
		setSocket(socket);
		setBr(br);
		setPw(pw);
		connConformation();
	}
	
	private void connConformation(){
		sendMessage("Welcome:"+threadId);
	}
	//return true if the message is successfully sent and received
	public void sendMessage(String message){
		//exception to handle failure?
		pw.println(message);
	}	
	
	public void setThreadId(String threadId){
		this.threadId=threadId;
	}
	
	public String getThreadId(){
		return threadId;
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

	public void run(){
		String line;
		try { 
			while ((line = br.readLine()) != null){
				System.out.println("this was received from: "
						+threadId+": "+line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Connection closed with "+threadId);
	}
}
