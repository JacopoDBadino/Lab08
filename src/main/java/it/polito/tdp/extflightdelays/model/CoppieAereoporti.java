package it.polito.tdp.extflightdelays.model;

import java.util.HashSet;
import java.util.Set;

public class CoppieAereoporti {
	Airport air1;
	Airport air2;
	int cnt = 0;
	int dist = 0;

	public CoppieAereoporti(Airport air1, Airport air2, int cnt, int dist) {
		this.air1 = air1;
		this.air2 = air2;
		this.cnt = cnt;
		this.dist = dist;
	}

	public Airport getAir1() {
		return air1;
	}

	public void setAir1(Airport air1) {
		this.air1 = air1;
	}

	public Airport getAir2() {
		return air2;
	}

	public void setAir2(Airport air2) {
		this.air2 = air2;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public int getDist() {
		return dist;
	}

	public void setDist(int dist) {
		this.dist = dist;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((air1 == null) ? 0 : air1.hashCode());
		result = prime * result + ((air2 == null) ? 0 : air2.hashCode());
		result = prime * result + cnt;
		result = prime * result + dist;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoppieAereoporti other = (CoppieAereoporti) obj;
		if (air1 == null) {
			if (other.air1 != null)
				return false;
		} else if (!air1.equals(other.air1))
			return false;
		if (air2 == null) {
			if (other.air2 != null)
				return false;
		} else if (!air2.equals(other.air2))
			return false;
		if (cnt != other.cnt)
			return false;
		if (dist != other.dist)
			return false;
		return true;
	}
	
	

}
