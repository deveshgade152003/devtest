package test1;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class CSMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*Server s = new Server ();
		Client c = new Client ();
		
		s.start();
		c.start();*/
		/*try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			String test = "sdkfbaljkflskdjfmsakhlkj asmkdfbsdk fkasljbv lxv " +
					"lkjdvka ljkvkdlffvbjkbdldbkdlx cvjkxlvkx ckbbbbbbbsdfjgndgjsnlsdnvkjvnxlknckbnxcvbkjnbclxvkbjnkxjndlkngffffbxlbkjvn";
			byte[] result = test.getBytes();
			byte[] hash = md.digest(result);
			System.out.println(hash.length);
			System.out.println(hash.toString());
			InetAddress addr = InetAddress.getByName("www.google.com");
			System.out.println("Reachable : " + addr.isReachable(1000));
			System.out.println(addr);
			addr = InetAddress.getLocalHost();
			System.out.println(addr.getHostAddress());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Message m = new Message ();
		Socket sock;
		try {
			sock = new Socket ("localhost",20001);
			OutputStream os = sock.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream (os);
			oos.write(m.getBytes());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

}
class Client extends Thread {
	Socket sock;
	public Client () {
		try {
			
			sock = new Socket("localhost", 20043);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void run () {
		OutputStream os;
		int BLOCKSIZE = 4096;
		try {
			os = sock.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			dos.write(23);
			dos.flush();
			MessageDigest md = MessageDigest.getInstance("MD5");
			
			//FileReader fr = new FileReader("F:\\test\\Sample.txt");
			InputStream fis = new FileInputStream("F:\\test\\Sample.txt");
			byte[] temp = new byte[BLOCKSIZE];
			byte[] hash = null;
			while(fis.read(temp) != -1) {
				
				dos.write(temp);
				//hash = md.digest(temp);
				System.out.println("Sending " + temp.toString());
				dos.flush();
			}
			dos.write(hash);
			dos.flush();
			//BufferedReader br = new BufferedReader()
			//while (fis.read())
			
			sock.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
/**
 * @author Devesh Gade
 * @param none
 * */
class Server extends Thread {
	ServerSocket ss;
	public Server () {
		try {
			ss = new ServerSocket (20043);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void run () {
		int BLOCKSIZE = 4096;
		while (true) {
			Socket sock;
			try {
				sock = ss.accept();
				InputStream is = sock.getInputStream();
				DataInputStream dis = new DataInputStream(is);
				System.out.println("Got from client : " + dis.read());
				byte[] buffer = new byte[BLOCKSIZE];
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] hash = null;
				
				while(dis.read(buffer) != -1) {
					//hash = md.digest(buffer);		
					System.out.println("Got " + buffer.toString());
				}
				System.out.println(hash);
				if (sock.isClosed() == true) {					
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
class Message implements Serializable {
	String header;
	String body;
	String checksum;
	public void setHeader () {
		
	}
	public String getHeader () {
		return header;
	}
	/**
	 * @param b defines the body of the message object 
	 * */
	public void setBody (String b) {
		
	}
	public String getBody () {
		return body;
	}
	public void setChecksum (String c) {
		
	}
	public String getCheckSum () {
		return checksum;
	}
	private String calculateChecksum () {
		String chk = null;
		return chk;
	}
	public byte[] getBytes () {
		String message = header + body + checksum;				 
		return message.getBytes(); 
	}
	private void writeObject (ObjectOutputStream out) {
		try {
			out.writeDouble(header.length());
			out.writeChars(header);
			out.writeDouble(body.length());
			out.writeChars(body);
			out.writeDouble(checksum.length());
			out.writeChars(checksum);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void readObject (ObjectInputStream in) {
		try {
			while (in.available() != 0) {
				int byteCounter = 0;
				int BLOCKSIZE = 4096;
				byte[] buffer = new byte[BLOCKSIZE];
				
				//
				double headLength = in.readDouble();
				in.read(buffer, 0, (int)headLength);
				header = buffer.toString();
				
				//
				double bodyLength = in.readDouble();
				in.read(buffer, 0, (int)bodyLength);
				body = buffer.toString();
				
				//
				double checksumLength = in.readDouble();				
				in.read(buffer, 0, (int)checksumLength);
				checksum = buffer.toString();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
