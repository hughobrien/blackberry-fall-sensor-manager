package fallHandler.test;

import fallHandler.datastore.FallSensorRecord;
import fallHandler.datastore.TestEntryPoint;

/**
 * Extensive tests for methods in the FallSensorRecord class.
 * 
 * @author Hugh O'Brien
 */
public final class TestFallSensorRecord {

	/**
	 * Execute all defined tests.
	 */
	public static void runAll() {

		testFallSensorRecordConstructor();
		testFallSensorRecordStore();
		testFallSensorRecordGetVal();
		testFallSensorRecordGetName();
		testFallSensorRecordIncrement();
		testFallSensorRecordDecrement();
		testFallSensorRecordAddTo();
		testFallSensorRecordToString();
	}

	/**
	 * Test the constructor for FallRecordSensor.
	 */
	private static void testFallSensorRecordConstructor() {

		FallSensorRecord value =
			TestEntryPoint.FallSensorRecordConstructor("testConstructor", 100);

		System.out.println("testFallSensorRecordConstructor1 "
			+ ((value.getName().equals("testConstructor")) ? "passed."
				: "failed."));

		System.out.println("testFallSensorRecordConstructor2 "
			+ ((value.getVal() == 100) ? "passed." : "failed."));

	}

	/**
	 * Test the store method of FallSensorRecord.
	 */
	private static void testFallSensorRecordStore() {

		FallSensorRecord record =
			TestEntryPoint.FallSensorRecordConstructor("storeTest", 100);

		TestEntryPoint.FallSensorRecordStore(record, 200);

		System.out.println("testFallSensorRecordStore "
			+ ((record.getVal() == 200) ? "passed." : "failed."));

	}

	/**
	 * Test the getVal method of FallSensorRecord.
	 */
	private static void testFallSensorRecordGetVal() {

		FallSensorRecord record =
			TestEntryPoint.FallSensorRecordConstructor("getValTest", 100);

		System.out.println("testFallSensorRecordGetVal "
			+ ((record.getVal() == 100) ? "passed." : "failed."));

	}

	/**
	 * Test the getName method of FallSensorRecord.
	 */
	private static void testFallSensorRecordGetName() {

		FallSensorRecord record =
			TestEntryPoint.FallSensorRecordConstructor("getNameTest", 100);

		System.out
			.println("testFallSensorRecordGetName "
				+ ((record.getName().equals("getNameTest")) ? "passed."
					: "failed."));

	}

	/**
	 * Test the increment method of FallSensorRecord.
	 */
	private static void testFallSensorRecordIncrement() {

		FallSensorRecord record =
			TestEntryPoint.FallSensorRecordConstructor("incrementTest", 100);

		TestEntryPoint.FallSensorRecordIncrement(record);

		System.out.println("testFallSensorRecordIncrement "
			+ ((record.getVal() == 101) ? "passed." : "failed."));

	}

	/**
	 * Test the decrement method of FallSensorRecord.
	 */
	private static void testFallSensorRecordDecrement() {

		FallSensorRecord record =
			TestEntryPoint.FallSensorRecordConstructor("decrementTest", 100);

		TestEntryPoint.FallSensorRecordDecrement(record);

		System.out.println("testFallSensorRecordDecrement "
			+ ((record.getVal() == 99) ? "passed." : "failed."));

	}

	/**
	 * Test the addTo method of FallSensorRecord.
	 */
	private static void testFallSensorRecordAddTo() {

		FallSensorRecord record =
			TestEntryPoint.FallSensorRecordConstructor("addToTest", 100);

		TestEntryPoint.FallSensorRecordAddTo(record, 100);

		System.out.println("testFallSensorRecordAddTo "
			+ ((record.getVal() == 200) ? "passed." : "failed."));
	}

	/**
	 * Directly print the String representation of a FallSensorRecord.
	 */
	private static void testFallSensorRecordToString() {

		System.out
			.println("testFallSensorRecordToString "
				+ ((TestEntryPoint.FallSensorRecordConstructor("toStringTest",
					100).toString().equals("toStringTest: 100") ? "passed."
					: "failed.")));
	}

}
