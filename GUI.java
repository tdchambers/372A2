import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

public class Gui extends JFrame{
	/*--------------------------------
	  Have NOT tested with the other files 
		  
	  ----------------------------------------*/
	//  private final SocketClient socketClient;
		/**
		 * 
		 */
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
		
		this.add(receiveButton);
		this.add(Clear);
		
		this.add(NUMPACKETSlabel);
		this.add(NUMPACKETStxt);
		

			
		}
		
		public void SubmitButtonHandler(ActionEvent e) {
			 String address = IPtxt.getText();
             int senderPort = Integer.parseInt(ACKPORTtxt.getText());
             int receiverPort = Integer.parseInt(RECDATAtxt.getText());
             String outputFileName = FILETOWRITEtxt.getText();
            // boolean reliable = checkboxReliable.isSelected();
             
             new SwingWorker<Void, Void>() {
                 @Override
                 public Void doInBackground() {
                     try {
                       //  receiver.setInOrderPacketLabel(NUMPACKETStxt); // Assign our JLabel to our ReceiverHandler to update it
                         receiver.Receiving(address, senderPort, receiverPort, outputFileName);
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
		
		public void ConnectButtonHandler(ActionEvent e){
			
			
		}
		
		public void DisconnectButtonHandler(ActionEvent e) {
			
		}

		
		public void layoutView() {
			this.setLayout(new GridLayout(0,2,4,1));
			this.addElements();
			

			
			receiveButton.addActionListener(this::SubmitButtonHandler);
			Clear.addActionListener(this::ClearButtonHandler);

			this.setTitle("RECEIVER GUI");
		
			
			
		}
		
		
		
		
		

}
			

