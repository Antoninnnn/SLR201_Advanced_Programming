
public class Tap extends Thread {
	private final Bathtub btub;
	private final int vitess;
	
	public Tap(Bathtub bt, int i) {
		super();
		this.btub = bt;
		this.vitess = i;
	}

	public  void run() {
		while (true) {
			//  COULD ALSO INTRODUCE A BOOLEAN action
			try {
				synchronized(this.btub){
					if(this.btub.getQuantity()>(this.btub.getCapacity()-this.vitess)) {
						btub.wait();
					} 
					else {
						this.btub.add(this.vitess);
						btub.notifyAll();
						//try {Thread.sleep(300);} catch(Exception e) {}
					}
				}
			}catch (Exception e) { e.printStackTrace() ;}
			try {Thread.sleep(300);} catch(Exception e) {}
		}
		
	}
		
		

}
