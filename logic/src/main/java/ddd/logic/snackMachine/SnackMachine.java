package ddd.logic.snackMachine;
import static ddd.logic.sharedKernel.Money.None;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ddd.logic.common.AggregateRoot;
import ddd.logic.sharedKernel.Money;

public class SnackMachine extends AggregateRoot {
	private Money moneyInside; 
	private float moneyInTransaction; 
	private List<Slot> slots;
	
	public SnackMachine() {
		moneyInside = None; 
		moneyInTransaction = 0; 
		slots = new ArrayList<>();
		slots.add(new Slot(this, 1));
		slots.add(new Slot(this, 2));
		slots.add(new Slot(this, 3));
	}
	
	public Money unloadMoney() {
		if (moneyInTransaction > 0)
			throw new IllegalStateException();

		Money money = moneyInside;
		moneyInside = Money.None;
		return money;
	}
	
	public void insertMoney(Money money) {
    	Money[] coinsAndNotes = { Money.Cent, Money.TenCent, Money.Quarter, Money.Dollar, Money.FiveDollar,
				Money.TwentyDollar };

		if (!Arrays.asList(coinsAndNotes).contains(money))
			throw new IllegalStateException();
		
    	moneyInTransaction = moneyInTransaction + money.getAmount();
    	moneyInside = moneyInside.add(money);
    }
	
	public void buySnack(int position) {
		Slot slot = getSlot(position);
		if(slot.getSnackPile().getPrice() > moneyInTransaction) {
			throw new IllegalStateException();
		}
		slot.setSnackPile(slot.getSnackPile().subtractOne());
		Money change = moneyInside.allocate(moneyInTransaction - slot.getSnackPile().getPrice());
		if(change.getAmount()<moneyInTransaction - slot.getSnackPile().getPrice()) {
			throw new IllegalStateException();
		}
		moneyInside = moneyInside.substract(change);
    	moneyInTransaction = 0; 
    }
	
    public void returnMoney() {
		Money moneyToReturn = moneyInside.allocate(moneyInTransaction);
		moneyInside = moneyInside.substract(moneyToReturn);
		moneyInTransaction = 0;
	}
    
	public void loadMoney(Money money) {
		moneyInside = moneyInside.add(money);
	}
	public Slot getSlot(int position) {
		return slots.stream().filter(x -> x.getPosition() == position).findAny().orElse(null);
	}
	
	public SnackPile getSnackPile(int position) {
		return getSlot(position).getSnackPile();
	}
	
	public void loadSnacks(int position, SnackPile snackPile) {
		Slot slot = slots.stream().filter(x -> x.getPosition() == position).findAny().orElse(null);
		if(slot != null) {
			slot.setSnackPile(snackPile);
		}
	}
	public SnackMachineDto convertToSnackMachineDto() {
		SnackMachineDto snackMachineDto = new SnackMachineDto();
		snackMachineDto.setId(id);
		snackMachineDto.setMoneyInTransaction(moneyInTransaction);
		
		List<SlotDto> slotDtoList = new ArrayList<>();
		for(Slot slot : slots) slotDtoList.add(slot.convertToSlotDto());
		snackMachineDto.setSlotDtoList(slotDtoList);
		
		snackMachineDto.setOneCentCount(moneyInside.getOneCentCount());
		snackMachineDto.setTenCentCount(moneyInside.getTenCentCount());
		snackMachineDto.setQuarterCount(moneyInside.getQuarterCount());
		snackMachineDto.setOneDollarCount(moneyInside.getOneDollarCount());
		snackMachineDto.setFiveDollarCount(moneyInside.getFiveDollarCount());
		snackMachineDto.setTwentyDollarCount(moneyInside.getTwentyDollarCount());
		return snackMachineDto;
	}
	
	public void setMoneyInTransaction(float moneyInTransaction) {
		this.moneyInTransaction = moneyInTransaction;
	}

	public float getMoneyInTransaction() {
		return moneyInTransaction;
	}
	
	public void setMoneyInside(Money moneyInside) {
		this.moneyInside = moneyInside;
	}

	public Money getMoneyInside() {
		return moneyInside;
	}

	public List<Slot> getSlots() {
		return slots;
	}

	public void setSlots(List<Slot> slots) {
		this.slots = slots;
	}
}