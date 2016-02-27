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

public class ServerUDP {

    private static int clientID = 1;
    
    

    public static void main(String[] args) throws IOException
    {
    	DatagramSocket serverSocket;
        int port = 6666;
        
    	System.out.println("\nUDP SERVER STARTED !");
		System.out.println("\n--------------------------------");
		System.out.println(" CURRENT SERVER INFORMATION ");
		System.out.println("--------------------------------");
		System.out.println("IP address : " + InetAddress.getLocalHost().getHostAddress());
		System.out.println("Port Number: " + port);	 
		System.out.println("--------------------------------");
        


        serverSocket = new DatagramSocket(port);
        byte[] buffer = new byte[1024];
        while (true)
        {
            try
            {
                DatagramPacket packet =  new DatagramPacket(buffer, buffer.length );
                serverSocket.receive(packet);
                //System.out.println("SERVER: Accepted connection.");
                //System.out.println("SERVER: TO DO: "+new String(packet.getData(), 0, packet.getLength()));

                //new socket created with random port for thread
                //DatagramSocket threadSocket = new DatagramSocket();

                Thread t = new Thread(new udpServerManager(serverSocket, packet));

                t.start();

            } catch (Exception e)
            {
                System.err.println("Connection Error !.");
            }
        }
    }
}