package test1;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HeartbeatExtented {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Serv s = new Serv ();
		Clie c1 = new Clie (12310);
		Clie c2 = new Clie (12311);
		Clie c3 = new Clie (12312);
		Clie c4 = new Clie (12313);
		s.start();
		c1.start();
		c2.start();
		c3.start();
		c4.start();
		try {
			System.out.println("main sleeping for 10 secs");
			Thread.currentThread().sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c4.interrupt();
		System.out.println("main ended");
	}

}
class Mapping extends Thread {
	Map <Integer, Long> map;
	public Mapping () {
		map = new HashMap <Integer, Long> ();
	}
	public synchronized void saveTime (int port, long time) {
		long t;
		if ( map.get(port) != null ) {
			t = map.get(port);
			System.out.println("Saving port + time: " + port + " " + (time - t));
		}
		map.put(port, time);
	}
	public void run () {
		//monitor ();
		while (true) {
		long difference;
		for (Map.Entry<Integer, Long> entry : map.entrySet()) {			
			synchronized (entry) {
			difference = System.currentTimeMillis() - entry.getValue();			
			if ((difference / 1000) > 5) {
				System.out.println("Port " + entry.getKey() + " dead");
			}
			}
		}
		try {
			sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	public synchronized void monitor () {
		
	}
}
class Serv extends Thread {
	Mapping m;
	public Serv () {
		m = new Mapping ();
		m.start();
	}
	public void run () {		
		int numRetries = 4;
		//for (int i = 0; i < numRetries; i++) {
			try {
				DatagramSocket ds = new DatagramSocket(12346,
						InetAddress.getLocalHost());				
				byte[] receive = new byte[256];
				DatagramPacket dpr = new DatagramPacket(receive, receive.length);
				while (!Thread.currentThread().isInterrupted()) {
					ds.receive(dpr);
					m.saveTime (dpr.getPort(), System.currentTimeMillis());
					String message = new String(receive, 0, dpr.getLength(),
							"UTF-8");
					System.out.println("S : got " + message);
				}
				m.join();

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
				//System.out.println("Retrying ");
				//continue;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		//}
	}
}
class Clie extends Thread {
	int port;
	public Clie (int p) {
		port = p;
	}
	public void run () {
		DatagramSocket ds;
		try {
			ds = new DatagramSocket(port, InetAddress.getLocalHost());
			byte[] send = new byte[256];
			while (true) {
				String mess = "from " + Integer.toString(port);
				send = mess.getBytes("UTF-8");
				DatagramPacket dps = new DatagramPacket(send,send.length, InetAddress.getLocalHost(), 12346);								
				ds.send(dps);
				
				sleep(5000);
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
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
class HostMap {
	HashMap<Host, HostDetails> map;
	public HostMap () {
		map = null;
	}
	public HashMap getInstance () {
		if (map == null) {
			map = new HashMap <Host, HostDetails> ();
			return map;
		}
		return map;
	}
	public void Add () {
		
	}
	public boolean isAlive (long currTime) {
		return true;//else return false change
	}
}
class HostDetails {
	long time;
	public void setTime () {
		
	}
	public long  getTime () {
		return time; 
	}
}
class Host {
	InetAddress ipAddress;
	int port;
	public Host (InetAddress ia, int p) {
		ipAddress = ia;
		port = p;
	}
	public void setIPAddress (InetAddress ia) {
		ipAddress = ia;
	}
	public void setPort (int p) {
		port = p;
	}
	public InetAddress getIPAddress () {
		return ipAddress;
	}
	public int getPort () {
		return port;
	}
}