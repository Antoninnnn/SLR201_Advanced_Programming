//import java.io.*;

public class Test {
	public static void  main(String[] args) {
		int count = 10;
		//XorFile XF = new XorFile();
		
		GenerateKey GK = new GenerateKey();
		GK.generateKey("key.txt", count);
	}

}
