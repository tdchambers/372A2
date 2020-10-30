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
    
    
    
  }
  
  
}
