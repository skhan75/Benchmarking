import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class ClientUDP implements Runnable {
	private int buf;
	byte[] inBuf;
	static byte[] outBuf;
	static int port ;
	static String ip;
	private InetAddress address;
	DatagramPacket outPacket;
	DatagramPacket inPacket;
	DatagramSocket socket;
	private int clientID;
	static int blockSize;
	
	public static void main(String[] args) throws IOException
    {
		
        
        System.out.println("****************************************");
		System.out.println("         UDP CLIENT CONNECTION          ");
		System.out.println("****************************************\n");
		
		Scanner in = new Scanner(System.in);
		
		System.out.println("\nEnter the Server's IP you want to connect.");
		ip = in.nextLine(); //Ask's the user for the Server's IP address it wants to connect
		System.out.println("\nEnter the Server Port you want to connect (6666)");
		port = in.nextInt(); //Asks the user for the Server's port it wants to connect
		
		
        
        try{
			for(int i=1; i<=2; i++){ 

				System.out.println("\n---------------------------------------");
        		System.out.println("Number of Concurrent Client Threads# ------> "+i);
            	System.out.println("---------------------------------------\n");
            	
		        InetAddress address = InetAddress.getByName(ip);
	        	
				int array[] = {1,1000,64000 };
				
	        	for(int j=1; j<=i; j++){
	        		
	        		
	            	for (int k=1; k<=3; k++) {
	            		blockSize = array[k-1];
	            		ClientUDP cUdp = new ClientUDP();
	            		cUdp.clientID = j;
	            		Thread t = new Thread(cUdp);
	            		t.start();
	            		t.join();
	        		 }
	            	System.out.println("Thread " +  j + " exiting...\n");
	        	}
	        	
			}
        }
        catch(InterruptedException e){
      	  e.printStackTrace();
        }
}


	@Override
	public void run() {	
		System.out.println("\nRunning Thread # "+clientID);
		System.out.println("BLOCK SIZE --> "+blockSize);
		float speed, time, res;
		
		StringBuilder sb = new StringBuilder(blockSize);
	    for (int i = 0; i < blockSize; i++) {
	        sb.append('X');
	    }
	    
		String message = sb.toString();
		
		outBuf = message.getBytes();
		inBuf = new byte[1024];

		try {
			socket = new DatagramSocket();
			long start = System.currentTimeMillis();
			
			for(int i=0; i<10; i++){
				
				upload();
				
				Thread.sleep(3000);
				
				download();
			}
			long end = System.currentTimeMillis();
			long difference = end - start;
			
			
			speed = (2 * blockSize * 1000) / 1048576;
			time = ((float)difference / 1000);
			res = speed / time;
			
			System.out.println("Packet Size: " + blockSize );
			System.out.println("Latency: " + difference + "ms");
			System.out.println("Throughput: " + res + " Mbps");
			
		} 
    	catch (InterruptedException | IOException e1) {
			e1.printStackTrace();
		}
	}

	private void upload() throws IOException {
		//outBuf= new byte[1024];
		
		outPacket = new DatagramPacket(outBuf, outBuf.length, InetAddress.getLocalHost(), port);
		socket.send(outPacket);
		//System.out.println("Bytes Sent");
		
		//socket.close();
	}

	private void download() throws IOException {
		//inBuf = new byte[1024];
		inPacket = new DatagramPacket(inBuf, inBuf.length);
		socket.receive(inPacket);
		//System.out.println(new String(inPacket.getData(), 0, inPacket.getLength()));
		//System.out.println("Bytes Received");
		//socket.close();
	}


}