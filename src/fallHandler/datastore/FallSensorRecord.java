package fallHandler.datastore;

import java.text.DateFormat;
import java.util.Date;


/**
 * FallSensorRecords store the name and value of a certain type of data. Every
 * record is observable even before it has received data.
 * 
 * @author Hugh O'Brien
 */
public final class FallSensorRecord extends Observable {

	/** Name of the record. */
	private final String name;
	/** Last update time */
	private long updateTime = 0;
	/** Initial record value. */
	private int value = 0;

	/**
	 * Update the record with the current timestamp.
	 */
	private void logTime() {

		updateTime = System.currentTimeMillis();
	}

	/**
	 * Create a new record with a name and a value.
	 * 
	 * @param newName Name of record.
	 * @param newValue Value of record.
	 */
	FallSensorRecord(String newName, int newValue) {

		name = newName;
		setVal(newValue);
	}

	/**
	 * Store a new value in the current record.
	 * 
	 * @param newValue Value to store
	 */
	public void setVal(int newValue) {

		/* Avoid triggering a notification if the value is not a change */
		if (newValue != value) {
			value = newValue;
			logTime();
			notifyObservers();
		}
	}

	/**
	 * Retrieve the current value of the record.
	 * 
	 * @return The value of the record.
	 */
	public int getVal() {

		return value;
	}

	/**
	 * Retrieve the name of the record.
	 * 
	 * @return The name of the record.
	 */
	public String getName() {

		return name;
	}

	/**
	 * Increment the current value of the record by one.
	 */
	void increment() {

		value++;
		logTime();
		notifyObservers();
	}

	/**
	 * Decrement the current value of the record by one.
	 */
	void decrement() {

		value--;
		logTime();
		notifyObservers();
	}

	/**
	 * Sum the specified value with the current record value.
	 * 
	 * @param valueToAdd Value to sum with the current value.
	 */
	void addTo(int valueToAdd) {

		/* Avoid triggering a notification if the value has not changed */
		if (valueToAdd != 0) {
			value += valueToAdd;
			logTime();
			notifyObservers();
		}
	}

	/**
	 * Represent the record's name, value and update time as a String.
	 * 
	 * @return String representation of the record.
	 */
	public String toString() {

		return name + ": " + value;
	}

	/**
	 * Represent the record's name, value and update time as a String.
	 * 
	 * @return String representation of the record.
	 */
	public String toStringWithTime() {

		return name
			+ ": "
			+ value
			+ ", "
			+ ((updateTime == 0) ? "No Updates" : 
				DateFormat.getDateTimeInstance(
						DateFormat.LONG, 
						DateFormat.LONG)
						.format(new Date(updateTime)));
	}

	;
	
}
