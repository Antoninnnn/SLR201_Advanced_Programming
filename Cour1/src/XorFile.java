import java.io.*;



public class XorFile{
	
	
	private String inputFileName = null;
	private String outputFileName = null;
	private String keyFileName = null;
	
	
	
	public String getInputFileName() {
		return inputFileName;
	}



	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}



	public String getOutputFileName() {
		return outputFileName;
	}



	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}



	public String getKeyFileName() {
		return keyFileName;
	}



	public void setKeyFileName(String keyFileName) {
		this.keyFileName = keyFileName;
	}



	public XorFile() {
		super();
		// TODO Auto-generated constructor stub
	}


	public boolean xorFile(String inputFileName, String outputFileName, String keyFileName) {
		FileInputStream fis_i = null;
		BufferedInputStream bis_i = null;
		
		FileInputStream fis_k = null;
		BufferedInputStream bis_k = null;
		
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		
		try {
			fis_i = new FileInputStream(inputFileName);
			bis_i = new BufferedInputStream(fis_i);
		
			
			fis_k = new FileInputStream(keyFileName);
			bis_k = new BufferedInputStream(fis_k);
			
			fos = new FileOutputStream(outputFileName);
			bos = new BufferedOutputStream(fos);
		

			
			int b0 = bis_i.read();
			int b1 = bis_k.read();
			while(b0 !=-1) {
				while(b1 !=-1) {
					XorByte gs = new XorByte();
					int s = gs.xor(b0,b1);
					bos.write(s);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		finally {
			try  {fis_i.close();} catch (Exception e) {return false;};
			try  {bis_i.close();} catch (Exception e) {return false;};
			
			try  {fis_k.close();} catch (Exception e) {return false;};
			try  {bis_k.close();} catch (Exception e) {return false;};
			
			try  {fos.close();} catch (Exception e) {return false;};
			try  {bos.close();} catch (Exception e) {return false;};
			
			return true;
			
		}
		
	}
	
	
	
}

