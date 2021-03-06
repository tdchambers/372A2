import java.io.*;
import java.util.*; 
import java.net.*; 

public class Sender{
  
  public static void main(String[] args) throws SocketException,IOException, FileNotFoundException {
    
    String iP = args[0];
    
    int senderPort = Integer.parseInt(args[1]);
    int receiverPort = Integer.parseInt(args[2]);
    String fileName = args[3];
    int maxSize = Integer.parseInt(args[4]);
    int timeout = Integer.parseInt(args[5]); 
    int ack;
    byte[] b = null;
    byte[] buffer = new byte[1024];
		DatagramPacket packet = new DatagramPacket(buffer, 1024);
		DatagramSocket socket = new DatagramSocket(null);
		
    try {
		socket.bind(new InetSocketAddress(iP, senderPort));
	    socket.setSoTimeout(timeout);
	} catch (SocketException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
  
    	
   
    
    //read file
    StringBuilder stringBuilder = new StringBuilder();
    System.out.println("original file name: " +fileName);
    Scanner scanner = new Scanner (new File(fileName));
    while (scanner.hasNextLine()){
      stringBuilder.append(scanner.nextLine());
      stringBuilder.append("\n");
    }
    scanner.close();
    
   String fileData = stringBuilder.toString();  
	  
   long timer = System.currentTimeMillis();

   for (int i = 0; i < (fileData.length() / maxSize) +2; i++){
	   if(i < (fileData.length() / maxSize) + 1){
		    b = new byte[maxSize +1];
		    int endIndex = i == fileData.length() / maxSize ? fileData.length() : maxSize * (i + 1);
	        for (int index = maxSize * i; index < endIndex; index++) {
	            b[maxSize + index - endIndex] = (byte) fileData.charAt(index);
	        }
	        b[maxSize] = (byte) (i % 2);
	        
		   //create byte array here
		   
	   }
	   else{
		   //signal end of transmission
		   System.out.println("final transmission...");
		 b  = new byte[]{(byte) '\t' ,(byte) 5};
	   }
	
	try {
		System.out.println("Sending DataGram packet...");
		//System.out.println(InetAddress.getByName(iP));
		//System.out.println(receiverPort);
		socket.send(new DatagramPacket(b, b.length, InetAddress.getByName(iP), receiverPort));
		System.out.println("SENT");
		System.out.println("Awaiting Acknowledgement... ");
		socket.receive(packet);

	   
	    ack = -1;
	   
	   for (byte byt: packet.getData()){
		   String value = String.valueOf((char) byt);
		   if(value.equals("0") || value.equals("1") || value.equals("5")){
			   
			   ack = Integer.parseInt(value);
		   }
		   
	   }
	  // System.out.println("ACK : " + ack);
	   if (ack != 0 && ack != 1 && ack != 5){
		   i--; 
		   System.out.println("The ack received is not valid. The datagram is being resent.");
		   
	   }
		else{
			System.out.println("The Ack received is valid.");  
		
	}
	   System.out.println("ACK :" + ack);
   	} catch (UnknownHostException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();

 	 }catch (SocketTimeoutException e1){
		System.out.println ("-----------------\nTimeout has occured\n-------------");
		
		i--; 
	}
   }

	  
	  
	   long transmissionTime = System.currentTimeMillis() - timer;
	   System.out.println("The total transmission time (ms) is: " + transmissionTime);
	   System.out.println("closing");
	   socket.close(); 
  }
  

		   
	   
   }
