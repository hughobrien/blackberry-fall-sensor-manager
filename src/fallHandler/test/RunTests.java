package fallHandler.test;

import fallHandler.datastore.DataStore;
import fallHandler.datastore.SourceList;
import fallHandler.datastore.SourceList.SourceListEntry;

/**
 * List of methods to test that errors are caught and that valid messages are
 * passed.
 * 
 * @author Hugh O'Brien
 */
public final class RunTests {

	/**
	 * Calls each of the tests in series.
	 */
	public static void runAll() {

		System.out.flush();

		TestDataStore.runAll();
		TestSimulatedByteSource.runAll();
		TestFallSensorRecord.runAll();
		TestFallSensorHeader.runAll();
		TestFallSensorMessage.runAll();
		TestObservable.runAll();
		TestMobilityMessage.runAll();
		TestMessageVerifier.runAll();
		TestSourceList.runAll();

		System.out.println(DataStore.getEntries());

		SourceListEntry[] knownSources =
			((SourceList) DataStore.getSourceList())
				.getSources();

		for (int i = 0; i < knownSources.length; i++) {
			System.out.println("SourceID: " + knownSources[i].sourceID
				+ "   Source: " + knownSources[i].byteSource);
		}

		System.out.flush();
	}
}
