package ddd.logic.management;
import static ddd.logic.sharedKernel.Money.None;
import ddd.logic.atm.Atm;
import ddd.logic.common.AggregateRoot;
import ddd.logic.sharedKernel.Money;
import ddd.logic.snackMachine.SnackMachine;
public class HeadOffice extends AggregateRoot {
	private float balance;
	private Money cash = None;
	
	public void unloadCashFromSnackMachine(SnackMachine snackMachine){
        Money money = snackMachine.unloadMoney();
        cash = cash.add(money);
    }

    public void loadCashToAtm(Atm atm){
        atm.loadMoney(cash);
        cash = Money.None;
    }
    
	public void changeBalance(float delta){
        balance += delta;
    }
	
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public Money getCash() {
		return cash;
	}
	public void setCash(Money cash) {
		this.cash = cash;
	}

	public HeadOfficeDto convertToHeadOfficeDto() {
		HeadOfficeDto headOfficeDto = new HeadOfficeDto();
		headOfficeDto.setId(id);
		headOfficeDto.setBalance(balance);
		headOfficeDto.setOneCentCount(cash.getOneCentCount());
		headOfficeDto.setTenCentCount(cash.getTenCentCount());
		headOfficeDto.setQuarterCount(cash.getQuarterCount());
		headOfficeDto.setOneDollarCount(cash.getOneDollarCount());
		headOfficeDto.setFiveDollarCount(cash.getFiveDollarCount());
		headOfficeDto.setTwentyDollarCount(cash.getTwentyDollarCount());
		return headOfficeDto;
	}
}