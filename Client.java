/*
 * The client then will start up and create a socket based 
 * on the information provided by the server.
 */
package localchat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * @author Robel Alem & Quinn Conroy
 * @version 2017.11.20
 */

public class Client {
	
    private DatagramSocket socket;
    private Scanner scan;
    private InetAddress serverAddr;
    private int port;
	
	public Client() {
		Scanner scan = new Scanner(System.in);
		System.out.println("*** CLIENT ***");
		System.out.println("Enter hostname: ");
		String hostname = scan.nextLine();
		System.out.println("Enter port number: ");
		port = scan.nextInt();
		try{
			serverAddr = InetAddress.getByName(hostname);
			socket = new DatagramSocket(port);
			DatagramPacket ping = new DatagramPacket(new byte[1024],1024,serverAddr,port);
			socket.send(ping);
			
			while(true) {
				new Thread(new Receiver(socket)).run();
				new Thread(new Sender(socket,serverAddr)).run();
			}
		}
		catch(SocketException error) {
			System.out.println("Socket could not be created at port: " + port);
		}
		catch(UnknownHostException error) {
			System.out.println("Host could not be found.");
		}
		catch(IOException error) {
			System.out.println("Error sending packet.");
		}
	}
}
   
            
            

