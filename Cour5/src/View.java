import java.io.PrintStream;

public class View extends Thread {
	private Bathtub btub;

	
	public View(Bathtub bt) {
		super();
		this.btub = bt;
	}

	public  void run() {
		//synchronized(this.btub) {
			try {
				PrintStream out = System.out;
				while (true) {
						out.println(this.btub);
						//notifyAll();
						try {Thread.sleep(100);} catch(Exception e) {}
					}
			
			}catch (Exception e) { e.printStackTrace() ;}
		}
	//}
}
