package ch.hsr.challp.and4.domain.sortingStrategy;

import java.io.Serializable;

import ch.hsr.challp.and4.domain.Trail;

public class SortByPopularity implements SortStrategy, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -966265278401016991L;

	public int sortingAlgorithm(Trail f, Trail s) {
		return s.getFavorits()-f.getFavorits();
	}

}
