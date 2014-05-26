package fallHandler.datastore;
//package fallHandler.datastore;
//
//import java.util.Hashtable;
//
//import net.rim.device.api.system.PersistentObject;
//import net.rim.device.api.system.PersistentStore;
//
///**
// * Persistent stored version of for a hashtable that stores and retrieves
// * Strings based on String keys, used to store configuration values.
// * 
// * @author Hugh O'Brien
// */
//public class Options {
//
//	/** Hashtable for storing the data. */
//	private Hashtable db;
//
//	/** Persistent access handle. */
//	private PersistentObject persisted;
//
//	/** Key for this persistent object. */
//	final long key = "datastore.Options".hashCode();
//
//	/**
//	 * Simply calls the initialise method.
//	 */
//	Options() {
//
//		initialise();
//	}
//
//	/**
//	 * If this object exists in the persistence store it is automatically
//	 * retrieved, other wise a new instantiation with default values is made.
//	 */
//	private void initialise() {
//
//		persisted = PersistentStore.getPersistentObject(key);
//
//		if (persisted.getContents() == null) {
//
//			db = new Hashtable();
//			persisted.setContents(db);
//			persisted.commit();
//			setDefaults();
//		}
//		else {
//			db = (Hashtable) persisted.getContents();
//		}
//	}
//
//	/**
//	 * Assign a value to a record.
//	 * 
//	 * @param record Record to access.
//	 * @param newValue Value to store.
//	 */
//	public void setValue(String record, String newValue) {
//
//		db.put(record, newValue);
//		persisted.commit();
//
//	}
//
//	/**
//	 * Retrieve a value from a record.
//	 * 
//	 * @param record Record to access.
//	 * @return Value of the record or "No Such Record".
//	 */
//	public String getValue(String record) {
//
//		if (db.contains(record)) {
//			return (String) db.get(record);
//		}
//
//		return "Unknown";
//
//	}
//
//	/**
//	 * Initialise the data with some default values (First run only).
//	 */
//	private void setDefaults() {
//
//		setValue(Records.options.numbertodial, "+353853133749");
//		setValue(Records.options.loadfallhandler, "no");
//		setValue(Records.options.loadfallalertacknowledger, "no");
//	}
//
//	/**
//	 * Clears the persistent entry for Options and reinitialises it to the
//	 * default values.
//	 */
//	public void reset() {
//
//		PersistentStore.destroyPersistentObject(key);
//		initialise();
//	}
//}
