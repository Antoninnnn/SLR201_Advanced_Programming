
public class Bathtub {

	@Override
	public String toString() {
		return "Bathtub [capacity=" + capacity + ", quantity=" + quantity + "]";
	}

	private final int capacity;
	public int getCapacity() {
		return capacity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	private int quantity;
	
	public int getQuantity() {
		return quantity;
	}

	public Bathtub(int capacity) {
		super();
		this.capacity = capacity;
		this.quantity =0;
		
	}
	
	public final void add(int quantity) {
		this.quantity += quantity;
	}
	
	public final void pull(int quantity) {
		this.quantity -= quantity;
	}
	
}
