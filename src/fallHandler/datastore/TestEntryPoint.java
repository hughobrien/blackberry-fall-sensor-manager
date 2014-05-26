package fallHandler.datastore;

/**
 * Provides method proxying for test functions that are not publicly accessible.
 * 
 * @author Hugh O'Brien
 */
public final class TestEntryPoint {

	/**
	 * Access the package-access constructor for a FallSensorRecord.
	 * 
	 * @param name Name to pass to constructor.
	 * @param val Value to pass to constructor.
	 * @return Reference to the FallSensorRecord.
	 */
	public static FallSensorRecord FallSensorRecordConstructor(String name,
		int val) {

		return new FallSensorRecord(name, val);
	}

	/**
	 * Access the package-access FallSensorRecord.store method.
	 * 
	 * @param record Record to access.
	 * @param valueToStore Value to store.
	 */
	public static void FallSensorRecordStore(FallSensorRecord record,
		int valueToStore) {

		record.setVal(valueToStore);
	}

	/**
	 * Access the package-access FallSensorRecord.increment method.
	 * 
	 * @param record Record to access.
	 */
	public static void FallSensorRecordIncrement(FallSensorRecord record) {

		record.increment();

	}

	/**
	 * Access the package-access FallSensorRecord.decrement method.
	 * 
	 * @param record Record to access.
	 */
	public static void FallSensorRecordDecrement(FallSensorRecord record) {

		record.decrement();

	}

	/**
	 * Access the package-access FallSensorRecord.addTo method.
	 * 
	 * @param record Record to access.
	 * @param valueToAdd Value to add.
	 */
	public static void FallSensorRecordAddTo(FallSensorRecord record,
		int valueToAdd) {

		record.addTo(valueToAdd);

	}

	/**
	 * Access the package-access DataStore.increment method.
	 * 
	 * @param record Record to increment.
	 */
	public static void DataStoreIncrement(String record) {

		DataStore.increment(record);

	}

	/**
	 * Access the package-access DataStore.decrement method.
	 * 
	 * @param record Record to decrement.
	 */
	public static void DataStoreDecrement(String record) {

		DataStore.decrement(record);

	}

	/**
	 * Access the DataStore.addTo method.
	 * 
	 * @param record Record to add to
	 * @param valuetoAdd Value to add.
	 */
	public static void DataStoreAddTo(String record, int valuetoAdd) {

		DataStore.addTo(record, valuetoAdd);

	}

	/**
	 * Return an instance of a FallSensorHeader
	 * 
	 * @param input Input byte [].
	 * @return Reference to new FallSensorHeader.
	 */
	public static FallSensorHeader FallSensorHeaderConstructor(int[] input) {

		return new FallSensorHeader(input);
	}

	/**
	 * Access the package-access msgType field of a FallSensorHeader.
	 * 
	 * @param header FallSensorHeader to access.
	 * @return Value of the msgType field.
	 */
	public static int FallSensorHeaderMsgType(FallSensorHeader header) {

		return header.type;
	}

	/**
	 * Return an instance of a FallSensorMessage
	 * 
	 * @param input Input byte [].
	 * @return Reference to new FallSensorMessage.
	 */
	public static FallSensorMessage FallSensorMessageConstructor(int[] input) {

		return new FallSensorMessage(input, null);
	}

	/**
	 * Returns a new instance of a SourceList.
	 * 
	 * @return Reference to new SourceList.
	 */
	public static SourceList SourceListConstructor() {

		return new SourceList();
	}

}
