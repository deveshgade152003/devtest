package test1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

public class BinTx {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			FileOutputStream fos = new FileOutputStream (new File ("F:\\test\\combine.txt"));
			
			ZServer ser1 = new ZServer (30001, "F:\\test\\Sample_1.txt");
			ZServer ser2 = new ZServer (30001, "F:\\test\\Sample_2.txt");
			ZServer ser3 = new ZServer (30001, "F:\\test\\Sample_3.txt");
			ZServer ser4 = new ZServer (30001, "F:\\test\\Sample_4.txt");
			ser1.start();
			ser2.start();
			ser3.start();
			ser4.start();
			
			ArrayList<ZClient> client = new ArrayList<ZClient>(10); 
			
			ServerSocket ss = new ServerSocket (30001);
			while (true) {				
				Socket s = ss.accept();
				ZClient c = new ZClient (s, fos);
				System.out.println("Starting : " + c.getId());
				c.start();
				client.add(c);				
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
}
class ZClient extends Thread {
	Socket socket;
	byte[] buffer;
	FileOutputStream fos;
	public ZClient (Socket s, FileOutputStream f) {
		socket = s;
		fos = f;
	}
	public void run () {
		monitor ();
	}
	public synchronized void monitor () {
		byte[] temp = new byte[1024];
		
		int read = 0;
		int offset = 0;
		try {
			System.out.println("ID : "+this.getId());
			File f = new File ("F:\\test\\Sample_4.txt");
			InputStream is = socket.getInputStream();			
			buffer = new byte[(int)f.length()];
			
			while ((read += is.read(buffer, offset, buffer.length-offset)) < buffer.length) {
				fos.write(buffer, offset, read - offset);
				System.out.println("Thread "+this.getId()+" read : "+read);
				offset = read;
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Failed read + buffer : " + read + " " + buffer.length);
		}
	}
}
class ZServer extends Thread {
	int port;
	File fileName;
	public ZServer (int p, String f) {
		port = p;
		fileName = new File(f);
	}
	public void run () {
		try {
			FileInputStream fis = new FileInputStream (fileName);
			byte[] buffer = new byte[(int)fileName.length()];
			
			Socket s = new Socket ("localhost", port);
			OutputStream os = s.getOutputStream();
			int read = 0;
			while ((read = fis.read(buffer, 0, buffer.length)) != -1) {
				os.write(buffer, 0, buffer.length);
			}
			System.out.println("Done with " + this.getId());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}