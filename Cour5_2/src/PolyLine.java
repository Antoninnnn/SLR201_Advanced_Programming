import java.io.*;
import java.util.ArrayList;

public class PolyLine {
	@Override
	public String toString() {
		return "PolyLine [pL=" + pL + "]";
	}

	ArrayList<Point> pL;
	
	// constructer of the class PolyLine
	public PolyLine(ArrayList<Point> pL) {
		super();
		this.pL = pL;
	}
	
	public void saveInFile(String fileName) {
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(fileName);
			bos = new BufferedOutputStream(fos);
			oos = new ObjectOutputStream(bos);
			oos.writeObject(pL);
			//for (int i =0;i<pL.size();i++) {
				//oos.writeObject(this.pL[i]);}
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		finally {
			try  {fos.close();} catch (Exception e) {};
			try  {bos.close();} catch (Exception e) {};
			try  {oos.close();} catch (Exception e) {};
		}
	}
	

	
	public static ArrayList<Point> recoverFromFile(String fileName) {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ObjectInputStream ois = null;
		ArrayList<Point> data = null;
		try {
			fis = new FileInputStream(fileName);
			bis = new BufferedInputStream(fis);
			ois = new ObjectInputStream(bis);
			data= (ArrayList<Point>)ois.readObject();

			//for (int i =0;i<pL.size();i++) {
				//oos.writeObject(this.pL[i]);}
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		finally {
			try  {fis.close();} catch (Exception e) {};
			try  {bis.close();} catch (Exception e) {};
			try  {ois.close();} catch (Exception e) {};
		}
		return data;
		
	}
}
