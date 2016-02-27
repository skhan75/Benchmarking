/****************************************************************************************
 * NAME: SAMI AHMAD KHAN
 * CWID: A20352677
 * COURSE: CS 553: CLOUD COMPUTING
 * PROGRAMMING ASSIGNMENT NO.: 01																   
 * TOPIC: BENCHMARKING TOOL
 * @author SamAK
*****************************************************************************************/
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientTCP implements Runnable{
	static Socket clientSocket;
	static int threadNo = 1;
	static File file;
	static long FileSize;
	static DataOutputStream outputStream;
	static DataInputStream inputStream;
	static InputStream inFromServer;
	static OutputStream outToServer;
	static ClientTCP cTcp;
	static int count = 1;
	static int blockSize;
	static int clientID;
	private static String ip;
	private static int port;
	
	
	

	public ClientTCP(int blockSize, String ip, int port) {
		this.blockSize = blockSize;
		this.ip = ip;
		this.port = port;
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		
				
		System.out.println("****************************************");
		System.out.println("         TCP CLIENT CONNECTION          ");
		System.out.println("****************************************\n");
		
		Scanner in = new Scanner(System.in);
		
		System.out.println("\nEnter the Server's IP you want to connect.");
		ip = in.nextLine(); //Ask's the user for the Server's IP address it wants to connect
		System.out.println("\nEnter the Server Port you want to connect(5555)");
		port = in.nextInt(); //Asks the user for the Server's port it wants to connect
		
        
      for(int i=1; i<=2; i++){ 
    	   
    	   	System.out.println("\n---------------------------------------");
    	   	System.out.println("Number of Concurrent Client Threads# ------> "+i);
    	   	System.out.println("---------------------------------------\n");

        	//cTcp = new ClientTCP();
			int array[] = {1,1000,65000 };
			
        	for(int j=1; j<=i; j++){
        		
        		System.out.println("Running Client # ------> "+j);
        
        		for (int k=1; k<=3; k++) {
        			
        			blockSize = array[k-1];
        			ClientTCP cTcp = new ClientTCP(blockSize, ip, port);
        			cTcp.clientID = j;
        			cTcp.ip = ip;
        			Thread t = new Thread(cTcp);
        			t.start();
        			t.join();     			
        		 }
        		System.out.println("Thread " +  j + " exiting...\n");
        	}	
        	
        }
    	  
      }

	@Override
	public void run() {	
		try {
			clientSocket = new Socket(ip, port);
			inFromServer = clientSocket.getInputStream();
			inputStream = new DataInputStream(inFromServer);
			outToServer = clientSocket.getOutputStream();
			outputStream = new DataOutputStream(outToServer);
			
			System.out.println("BLOCK SIZE --> "+blockSize);
			float speed, time, res;
			
			StringBuilder sb = new StringBuilder(blockSize);
		    for (int i = 0; i < blockSize; i++) {
		        sb.append('X');
		    }
		    
			String message = sb.toString();
			
			System.out.println("-----------------------------------------------------------");
			System.out.println("Sending..." + message.length());
			
			long start = System.currentTimeMillis();
			
			for(int i=0; i<1000; i++){
				uploadTester(message);
			}
			System.out.println("Upload Successfull");
			
			for(int j=0; j<1000; j++){
				downloadTester();	
			}
			System.out.println("Download Successfull");
			
			
			long end = System.currentTimeMillis();
			long difference = end - start;
			
			speed = (2 * blockSize * 1000) / 1048576;
			time = ((float)difference / 1000);
			res = speed / time;

			System.out.println("Latency: " + difference + "ms");
			System.out.println("Throughput: " + res  + " Mbps");

			
			System.out.println("----------------------------------------------------------");
		
		} 
		catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	private void uploadTester(String message) throws IOException {
		outputStream.writeUTF(message);	
	
	}
	
	private void downloadTester() throws IOException {
		inputStream.readUTF();
		
	} // End of try block
		
	
	
	
}
