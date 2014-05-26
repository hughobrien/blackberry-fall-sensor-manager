package fallHandler.datastore;

import java.util.Vector;

/**
 * Observable is a simple implementation of the observer pattern. Add, remove
 * and delete operations are specified.
 * 
 * @author Hugh O'Brien
 */

public abstract class Observable {

	/** Vector to store the list of observers */
	private Vector observers;

	/**
	 * Add Observer to list of observers, duplicates silently ignored.
	 * 
	 * @param newObserver Observer to register
	 */
	public void addObserver(Observer newObserver) {

		/* Create the object if needs be */
		if (observers == null)
			observers = new Vector();

		if (!observers.contains(newObserver))
			observers.addElement(newObserver);
	}

	/**
	 * Remove Observer from list, nonexistent Observers ignored.
	 * 
	 * @param leavingObserver Reference of Observer to remove
	 */
	public void deleteObserver(Observer leavingObserver) {

		if (observers != null) {
			if (observers.contains(leavingObserver))
				observers.removeElement(leavingObserver);
		}
	}

	/**
	 * Call the update() method on all registered Observers.
	 */
	protected void notifyObservers() {

		if (observers != null) {

			for (int i = 0; i < observers.size(); i++) {
				Observer currentObserver;
				currentObserver = (Observer) observers.elementAt(i);
				currentObserver.update(this);
			}
		}
	}

	/**
	 * An Observer may register with a target and be notified of changes.
	 * 
	 * @author Hugh O'Brien
	 */
	public interface Observer {

		/**
		 * The Observable object will call update when a change occurs.
		 * 
		 * @param updatedObject Reference to the object that called the update.
		 */
		void update(Observable updatedObject);

	}

}
