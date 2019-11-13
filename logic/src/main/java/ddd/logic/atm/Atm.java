package ddd.logic.atm;

import static ddd.logic.sharedKernel.Money.None;

import ddd.logic.common.AggregateRoot;
import ddd.logic.sharedKernel.Money;

public class Atm extends AggregateRoot {
	private static float commissionRate = 0.01f;
	private Money moneyInside = None;
	private float moneyCharged;

	public String canTakeMoney(float amount) {
		if (amount <= 0f)
			return "Invalid amount";

		if (moneyInside.getAmount() < amount)
			return "Not enough money";

		if (!moneyInside.canAllocate(amount))
			return "Not enough change";

		return "";
	}
	
	public void takeMoney(float amount) {
		if (canTakeMoney(amount) != "")
			throw new IllegalStateException();
		
		Money output = moneyInside.allocate(amount);
		moneyInside = moneyInside.substract(output);

		float amountWithCommission = caluculateAmountWithCommission(amount);
		moneyCharged += amountWithCommission;
		addDomainEvent(new BalanceChangedEvent(this, amountWithCommission));
	}
	
	public float caluculateAmountWithCommission(float amount) {
		float commission = amount * commissionRate;
		float lessThanCent = commission % 0.01f;
		if (lessThanCent > 0) {
			commission = commission - lessThanCent + 0.01f;
		}
		return amount + commission;
	}

	public void loadMoney(Money money) {
		moneyInside = moneyInside.add(money);
	}

	public Money getMoneyInside() {
		return moneyInside;
	}

	protected void setMoneyInside(Money moneyInside) {
		this.moneyInside = moneyInside;
	}

	public float getMoneyCharged() {
		return moneyCharged;
	}

	protected void setMoneyCharged(float moneyCharged) {
		this.moneyCharged = moneyCharged;
	}

	public AtmDto convertToAtmDto() {
		AtmDto atmDto = new AtmDto();
		atmDto.setId(id);
		atmDto.setMoneyCharged(moneyCharged);
		atmDto.setOneCentCount(moneyInside.getOneCentCount());
		atmDto.setTenCentCount(moneyInside.getTenCentCount());
		atmDto.setQuarterCount(moneyInside.getQuarterCount());
		atmDto.setOneDollarCount(moneyInside.getOneDollarCount());
		atmDto.setFiveDollarCount(moneyInside.getFiveDollarCount());
		atmDto.setTwentyDollarCount(moneyInside.getTwentyDollarCount());
		atmDto.setDomainEvents(getDomainEvents());
		return atmDto;
	}
}