package test1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class TimeoutRetry {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client1 c = new Client1 ();
		Server1 s = new Server1 ();
		s.start();
		c.start();
	}

}
class Client1 extends Thread {
	public void run () {
		Socket sock = null;
		byte[] buf = null;
		for (int i = 0; i < 4; i++) {
		try {
			
			
			if (i == 0) {
			sock = new Socket ("localhost", 14000);
			sock.setSoTimeout(20000);			
			buf = new byte[256];
			}
				InputStream is = sock.getInputStream();
				int cnt = is.read(buf);
				System.out.println("Read " + new String(buf,0,cnt,"UTF-8"));
				throw new IOException();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Timeout : " + (i+1));
			continue;
		}
		}
	}
}
class Server1 extends Thread {
	public void run () {
		try {
			ServerSocket serv = new ServerSocket (14000);
			Socket sock = serv.accept();
			System.out.println("Server sleeping");
			sleep(10000);
			OutputStream os = sock.getOutputStream();
			os.write(new String("hello devesh").getBytes());		
			System.out.println("Writing 200");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}