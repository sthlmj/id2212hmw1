package hmw1.server;

import hmw1.tools.Filehandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class ClientWaitThread extends Thread {

	private static ServerSocket serverSocket;
	private static Socket socket;
	private static int port;
	private static final int defaultPort = 1337;
	private boolean keepAlive;
	private ThreadGroup threadgroup;
	private Filehandler filehandler;
	
	public ClientWaitThread() throws IOException {
		this.port = defaultPort;
		this.serverSocket = new ServerSocket(port);
		this.keepAlive = true;
		threadgroup = new ThreadGroup("ClientGameThreads");
		filehandler = new Filehandler("src/hmw1/tools/words.txt");
	}
	
	public ClientWaitThread(int port) throws IOException {
		this.port = port;
		this.serverSocket = new ServerSocket(port);
		this.keepAlive = true;
		threadgroup = new ThreadGroup("ClientGameThreads");
		filehandler = new Filehandler("src/hmw1/tools/words.txt");
	}
	
	
	/**
	 * @Override Threads method
	 */
	public void run() {
		
		System.out.println("Server up and waiting for connection(port="+ port  + ")...");
		while(!isInterrupted())
		{
			
		   try {
				socket = serverSocket.accept();//accept new client
				if(socket!=null) {
					new ClientGameThread(filehandler,threadgroup,socket).start();//start a new thread for-each new client
				}
					
			} catch (IOException e ) {
				
				super.interrupt();
				threadgroup.interrupt();
			} 
		   
		   System.out.println("New client connected, numbers of client connected is: " + threadgroup.activeCount());
		
		}
		
		
		interrupt();
		System.out.println("ThreadGroup was interrupted");
	}

	/**
	 * Interupts both this thread and the threads that it is responsible for
	 */
	@Override
	public void interrupt() {
		try {
			//we must destroy serversocket as the accept() function is a blocking function
			serverSocket.close();  
			
		} catch (IOException e) {
			//ignore
		}
		threadgroup.interrupt();
		super.interrupt();
	}
}
