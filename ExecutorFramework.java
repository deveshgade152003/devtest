package test1;
import java.util.concurrent.*;
public class ExecutorFramework {
	public static Executor exec; 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		exec = Executors.newFixedThreadPool(4);
		Task t1 = new Task ();
		Task t2 = new Task ();
		Task t3 = new Task ();
		Task t4 = new Task ();
		Task t5 = new Task ();
		Task t6 = new Task ();
		Task t7 = new Task ();
		Task t8 = new Task ();
		Task t9 = new Task ();
		Task t10 = new Task ();
		exec.execute(t1);
		exec.execute(t2);
		exec.execute(t3);
		exec.execute(t4);
		exec.execute(t5);
		exec.execute(t6);
		exec.execute(t7);
		exec.execute(t8);
		exec.execute(t9);
		exec.execute(t10);
		
	}

}
class Task extends Thread implements Runnable {
	public void run () {
		call();
	}
	public void call () {
		//synchronized (Replicator.exec) {
		System.out.println("ID : " + this.getId() + "performing task");		
		try {
			sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//}
	}
}
