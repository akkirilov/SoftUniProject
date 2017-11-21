package p03_GenericSwapMethodString;

public class Box <T> {

	private T item;

	public Box(T item) {
		this.item = item;
	}
	
	@Override
	public String toString() {
		return String.format("%s: %s", item.getClass().getName(), item);
	}
	
}
