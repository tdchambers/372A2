import java.io.*;
import java.net.*;
public class Receiver {
	DatagramSocket socket ;
	
	public void Receiving(String iP , int senderPort, int receiverPort, String outputTxtName) throws SocketException {
		System.out.println("Begin receving on: " + IP + "on port " + receiverPort);
		socket = new DatagramSocket();
		socket.bind(new InetSocketAddress(IP , receiverPort));
		byte[] buffer = new byte[1024];
		DatagramPacket packet = new DatagramPacket(buffer, 1024);
		while (true) {
			try {
				socket.receive(packet);
				StringBuilder outputData = new StringBuilder(); 
				for (int i = 0; i < packet.getLength() - 1 ; i++) {
					outputData.append((char) packet.getData()[i]);
				}
				int seqNumber = packet.getData()[packet.getLength()- 1 ];
				writeToOutputFile(outputTxtName, outputData.toString());
			} catch(IOException exception) {
				break;
			}
		}
	}
	public void endReceiving() {
		socket.close();
	}
	
	public void writeToOutputFile(String outputTxtName, String outputData) {
		try {
			PrintWriter outputfile = new PrintWriter(outputTxtName);
			outputfile.println(outputData);
			outputfile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		


}
}
