package ch.hsr.challp.and4.domain.sortingStrategy;

import java.io.Serializable;

import ch.hsr.challp.and4.domain.Trail;

public class SortByName implements SortStrategy, Serializable {

	public int sortingAlgorithm(Trail f, Trail s) {
		if(f.getName() == null){
			f.setName("");
		}
		if(s.getName() == null){
			s.setName("");
		}
		return f.getName().compareTo(s.getName());
	}

}
