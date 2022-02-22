package Xorbyte;

public class Xorbyte{
	public int xor(int b1, int b2) {
		return (int)(0xFF & (b1 ^ b2));
	}
}