package hmw1.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connector {

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	
	public Connector(Socket socket) {
		
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"),true);
		} catch (IOException e) {	
			e.printStackTrace();
		}
	}
	
	public boolean isConnected() {
		return socket.isConnected();
	}
	
	public void sendMsg(String toSend){
		out.println(toSend);
		out.flush();
		
	}
	public String readMsg() throws IOException{
		return in.readLine();
	}
	
	public boolean hasMsg() throws IOException{
		return in.ready();
	}
	
	public void closeConnection(){
		try {
			socket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (NullPointerException e){
			//ignore
		}
	}
	
	
	

}
