package hmw1.server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class HangmanServer {
	
	private static ServerSocket serverSocket;
	private static Socket socket;
	private static int port;
	private static final int defaultPort = 1337;

	public static void main(String[] args)  {
            
            if(args.length == 1) {
               
                System.out.println("received argument: " + args[0]);
               
                try{
                  port = Integer.parseInt(args[0]); 
                }catch(NumberFormatException e){
                    port = defaultPort;
                }
                
            }else{
                port = defaultPort;
            }
		
		try{
			ClientWaitThread gamethread = new ClientWaitThread(port);
			gamethread.start();//start a thread to wait for new clients
			
			Scanner sc = new Scanner(System.in);
			while(true){//take input from console
				String str=sc.nextLine();
				if(str.equals("exit")){//when user types exit
					gamethread.interrupt();//stops all threads
					System.out.println("Shuting down server");
					break;
				}
			}
			
			
		}catch(BindException e){
			System.out.println("Port is already in use; exiting");
		}catch(IOException e){
                    //ignore error
                }
  	
	}

}
