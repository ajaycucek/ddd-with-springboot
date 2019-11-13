package ddd.logic.snackMachine;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
@Entity
public class SlotDto {
	@Id
	@GeneratedValue
	private long id;
	private int quantity;
	private float price;
	@OneToOne(cascade = CascadeType.ALL)
	private SnackDto snackDto;
	private int position;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public SnackDto getSnackDto() {
		return snackDto;
	}
	public void setSnackDto(SnackDto snackDto) {
		this.snackDto = snackDto;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public Slot convertToSlot() {
		Slot slot = new Slot();
		slot.setId(id);
		slot.setPosition(position);
		slot.setSnackPile(new SnackPile(snackDto.convertToSnack(), quantity, price));
		return slot;
	}
}