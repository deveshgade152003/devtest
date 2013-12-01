package test1;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Heartbeat {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Ser s = new Ser ();
		Cli c = new Cli ();
		s.start();
		c.start();
		System.out.println("main ended");
		
	}

}
class Cli extends Thread {
	public void run () {
		try {
			DatagramSocket ds = new DatagramSocket(12345, InetAddress.getLocalHost()) ;
			String message = "gade";
			byte[] buf = new byte[256];
			buf = message.getBytes("UTF-8");
			
			
			byte[] recv = new byte[256];		
			DatagramPacket dpr = new DatagramPacket (recv, recv.length);
			while (true) {
				DatagramPacket dps = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(),12346);
				ds.send(dps);
				
				ds.receive(dpr);
				String mess = new String (recv, 0, dpr.getLength(), "UTF-8");
				
				System.out.println("C : sent gade " + "got " + mess );
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
class  Ser  extends Thread {
	public void run () {
		try {
			DatagramSocket ds = new DatagramSocket(12346, InetAddress.getLocalHost()) ;
			byte[] send = new byte[256];
			byte[]receive = new byte[256]; 
			
			DatagramPacket dpr = new DatagramPacket (receive, receive.length);
			while (true) {
				ds.receive(dpr);
				String message = new String (receive, 0, dpr.getLength(), "UTF-8");				
								
				send = new String("devesh").getBytes("UTF-8");
				DatagramPacket dps = new DatagramPacket(send,send.length, dpr.getAddress(), dpr.getPort());								
				ds.send(dps);
				
				System.out.println("S : got " + message + "sent devesh");
			}
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
