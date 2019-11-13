package ddd.logic.atm;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import org.springframework.context.ApplicationEvent;
import com.fasterxml.jackson.annotation.JsonIgnore;
import ddd.logic.sharedKernel.Money;

@Entity
public class AtmDto {
	@Id
	@GeneratedValue
	private long id;
	private float moneyCharged;
	private int oneCentCount;
	private int tenCentCount;
	private int quarterCount;
	private int oneDollarCount;
	private int fiveDollarCount;
	private int twentyDollarCount;
	@Transient
	private float amount ;
	@Transient
	@JsonIgnore
	private List<ApplicationEvent> domainEvents;
	public void clearEvents() {
		domainEvents.clear();
	}
	public float getAmount() {
		return  amount;
	}
	@PostLoad
	public void setAmount() {
		amount = oneCentCount * 0.01f + tenCentCount * 0.10f + quarterCount * 0.25f + oneDollarCount * 1f
				+ fiveDollarCount * 5f + twentyDollarCount * 20f;
	}
	public List<ApplicationEvent> getDomainEvents() {
		return domainEvents;
	}
	public void setDomainEvents(List<ApplicationEvent> domainEvents) {
		this.domainEvents = domainEvents;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public float getMoneyCharged() {
		return moneyCharged;
	}
	public void setMoneyCharged(float moneyCharged) {
		this.moneyCharged = moneyCharged;
	}
	public int getOneCentCount() {
		return oneCentCount;
	}
	public void setOneCentCount(int oneCentCount) {
		this.oneCentCount = oneCentCount;
	}
	public int getTenCentCount() {
		return tenCentCount;
	}
	public void setTenCentCount(int tenCentCount) {
		this.tenCentCount = tenCentCount;
	}
	public int getQuarterCount() {
		return quarterCount;
	}
	public void setQuarterCount(int quarterCount) {
		this.quarterCount = quarterCount;
	}
	public int getOneDollarCount() {
		return oneDollarCount;
	}
	public void setOneDollarCount(int oneDollarCount) {
		this.oneDollarCount = oneDollarCount;
	}
	public int getFiveDollarCount() {
		return fiveDollarCount;
	}
	public void setFiveDollarCount(int fiveDollarCount) {
		this.fiveDollarCount = fiveDollarCount;
	}
	public int getTwentyDollarCount() {
		return twentyDollarCount;
	}
	public void setTwentyDollarCount(int twentyDollarCount) {
		this.twentyDollarCount = twentyDollarCount;
	}
	public Atm convertToAtm() {
		Atm atm = new Atm();
		atm.setId(id);
		atm.setMoneyCharged(moneyCharged);
		atm.setMoneyInside(new Money(oneCentCount,tenCentCount,quarterCount,
				oneDollarCount,fiveDollarCount,twentyDollarCount));
		return atm;
	}
}