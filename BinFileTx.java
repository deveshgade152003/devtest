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

public class BinFileTx {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*try {
			File f = new File("F:\\test\\Sample.txt");
			long flength = f.length();
			FileInputStream fis = new FileInputStream ("F:\\test\\Sample.txt");
			int chunksize = 4096;
			int numchunks = 4;
			int loopnumchunks = (int) ( flength / numchunks ); 
			byte[] fb = new byte[loopnumchunks];
			int i = 1;
			while (i <= 4) {
				FileOutputStream fos = new FileOutputStream("F:\\test\\Sample_"+i+".txt");
				int read = 0;
				while ((read = fis.read(fb, 0, loopnumchunks)) > 0) {
					fos.write(fb, 0, loopnumchunks);
					break;
				}
				i++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		SServer s = new SServer ();
		CClient c = new CClient ();
		s.start();
		c.start();
	}

}
class CClient extends Thread {
	
	public static int CHUNK_SIZE;
	public static int NUM_CHUNKS;
	public static int chunksize;
	public CClient () {
		CHUNK_SIZE = 4092;
		NUM_CHUNKS = 4;
	}
	public void run () {
		try {
			File f = new File ("F:\\test\\Sample.txt");
			long fileLength = f.length();
			InputStream is = new FileInputStream("F:\\test\\Sample.txt");
			chunksize = (int) (fileLength/NUM_CHUNKS);
			System.out.println("size + chunksize: " + fileLength + " " + chunksize );
			byte[] fb = new byte [chunksize]; 
			Socket s = new Socket ("localhost", 20001);
			OutputStream os = s.getOutputStream();
			s.setSoTimeout(60000);
			int i = 0;
			int read = 1;
			//while (i < NUM_CHUNKS) {
				while ((read = is.read(fb)) > 0 ) {
					os.write(fb, 0, read);
					System.out.println("hi");
				}
				i++;
			//}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
class SServer extends Thread {
	public void run () {
		int read = 0;
		try {
			ServerSocket ss = new ServerSocket (20001, 2);
			Socket s = ss.accept();
			InputStream is = s.getInputStream();
			s.setSoTimeout(120000);
			
			int i = 1;
			
			byte[] fb = new byte[CClient.chunksize];
			int l = 0;
			
			while (i < CClient.NUM_CHUNKS) {
				FileOutputStream fos1 = new FileOutputStream ("F:\\test\\chunk"+Integer.toString(i));
				try {
					//if (is.markSupported()) System.out.println("true");
					//is.reset();
				while ((read += is.read(fb, read, CClient.chunksize - read)) > 0 ) {					
					if( read < CClient.chunksize) System.out.println(l++);
				}
				} catch (ArrayIndexOutOfBoundsException e) {
					int diff = CClient.chunksize - read;
					is.read(fb, read, diff);
					fos1.write(fb, 0, read);
					is.mark(read);
				} finally {
					i++;
				fos1.close();
				}
			
			} 
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		} 
		
	}
}

