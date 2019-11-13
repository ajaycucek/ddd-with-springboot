package ddd.logic.sharedKernel;

import ddd.logic.common.ValueObject;

public class Money extends ValueObject<Money> {

	public static Money None = new Money(0, 0, 0, 0, 0, 0);
	public static Money Cent = new Money(1, 0, 0, 0, 0, 0);
	public static Money TenCent = new Money(0, 1, 0, 0, 0, 0);
	public static Money Quarter = new Money(0, 0, 1, 0, 0, 0);
	public static Money Dollar = new Money(0, 0, 0, 1, 0, 0);
	public static Money FiveDollar = new Money(0, 0, 0, 0, 1, 0);
	public static Money TwentyDollar = new Money(0, 0, 0, 0, 0, 1);

	private int oneCentCount;
	private int tenCentCount;
	private int quarterCount;
	private int oneDollarCount;
	private int fiveDollarCount;
	private int twentyDollarCount;

	private float amount;
	
	public Money() {}

	 public boolean canAllocate(float amount){
         Money money = allocateCore(amount);
         return money.getAmount() == amount;
     }

     public Money allocate(float amount){
         if (!canAllocate(amount))
             throw new IllegalStateException();

         return allocateCore(amount);
     }

     private Money allocateCore(float amount){
         int twentyDollarCount = Math.min((int)(amount / 20), this.twentyDollarCount);
         amount = amount - twentyDollarCount * 20;

         int fiveDollarCount = Math.min((int)(amount / 5), this.fiveDollarCount);
         amount = amount - fiveDollarCount * 5;

         int oneDollarCount = Math.min((int)amount, this.oneDollarCount);
         amount = amount - oneDollarCount;

         int quarterCount = Math.min((int)(amount / 0.25f), this.quarterCount);
         amount = amount - quarterCount * 0.25f;

         int tenCentCount = Math.min((int)(amount / 0.1f), this.tenCentCount);
         amount = amount - tenCentCount * 0.1f;

         int oneCentCount = Math.min((int)(amount / 0.01f), this.oneCentCount);

         return new Money(
             oneCentCount,
             tenCentCount,
             quarterCount,
             oneDollarCount,
             fiveDollarCount,
             twentyDollarCount);
     }
	
	public int getQuarterCount() {
		return quarterCount;
	}

	public void setQuarterCount(int quarterCount) {
		this.quarterCount = quarterCount;
	}

	public int getOneCentCount() {
		return oneCentCount;
	}

	public int getTenCentCount() {
		return tenCentCount;
	}

	public int getOneDollarCount() {
		return oneDollarCount;
	}

	public int getFiveDollarCount() {
		return fiveDollarCount;
	}

	public int getTwentyDollarCount() {
		return twentyDollarCount;
	}

	public float getAmount() {
		return  oneCentCount * 0.01f + tenCentCount * 0.10f + quarterCount * 0.25f + oneDollarCount * 1f
				+ fiveDollarCount * 5f + twentyDollarCount * 20f;
	}

	public Money(int oneCentCount, int tenCentCount, int quarterCount, int oneDollarCount, int fiveDollarCount,
			int twentyDollarCount) {
		if (oneCentCount < 0)
			throw new IllegalStateException();
		if (tenCentCount < 0)
			throw new IllegalStateException();
		if (quarterCount < 0)
			throw new IllegalStateException();
		if (oneDollarCount < 0)
			throw new IllegalStateException();
		if (fiveDollarCount < 0)
			throw new IllegalStateException();
		if (twentyDollarCount < 0)
			throw new IllegalStateException();

		this.oneCentCount = oneCentCount;
		this.tenCentCount = tenCentCount;
		this.quarterCount = quarterCount;
		this.oneDollarCount = oneDollarCount;
		this.fiveDollarCount = fiveDollarCount;
		this.twentyDollarCount = twentyDollarCount;
	}

	public static Money add(Money money1, Money money2) {
		Money sum = new Money(money1.oneCentCount + money2.oneCentCount, money1.tenCentCount + money2.tenCentCount,
				money1.quarterCount + money2.quarterCount, money1.oneDollarCount + money2.oneDollarCount,
				money1.fiveDollarCount + money2.fiveDollarCount, money1.twentyDollarCount + money2.twentyDollarCount);
		return sum;
	}

	public Money add(Money other){
        Money sum = new Money(
            oneCentCount + other.oneCentCount,
            tenCentCount + other.tenCentCount,
            quarterCount + other.quarterCount,
            oneDollarCount + other.oneDollarCount,
            fiveDollarCount + other.fiveDollarCount,
            twentyDollarCount + other.twentyDollarCount);
        return sum;
    }
	
	public Money substract(Money other) {
		return new Money(oneCentCount - other.oneCentCount, tenCentCount - other.tenCentCount,
				quarterCount - other.quarterCount, oneDollarCount - other.oneDollarCount,
				fiveDollarCount - other.fiveDollarCount, twentyDollarCount - other.twentyDollarCount);
	}

	@Override
	protected boolean equalsCore(Money other) {
		return oneCentCount == other.oneCentCount && tenCentCount == other.tenCentCount
				&& quarterCount == other.quarterCount && oneDollarCount == other.oneDollarCount
				&& fiveDollarCount == other.fiveDollarCount && twentyDollarCount == other.twentyDollarCount;
	}

	@Override
	protected int getHashCodeCore() {
		int hashCode = oneCentCount;
		hashCode = (hashCode * 397) ^ tenCentCount;
		hashCode = (hashCode * 397) ^ quarterCount;
		hashCode = (hashCode * 397) ^ oneDollarCount;
		hashCode = (hashCode * 397) ^ fiveDollarCount;
		hashCode = (hashCode * 397) ^ twentyDollarCount;
		return hashCode;
	}
}