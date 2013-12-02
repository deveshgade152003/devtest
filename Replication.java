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
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Replication {
	public static Executor exec;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		exec = Executors.newFixedThreadPool(3);
		
		QServer s1 = new QServer (32001);
		QServer s2 = new QServer (32002);
		QServer s3 = new QServer (32003);
		QServer s4 = new QServer (32004);
		s1.start();
		s2.start();
		s3.start();
		s4.start();
		
		QClient c1 = new QClient (32001, "F:\\test\\Sample_1.txt");
		QClient c2 = new QClient (32002, "F:\\test\\Sample_2.txt");
		QClient c3 = new QClient (32003, "F:\\test\\Sample_3.txt");
		QClient c4 = new QClient (32004, "F:\\test\\Sample_4.txt");
		exec.execute(c1);
		exec.execute(c2);
		exec.execute(c3);
		exec.execute(c4);
		
	}

}
class QClient extends Thread implements Runnable{
	int port;
	String fileName;
	public QClient (int p, String f) {
		port = p;
		fileName = f;
	}
	public void run () {
		try {
			Socket s = new Socket ("localhost", port);
			File f = new File (fileName);
			System.out.println("File length "+f.length());
			FileInputStream fis = new FileInputStream (new File (fileName));
			byte[] buffer = new byte[2048];
			int read = 0;
			int offset = 0;
			OutputStream os = s.getOutputStream();
			while ((read = fis.read(buffer)) != -1) {
				os.write(buffer, offset, read - offset);
				offset = 0;
			}
			os.close();
			fis.close();
			System.out.println("Done with " + fileName + " " +this.getId() );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
class QServer extends Thread {
	int port;
	
	public QServer (int p) {
		port = p;		
	}
	public void run () {
		try {
			ServerSocket ss = new ServerSocket (port);
			Socket s = ss.accept();
			InputStream is = s.getInputStream();
			FileOutputStream fos = new FileOutputStream (new File ("F:\\test\\chunk" + this.getId()));
			byte[] buffer = new byte[1024];
			int read = 0;
			int offset = 0;
			int total = 0;
			while ((read = is.read(buffer)) > 0) {
				fos.write(buffer, offset, read - offset);
				total = total + read;
				offset = 0;
				System.out.println("total + read: "+total + " "+read);
			}
			is.close();
			fos.close();
			System.out.println("Got chunk" + this.getId());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}