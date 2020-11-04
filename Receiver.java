
import java.io.*;
import java.net.*;

import javax.swing.JTextField;
public class Receiver {
	DatagramSocket socket ;
	private JTextField inOrderText;
	
	public void Receiving(String iP , int senderPort, int receiverPort, String outputTxtName) throws SocketException {
		System.out.println("Begin receving on: " + iP + " on port " + receiverPort);
		socket = new DatagramSocket(null);
		System.out.println("before bound ");
		socket.bind(new InetSocketAddress(iP , receiverPort));
		System.out.println("after bound");
		byte[] buffer = new byte[1024];
		DatagramPacket packet = new DatagramPacket(buffer, 1024);
		int inOrderCount = 0;
		StringBuilder outputData = new StringBuilder(); 
		while (true) {
			try {
				System.out.println("Waiting for data");
				socket.receive(packet);
				System.out.println("Packet Received...");
				
				for (int i = 0; i < packet.getLength() - 1 ; i++) {
					outputData.append((char) packet.getData()[i]);
					
				}
				
				//inOrderText.setText(inOrderCount+ "");
				int seqNumber = packet.getData()[packet.getLength()- 1 ];
				System.out.println(seqNumber);
				System.out.println(outputData.toString());
				if(outputData.toString().contains("\t") && seqNumber == 5) {
					writeToOutputFile(outputTxtName, outputData.toString());

				}else{
					inOrderCount++;
				}
				
				
				//send ACKS to sender 
				String ACK = "ACK" + seqNumber;
				socket.send(new DatagramPacket(ACK.getBytes(),ACK.getBytes().length, InetAddress.getByName(iP),senderPort));
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
	  public void updateInOrderText(JTextField text) {
		  this.inOrderText = text;
	  }

}

