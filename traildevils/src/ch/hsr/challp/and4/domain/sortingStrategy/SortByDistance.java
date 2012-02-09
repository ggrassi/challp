package ch.hsr.challp.and4.domain.sortingStrategy;

import java.io.Serializable;

import ch.hsr.challp.and4.domain.Trail;

public class SortByDistance implements SortStrategy, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6986360815230879446L;

	public int sortingAlgorithm(Trail f, Trail s) {
		return f.getDistance() - s.getDistance();
	}
}
