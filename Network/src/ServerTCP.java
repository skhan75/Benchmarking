/****************************************************************************************
 * NAME: SAMI AHMAD KHAN
 * CWID: A20352677
 * COURSE: CS 553: CLOUD COMPUTING
 * PROGRAMMING ASSIGNMENT NO.: 01																   
 * TOPIC: BENCHMARKING TOOL
 * @author SamAK
*****************************************************************************************/
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {
	
	public static void main(String[] args) throws IOException{
		
		System.out.println("*******************************************");
		System.out.println("|         TCP SERVER CONNECTION           |");
		System.out.println("*******************************************");
		
		Socket socket = new Socket();
		tcpServerManager tSman;
		int port = 5555;
		ServerSocket server = new ServerSocket(port);
		InetAddress add = InetAddress.getLocalHost();
		
		
		System.out.println("\nSERVER STARTED !");
		System.out.println("\n--------------------------------");
		System.out.println(" CURRENT SERVER INFORMATION ");
		System.out.println("--------------------------------");
		System.out.println("IP address : " + add.getHostAddress());
		System.out.println("Port Number: " + port);	 
		System.out.println("--------------------------------");
		
		System.out.println("\nWaiting for Clients to connect on port " +server.getLocalPort() + "....\n");
		//ExecutorService service = Executors.newFixedThreadPool(threadNo);
		while(true) {    
			socket = server.accept(); // Listening from the Client
			String Caddress = socket.getInetAddress().getHostAddress();
			int Cport = socket.getPort();
			System.out.println("Client Thread connected at Address: "+ Caddress+" & Port: "+ Cport+" \n");
			tSman = new tcpServerManager(socket);//Initializing an object of ServerManager and starting it in a thread.
			Thread t = new Thread(tSman); // Creating thread and assigning the thread to ServerManager
			t.start(); 
		}
	 }	

}
