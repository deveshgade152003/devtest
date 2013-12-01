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

public class ClientMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CL c = new CL ();
		SE s = new SE ();
		s.start();
		c.start();
	}

}
class CL extends Thread {
	public void run () {
		
		try {
			Socket s = new Socket ("localhost", 14001);
			byte[] buffer = new byte[4096];
			FileOutputStream fos = new FileOutputStream (new File ("F:\\test\\dup.txt"));
			InputStream is = s.getInputStream();
			int bytesread = 0;
			while ((bytesread = is.read(buffer)) != -1) {
				fos.write(buffer, 0, bytesread);
			}
			fos.close();
			is.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
class SE extends Thread {
	public void run () {
		try {
			ServerSocket serv = new ServerSocket(14001);
			Socket sock = serv.accept();
			byte[] buffer = new byte[4096];
			FileInputStream fis = new FileInputStream(new File("F:\\test\\Sample.txt"));
			int bytesread = 0;
			OutputStream os = sock.getOutputStream();			
			while ((bytesread = fis.read(buffer)) != -1) {
				os.write(buffer);
			}
			fis.close();
			os.close();
			System.out.println("Done writing file");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}