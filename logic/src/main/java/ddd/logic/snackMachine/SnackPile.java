package ddd.logic.snackMachine;

import ddd.logic.common.ValueObject;

public class SnackPile extends ValueObject<SnackPile> {
	public static SnackPile Empty = new SnackPile(Snack.None, 0, 0f);
	private Snack snack;
	private int quantity;
	private float price;

	private SnackPile() {
	}

	public SnackPile(Snack snack, int quantity, float price) {
		if (quantity < 0)
			throw new IllegalStateException();
		if (price < 0)
			throw new IllegalStateException();
		
		this.snack = snack;
		this.quantity = quantity;
		this.price = price;
	}

	public SnackPile subtractOne() {
		return new SnackPile(snack, getQuantity() - 1, getPrice());
	}

	@Override
	protected boolean equalsCore(SnackPile other) {
		return this.snack == other.snack && this.getQuantity() == other.getQuantity()
				&& this.getPrice() == other.getPrice();
	}

	@Override
	protected int getHashCodeCore() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Float.floatToIntBits(price);
		result = prime * result + quantity;
		result = prime * result + ((snack == null) ? 0 : snack.hashCode());
		return result;
	}

	public int getQuantity() {
		return quantity;
	}

	public float getPrice() {
		return price;
	}

	public Snack getSnack() {
		return snack;
	}
}