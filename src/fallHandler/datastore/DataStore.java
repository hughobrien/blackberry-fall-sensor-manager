package fallHandler.datastore;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;


/**
 * The DataStore stores all information pertaining to the fall sensor readings
 * as well as configuration values for the agents. DataStore wraps most simple
 * functions such as storing data or retrieving it but to interact directly with
 * the records, most commonly to add an object as an observer it is necessary to
 * get the FallSensorRecord reference from the DataStore using the getRecord
 * command and then act on the FallSensorRecord object in whatever way is
 * appropriate.
 * 
 * @author Hugh O'Brien
 */
public final class DataStore {

	/**
	 * Hashtable chosen as more advanced Collections types are not available in
	 * J2ME/Blackberry. db will ALWAYS contain FallSensorRecords.
	 */
	private static final Hashtable db = new Hashtable();
	
	/**
	 * Special type to keep track of all known sources without duplicates.
	 */
	private static final SourceList sourceList = new SourceList();
	
	/**
	 * Create a new Options object, options' constructor tests to see if an persistent
	 * instance of Options exists on the device or creates a new Object with defaults
	 * if it does not.
	 */
//	private static final Options options = new Options();

	/**
	 * Store a value based on a String record. 'Store' will update the
	 * FallSensorRecord with the name specified or if it does not exist, will
	 * create it.
	 * 
	 * @param record Name of record
	 * @param value Value to store
	 */
	public static void store(String record, int value) {

		if (db.containsKey(record)) {
			((FallSensorRecord) db.get(record)).setVal(value);
		}
		else {
			db.put(record, new FallSensorRecord(record, value));
		}

	}

	/**
	 * Retrieves the integer value of the given record. Non-existent records are
	 * automatically created and the value 0 is returned.
	 * 
	 * @param record Name of the record to retrieve.
	 * @return The value of the record.
	 */
	public static int retrieve(String record) {

		if (db.containsKey(record)) {
			return ((FallSensorRecord) db.get(record)).getVal();
		}

		/* If the key did not exist, create it and retrieve the default value */
		store(record, 0);
		return retrieve(record);

	}

	/**
	 * Retrieve a reference to the handler for a particular type of datum. If
	 * the record exists, the handler reference is returned. If the record does
	 * not exist, a handler is created so that the calling function can register
	 * itself as an observer. The value at this point will be 0.
	 * 
	 * @param record Name of record to retrieve the handler for.
	 * @return The record handler.
	 */
	public static FallSensorRecord getRecord(String record) {

		if (db.containsKey(record)) {
			return (FallSensorRecord) db.get(record);
		}

		/* If the key did not exist, create it and retrieve the default value */
		store(record, 0);
		return getRecord(record);
	}

	/**
	 * Increment the value at the specified record by one. Saves a step when
	 * requesting, modifying and storing.
	 * 
	 * @param record Record to increment.
	 */
	static void increment(String record) {

		if (db.containsKey(record)) {
			((FallSensorRecord) db.get(record)).increment();
		}
		else {
			store(record, 1);
		}
	}

	/**
	 * Decrement the value at the specified record by one. Saves a step when
	 * requesting, modifying and storing.
	 * 
	 * @param record Record to decrement.
	 */
	static void decrement(String record) {

		if (db.containsKey(record)) {
			((FallSensorRecord) db.get(record)).decrement();
		}
		else {
			store(record, -1);
		}
	}

	/**
	 * Utility function to ease tracking of the step count, stores the sum of
	 * the current record value and the passed value as the new record value.
	 * 
	 * @param record Record to update.
	 * @param value Value to sum with the existing value.
	 */
	static void addTo(String record, int value) {

		if (db.containsKey(record)) {
			((FallSensorRecord) db.get(record)).addTo(value);
		}
		else {
			store(record, value);
		}
	}

	/**
	 * Get a sorted list of all known records and their associated values.
	 * 
	 * @return Many-lined sorted String of records and values.
	 */
	public static String getEntries() {
	
		Vector vector = new Vector();

		/* Get a list of the records and query each one for its String */
		for (Enumeration e = db.elements(); e.hasMoreElements();) {
			vector.addElement("\n" + e.nextElement().toString());
		}

		Collections.sort(vector);

		vector.insertElementAt("Records Start:", 0);
		vector.insertElementAt("\nRecords End.", vector.size());

		return vector.toString();

	}


	public static Observable getSourceList() {
		return sourceList;
	}
	
//	public static String getOption(String record) {
//		return options.getValue(record);
//	}
	
}
