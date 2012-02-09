package ch.hsr.challp.and4.billing;

import java.util.Observer;

public interface Observable {
	public void addObserver(Observer obsrNewObserver);

	public void notifyObservers();

	public void removeObserver(Observer obsrToRemove);
}
