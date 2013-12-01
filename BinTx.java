package test1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class BinTx {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			ZServer ser1 = new ZServer (20001, "F:\\test\\Sample_1.txt");
			ZServer ser2 = new ZServer (20001, "F:\\test\\Sample_2.txt");
			ZServer ser3 = new ZServer (20001, "F:\\test\\Sample_3.txt");
			ZServer ser4 = new ZServer (20001, "F:\\test\\Sample_4.txt");
			ser1.start();
			ser2.start();
			ser3.start();
			ser4.start();
			
			Socket s = new Socket ("localhost", 30001);
			
			InputStream is = s.getInputStream();
		
			FileInputStream fis1 = new FileInputStream(f1);
			FileInputStream fis2 = new FileInputStream(f2);
			FileInputStream fis3 = new FileInputStream(f3);
			FileInputStream fis4 = new FileInputStream(f4);
			
			byte[] buffer1 = new byte[(int)f1.length()];
			byte[] buffer2 = new byte[(int)f2.length()];
			byte[] buffer3 = new byte[(int)f3.length()];
			byte[] buffer4 = new byte[(int)f4.length()];
			
			
			
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
	public void run () {
		
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
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}