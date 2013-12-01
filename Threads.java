package test1;

public class Threads {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		App2 second = new App2 ();
		App1 first = new App1 (second);
		
		App3 third = new App3 ();
		first.start();
		second.start();
		third.start();
		try {
			first.join();
			second.join();
			third.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
class App1 extends Thread {
	boolean comm;
	App2 secondApp;
	public App1 () {
		comm = false;
	}
	public App1 (App2 sec) {
		secondApp = sec;
	}
	public void run () {
		try {
			this.sleep(2000);			
			synchronized (secondApp) {
				
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void Method1 () {
		
	}
	public void Method2 () {
		
	}
}
class App2 extends Thread {
	int val;
	public App2 () {
		val = 0;
	}
	public void Method1 () {
		val = 10;
	}
	public void Method2 () {
		
	}	
	public void run() {
		this.Method1();
	}
}
class App3 extends Thread {
	public void Method1 () {
		
	}
	public void Method2 () {
		
	}
	public void run() {
		
	}
}