import javax.swing.JFrame;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import java.io.*;
import java.net.*;

import javax.swing.JTextField;

public class StopAndWaitReceiverMain{
			public static void main(String[] args){
			
				Gui F = new Gui();
				F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				 F.layoutView();
				 F.setSize(400,300);
				 F.setVisible(true);
			}
}


class Gui extends JFrame{

		private static final long serialVersionUID = 1L;
					
						
		//Labels
					private final Receiver receiver = new Receiver();
					private JLabel Port_label = new JLabel("Port #: ", SwingConstants.LEFT);
					
					//Buttons
					public static JButton receiveButton = new JButton("RECEIVE");
					public static JButton Clear= new JButton("CLEAR");
					public static JButton Connect= new JButton("Connect");
					public static JButton Disconnect= new JButton("Disconnect ");
				
					//TextFields
					
					public JLabel IP_label = new JLabel("IP Address: ", SwingConstants.LEFT);
					
					public static JTextField IPtxt = new JTextField();
					
					public JLabel ACKPORTlabel = new JLabel("Send ACK port#: ", SwingConstants.LEFT);

					public static JTextField ACKPORTtxt  = new JTextField();

					public JLabel RECDATAlabel = new JLabel("Receive Data port#: ", SwingConstants.LEFT);

					public static JTextField RECDATAtxt = new JTextField();

					public JLabel FILETOWRITElabel = new JLabel("file to write: "); 
					
				
					public static JTextField FILETOWRITEtxt = new JTextField();
					
										
					public JLabel NUMPACKETSlabel = new JLabel("# of received in-order packets");
					
					public static JTextField  NUMPACKETStxt = new JTextField("0"); 
					
					public JPanel checkboxPanel = new JPanel();
				  
					public JCheckBox reliableBox = new JCheckBox("Reliable");

					public void addElements() {
					receiveButton.setBackground(Color.GREEN);
					receiveButton.setOpaque(true);
					
					
					this.add(IP_label);
					this.add(IPtxt);
					this.add(ACKPORTlabel);
					this.add(ACKPORTtxt);

					this.add(RECDATAlabel);
					this.add(RECDATAtxt);
					
					this.add(FILETOWRITElabel);
					this.add(FILETOWRITEtxt);
					this.add(checkboxPanel);
					checkboxPanel.add(reliableBox);
					this.add(receiveButton);
					//this.add(Clear);
					
					this.add(NUMPACKETSlabel);
					this.add(NUMPACKETStxt);



					

						
					}
					
					public void SubmitButtonHandler(ActionEvent e) {
						 String address = IPtxt.getText();
			             int senderPort = Integer.parseInt(ACKPORTtxt.getText());
			             int receiverPort = Integer.parseInt(RECDATAtxt.getText());
			             String outputFileName = FILETOWRITEtxt.getText();
			             boolean isReliable = reliableBox.isSelected();
			             System.out.println("file to create: " + outputFileName);
			          
			             
			             new SwingWorker<Void, Void>() {
			                 @Override
			                 public Void doInBackground() {
			                     try {
			                       //  receiver.setInOrderPacketLabel(NUMPACKETStxt); // Assign our JLabel to our ReceiverHandler to update it
			                         receiver.Receiving(address, senderPort, receiverPort, outputFileName,isReliable);
			                        // receiver.updateInOrderText(NUMPACKETStxt);
			                     } catch (IOException exception) {
			                         exception.printStackTrace();
			                     }
			                     return null;
			                 }
			             }.execute();
					}	
					
					public void ClearButtonHandler(ActionEvent e) {
						IPtxt.setText("");
						ACKPORTtxt.setText("");
						RECDATAtxt.setText("");
						FILETOWRITEtxt.setText("");


					}
					

					
					public void DisconnectButtonHandler(ActionEvent e) {
						
					}

					
					public void layoutView() {
						this.setLayout(new GridLayout(0,2,5,1));
						this.addElements();
						

						
						receiveButton.addActionListener(this::SubmitButtonHandler);
						Clear.addActionListener(this::ClearButtonHandler);

						this.setTitle(" GUI");
					
						
						
					}
					
					
					
					
					

			}
						


			

 class Receiver {
	DatagramSocket socket ;
	private JTextField inOrderText;
	
	public void Receiving(String iP , int senderPort, int receiverPort, String outputTxtName, boolean isReliable) throws SocketException {
		System.out.println("Begin receving on: " + iP + " on port " + receiverPort);
		socket = new DatagramSocket(null);
		//System.out.println("before bound ");
		socket.bind(new InetSocketAddress(iP , receiverPort));
		//System.out.println("after bound");
		byte[] buffer = new byte[1024];
		DatagramPacket packet = new DatagramPacket(buffer, 1024);
		int packCount = 0; 
		int inOrderCount = 0;
		StringBuilder outputData = new StringBuilder(); 
		while (true) {
			try {
				System.out.println("\nWaiting for data\n");
				socket.receive(packet);
				System.out.println("\nPacket Received...\n");
				packCount++;
				

				if (isReliable || packCount % 10 != 0) {
					for (int i = 0; i < packet.getLength() - 1 ; i++) {
						outputData.append((char) packet.getData()[i]);
						
					}
					
					//inOrderText.setText(inOrderCount+ "");
					int seqNumber = packet.getData()[packet.getLength()- 1 ];
					System.out.println("Sequence #: " + seqNumber);
					//System.out.println(outputData.toString());
					if(outputData.toString().contains("\t") && seqNumber == 5) {
						writeToOutputFile(outputTxtName, outputData.toString());
						System.out.println(outputTxtName + " successfully created\n closing socket");
						
					}else{
						inOrderCount++;
						//inOrderText.setText(""+inOrderCount);
						
					}
					
					
					//send ACKS to sender 
					String ACK = "ACK" + seqNumber;
					System.out.println(ACK);
					socket.send(new DatagramPacket(ACK.getBytes(),ACK.getBytes().length, InetAddress.getByName(iP),senderPort));
				}else {System.out.println("Unreliable Packet");}
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

