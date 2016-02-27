import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class tcpServerManager implements Runnable {
	Socket socket;
	DataInputStream in;
	DataOutputStream out;
	
	public tcpServerManager(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try{
			/*System.out.println("Connection made to Client at "+ socket.getRemoteSocketAddress());
			in =new DataInputStream(socket.getInputStream());
			System.out.println(in.readUTF());
			
			out = new DataOutputStream(socket.getOutputStream());
	        out.writeUTF("Thank you for connecting to TCP Server "+ socket.getLocalSocketAddress() + "\nEnjoy !!\n");*/
	        
	        serverCommunicator();
		}
		
		catch(IOException e){ // Catching exception
	           e.printStackTrace(); }
		
	}

	private void serverCommunicator() throws IOException {
		String[] incoming = new String[10000];
		out = new DataOutputStream(socket.getOutputStream());
		in = new DataInputStream(socket.getInputStream());
		int i,j;
		
		System.out.println("Receving...");
		for(i = 0; i < 1000; i++){
			incoming[i] = in.readUTF();//.readByte();
		}
		System.out.println("Received " + i + "data from Client");
		
		System.out.println("Sending...");
		for(j = 0; j < 1000; j++){
			out.writeUTF(incoming[j]);
		}
		System.out.println("Sent " + j +" data to Client");
	}

}
