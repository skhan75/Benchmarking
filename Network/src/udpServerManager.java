/****************************************************************************************
 * NAME: SAMI AHMAD KHAN
 * CWID: A20352677
 * COURSE: CS 553: CLOUD COMPUTING
 * PROGRAMMING ASSIGNMENT NO.: 01																   
 * TOPIC: BENCHMARKING TOOL
 * @author SamAK
*****************************************************************************************/
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class udpServerManager implements Runnable {

	private DatagramSocket threadSocket;
	private DatagramPacket packet;
	 byte[] inBuf;
	 byte[] outBuf = new byte[1024];

	public udpServerManager(DatagramSocket threadSocket, DatagramPacket packet) {
		this.threadSocket = threadSocket;
		this.packet = packet;
	}

	@Override
	public void run() {
			
			try {
				
			//	System.out.println("Bytes Downloading..");
				inBuf = packet.getData();
				
				InetAddress address = packet.getAddress();
				int port = packet.getPort();
				
				//System.out.println("Bytes Sending..");
				//String greeting = "WELCOME TO SERVER";
				//outBuf = greeting.getBytes();
				
				DatagramPacket outPacket = new DatagramPacket(inBuf, inBuf.length, address, port);
				threadSocket.send(outPacket);
				//System.out.println("Bytes Sent");
				
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}
}
		


