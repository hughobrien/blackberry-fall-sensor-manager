package fallHandler.test;

import fallHandler.datastore.SourceList;
import fallHandler.datastore.TestEntryPoint;

/**
 * Unit tests for the SourceList Class.
 * 
 * @author Hugh O'Brien
 */
public final class TestSourceList {

	/**
	 * Run all defined tests.
	 */
	public static void runAll() {

		testDuplicateIgnore();
	}

	/**
	 * Test that entries are correctly added and that duplicates are ignored.
	 */
	private static void testDuplicateIgnore() {

		SourceList srcList = TestEntryPoint.SourceListConstructor();

		srcList.addSource(1, null);
		srcList.addSource(1, null);

		/* Test that there is only one entry in the returned array */
		System.out.println("testSourceListDuplicateIgnore "
			+ ((srcList.getSources().length == 1) ? "passed." : "failed."));
	}

}
