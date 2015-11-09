package hmw1.server;



import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class HangmanServer {
	
	private static ServerSocket serverSocket;
	private static Socket socket;
	private static int port;
	private static int defaultPort = 1339;

	public static void main(String[] args) throws IOException, InterruptedException {
		
		try{
			ClientWaitThread gamethread = new ClientWaitThread();
			gamethread.start();
			
			//simulate server automatic shutdown (shall be interrupted by console in future)
			//Thread.sleep(10000);
			//System.out.println("Shuting down server at timestamp: " + new Date().getTime());
			Scanner sc = new Scanner(System.in);
			while(true){
				String str=sc.nextLine();
				if(str.equals("exit")){
					gamethread.interrupt();//stops all threads
					System.out.println("Shuting down server at timestamp: " + new Date().getTime());
					break;
				}
			}
			
			
		}catch(BindException e){
			System.out.println("Port is already in use; exiting");
		}
		
		
		

		
				
			
			
			
		
			
		
		
		
	}

}
