
public class Test {

	public static void main(String[] args) {
		Bathtub bathhub = new Bathtub(100);
		new Leak(bathhub,3).start();
		new Tap(bathhub,5).start();
		new View(bathhub).start();
		
	}

}
