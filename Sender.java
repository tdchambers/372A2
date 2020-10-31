import java.io.*;
import java.util.*; 
import java.net.*; 

public class Sender{
  
  public static void main(String[] args) {
    
    String iP = args[0];
    int senderPort = Integer.parseInt(args[1]);
    int receiverPort = Integer.parseInt(args[2]);
    String fileName = args[3];
    int maxSize = Integer.parseInt(args[4]);
    int timeout = Integer.parseInt(args[5]); 
    
    byte[] buffer = new byte[1024];
		DatagramPacket packet = new DatagramPacket(buffer, 1024);
		DatagramSocket socket = new DatagramSocket(null);
    socket.bind(new InetSocketAddress(iP, senderPort));
    socket.setSoTimeout(timeout); 
    
    //read file
    StringBuilder stringBuilder = new StringBuilder(); 
    Scanner scanner = new Scanner (new File(fileName));
    while (scanner.hasNextLine()){
      stringBuilder.append(scanner.nextLine());
      stringBuilder.append("\n");
    }
    scanner.close();
    
   String fileData = stringBuilder.toString();  
	  
   long timer = System.currentTimeMillis();

   for (int i = 0; i < (fileData.length() + maxSize) +2; i++){
	   if(i < (fileData.length() / maxSize) + 1){
		   byte[] b = new byte[maxSize +1];
		   //create byte array here
		   
	   }
	   else{
		   //signal end of transmission
		 //  byte[] b = new byte[]{(byte) 'end' ,(byte) 5};
	   }
	
	socket.send(new DatagramPacket(b, b.length, InetAddress.getByAddress(iP), receiverPort));
		   
	try{
		int x = 5; 
		
	}catch(SocketTimoutException e){
		i--;
		//resending the datagram because a timeout has occured. 
	}
   }
	  
	  
	   long transmissionTime = System.currentTimeMillis() - timer;
	   System.out.println("The total transmission time (ms) is: " + transmissionTime);
	   socket.close(); 
  }
		   
	   
   }

   
