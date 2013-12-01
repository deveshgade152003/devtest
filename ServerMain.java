package test1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;

public class ServerMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		C client = new C();
		S server = new S();
		server.start();
		client.start();
		Socket s;
		try {
			s = new Socket("localhost",3440);
			SocketChannel sc = s.getChannel();
				
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
class C extends Thread {
	public void run () {
		byte[] buf = new byte[4096];
		try {
			String message = "fsgdgbeqgdgdsdgafgsdfgd";
			OutputStream bais = new ByteArrayOutputStream();
			bais.write(message.getBytes());
			buf = message.getBytes("UTF-8");
			System.out.println("Orig : " + new String(buf, "UTF-8"));
			System.out.println("Plain " +message.getBytes("UTF-8"));
			System.out.println("Plain2 " +buf);
			DatagramSocket ds = new DatagramSocket (15002);
			DatagramPacket dp = new DatagramPacket (buf, buf.length);
			dp.setData(message.getBytes());
			dp.setAddress(InetAddress.getLocalHost());
			dp.setPort(15001);
			ds.send(dp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
class S extends Thread {
	public void run () {
		try {
			DatagramSocket ds = new DatagramSocket(15001);			
			byte[] buffer = new byte[4096];			
			DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
			dp.setAddress(InetAddress.getLocalHost());
			dp.setPort(15002);
			while(true){
				ds.receive(dp);
				buffer = dp.getData();
				System.out.println("Len "+dp.getLength());
				System.out.println("Got "+new String(buffer,0,dp.getLength(), "UTF-8"));
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}