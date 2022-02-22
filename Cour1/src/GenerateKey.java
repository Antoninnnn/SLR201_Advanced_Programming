import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

public class GenerateKey {

	public void generateKey(String fileName, int count) {
		GenerateByte GB = new GenerateByte();
		FileOutputStream fos_k = null;
		BufferedOutputStream bos_k = null;
		
		try {
			fos_k = new FileOutputStream(fileName);
			bos_k = new BufferedOutputStream(fos_k);
			
			
			for (int i=0;i<count;i++) {
				int x= GB.generateByte();
				bos_k.write(x);
				
			}
			bos_k.flush();
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		finally {
			try  {fos_k.close();} catch (Exception e) {};
			try  {bos_k.close();} catch (Exception e) {};
		}
		
		
	}

}
