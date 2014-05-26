package fallHandler.test;

import fallHandler.datastore.DataStore;
import fallHandler.datastore.Observable;
import fallHandler.datastore.Observable.Observer;
import fallHandler.datastore.SourceList;
import fallHandler.datastore.TestEntryPoint;
import fallHandler.datastore.SourceList.SourceListEntry;

/**
 * Perform a series of tests on the DataStore class and the associated
 * FallSensorRecord class.
 * 
 * @author Hugh O'Brien
 */
public final class TestDataStore {

	/** Boolean for the observer test to twiddle */
	public static boolean observerCalled = false;

	/**
	 * Run all defined tests.
	 */
	public static void runAll() {

		testDataStoreAddTo();
		testDataStoreGetEntries();
		testDataStoreStoreAndRetrieve();
		testDataStoreIncrement();
		testDataStoreDecrement();
		testDataStoreObserver();
		testDataStoreAddGetFallSensorIDs();
		

	}

	/**
	 * Store a value in the DataStore and attempt to retrieve it.
	 */
	private static void testDataStoreStoreAndRetrieve() {

		/* store in a non-existent record */
		DataStore.store("testRecord", 100);
		System.out.println("testDataStoreStoreAndRetrieve1 "
			+ ((DataStore.retrieve("testRecord") == 100) ? "passed."
				: "failed."));

		/* test for default value */
		System.out.println("testDataStoreStoreAndRetrieve2 "
			+ ((DataStore.retrieve("invalidRecord") == 0) ? "passed."
				: "failed."));

		/* store in an existent record */
		DataStore.store("testRecord", 200);
		System.out.println("testDataStoreStoreAndRetrieve3 "
			+ ((DataStore.retrieve("testRecord") == 200) ? "passed."
				: "failed."));

		/* Test that the record name is properly saved */
		System.out.println("testDataStoreStoreAndRetrieve4 "
			+ ((DataStore.getRecord("testName").getName().equals("testName"))
				? "passed." : "failed."));

	}

	/**
	 * Call the getEntries method and print it's results.
	 */
	private static void testDataStoreGetEntries() {

		System.out.println("testDataStoreGetEntries "
			+ ((DataStore.getEntries().equals(
				"[Records Start:, " + "\n" + "addToTest1: 50, " + "\n"
					+ "addToTest2: 200, " + "\n" + "Records End.]") ? "passed."
				: "failed.")));

	}

	/**
	 * Test the observer features by registering and observer, triggering and
	 * update and seeing if the update() method was called.
	 */
	private static void testDataStoreObserver() {

		Observer testObserver = new Observer() {

			public void update(Observable updatedObject) {

				TestDataStore.observerCalled = true;
			}
		};

		DataStore.getRecord("observerTest").addObserver(testObserver);
		DataStore.store("observerTest", 100);

		System.out.println("testDataStoreObserver "
			+ ((observerCalled) ? "passed." : "failed."));
	}

	/**
	 * Test the DataStore.increment method.
	 */
	private static void testDataStoreIncrement() {

		/* non-existent record */
		TestEntryPoint.DataStoreIncrement("incrementTest1");

		System.out.println("testDataStoreIncrement1 "
			+ ((DataStore.retrieve("incrementTest1") == 1) ? "passed."
				: "failed."));

		/* existent record */
		DataStore.store("incrementTest2", 100);
		TestEntryPoint.DataStoreIncrement("incrementTest2");

		System.out.println("testDataStoreIncrement2 "
			+ ((DataStore.retrieve("incrementTest2") == 101) ? "passed."
				: "failed."));
	}

	/**
	 * Test the DataStore.decrement method.
	 */
	private static void testDataStoreDecrement() {

		/* non-existent record */
		TestEntryPoint.DataStoreDecrement("decrementTest1");

		System.out.println("testDataStoreDecrement1 "
			+ ((DataStore.retrieve("decrementTest1") == -1) ? "passed."
				: "failed."));

		/* existent record */
		DataStore.store("decrementTest2", 100);
		TestEntryPoint.DataStoreDecrement("decrementTest2");

		System.out.println("testDataStoreDecrement2 "
			+ ((DataStore.retrieve("decrementTest2") == 99) ? "passed."
				: "failed."));
	}

	/**
	 * Test the DataStore.addTo method.
	 */
	private static void testDataStoreAddTo() {

		/* non-existent record */
		TestEntryPoint.DataStoreAddTo("addToTest1", 50);

		System.out
			.println("testDataStoreAddTo1 "
				+ ((DataStore.retrieve("addToTest1") == 50) ? "passed."
					: "failed."));

		/* existent record */
		DataStore.store("addToTest2", 100);
		TestEntryPoint.DataStoreAddTo("addToTest2", 100);

		System.out.println("testDataStoreAddTo2 "
			+ ((DataStore.retrieve("addToTest2") == 200) ? "passed."
				: "failed."));
	}

	/**
	 * Test that FallSensorIDs are logged properly.
	 */
	private static void testDataStoreAddGetFallSensorIDs() {

		boolean passed = false;
		int testSource = 100;

		((SourceList) DataStore.getSourceList())
			.addSource(testSource, null);

		SourceListEntry[] srcList =
			((SourceList) DataStore.getSourceList())
				.getSources();

		for (int i = 0; i < srcList.length; i++) {
			if (srcList[i].sourceID == testSource)
				passed = true;

		}

		System.out.println("testDataStoreAddGetFallSensorIDs "
			+ ((passed) ? "passed." : "failed."));
	}

	
}
