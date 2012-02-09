package ch.hsr.challp.and4.domain.sortingStrategy;

import java.io.Serializable;

import ch.hsr.challp.and4.domain.Trail;

public class SortByDistance implements SortStrategy, Serializable {

	public int sortingAlgorithm(Trail f, Trail s) {
		return f.getDistance() - s.getDistance();
	}
}
