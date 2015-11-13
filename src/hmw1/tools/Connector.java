package hmw1.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connector{

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	
        /**
         * 
         * @param socket - for connection
         * @throws IOException when connection couldnt be establish
         */
	public Connector(Socket socket) throws IOException{
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"),true);
		
	}
	
	public boolean isConnected() {
		return socket.isConnected();
	}
	
	public void sendMsg(String toSend){
             final String str = toSend;
                
                
            Thread sendMsgThread = new Thread(){
                public void run() {
                    
                out.println(str);
		out.flush();
                    
                }
            };
            sendMsgThread.start();

		
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
			
		} catch (NullPointerException e){
			//ignore
		}
	}
	
	
	

}
