
public class Leak extends Thread {
	private final Bathtub btub;
	private final int vitess;
	
	public Leak(Bathtub bt, int i) {
		super();
		this.btub = bt;
		this.vitess = i;
	}

	public  void run() {
		while (true) {
			try {
				synchronized(this.btub) {
					if(this.btub.getQuantity()<this.vitess) {
						btub.wait();
					} 
					else {
						this.btub.pull(this.vitess);
						btub.notifyAll();
						
					}
				}
			}catch (Exception e) { e.printStackTrace() ;}
			try {Thread.sleep(300);} catch(Exception e) {}
		}
		
		
		

		
	}

}
