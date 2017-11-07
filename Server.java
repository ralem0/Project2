/*
 * This is the server class that sends its ip address and 
 * port number
 */
package localchat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;

/**
 *
 * @author Antoinette Addo
 * @version 2017.10.12
 */

public class Server {
	
	private DatagramSocket socket;
	private InetAddress addr;
	private HashSet<SocketAddress> group;
	private boolean firstTime;
	
        //server constructor
	public Server() {
		group = new HashSet<>();
		int port = 5000;
                firstTime = true;
		System.out.println("*** Server ***");
		
		try {
			socket = new DatagramSocket(port);  
			addr = InetAddress.getLocalHost();
			
			String hostName = addr.getHostName();
			System.out.println("IP Address: " + addr);
			System.out.println("Port Number: " + port);
			System.out.println("Hostname: " + hostName);
			
			while(true) {
				byte[] buffer = new byte[1024];
				byte[] inBuffer = new byte[1024];
				DatagramPacket packet = new DatagramPacket(buffer,buffer.length);   //create new udp packet
				socket.receive(packet);
				System.out.println(new String(packet.getData()));
				group.add(packet.getSocketAddress());
				
				for(SocketAddress s: group) {
					new Thread(new Sender(socket,s)).run();
				}
                        }
		}
		catch(SocketException socketErr) {
			System.out.println("Socket cannot be created.");    //print out statement for error in socket
		}
		catch(UnknownHostException hostErr) {
			System.out.println("Host cannot be found");         //print out statement for error in host name
		}
		catch(IOException ioErr) {      
                    System.out.println("Error sending packet.");            //failed message spits out when the packet doesnt send
			
		}
	}
}
