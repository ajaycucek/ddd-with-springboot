package ddd.logic.common;

import java.io.Serializable;

import org.hibernate.proxy.HibernateProxyHelper;

public abstract class Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	protected long id;

	@Override
	public boolean equals(Object obj) {
		Entity other = (Entity) obj;

		if (other == null)  // Reference equals
			return false;

		if (this == other)  // Reference equals
			return true;
		
		if (!this.getClass().equals(other.getClass()))
			return false;
		
		if (this.id == 0 || other.getId() == 0)
			return false;

		return this.id == other.getId();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}