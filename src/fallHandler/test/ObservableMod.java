package fallHandler.test;

import fallHandler.datastore.Observable;

/**
 * Hack on Observable to allow test code to trigger Observer updates.
 * 
 * @author Hugh O'Brien
 */
public class ObservableMod extends Observable {

	/**
	 * Calls the protected method notifyObservers.
	 */
	public void triggerUpdate() {

		notifyObservers();
	}
}
