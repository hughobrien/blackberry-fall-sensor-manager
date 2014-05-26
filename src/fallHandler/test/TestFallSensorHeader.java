package fallHandler.test;

import fallHandler.datastore.DataStore;
import fallHandler.datastore.MobilityMessage;
import fallHandler.datastore.Records;
import fallHandler.datastore.TestEntryPoint;

/**
 * Test the methods and error catchers of the FallSensorHeader class.
 * 
 * @author Hugh O'Brien
 */
public final class TestFallSensorHeader {

	/**
	 * Run all defined tests.
	 */
	public static void runAll() {

		testMsgTooShort();
		testBadSeqNumbers();
		testGoodSeqNumbers();
		testUpdateDataStore();
		testLogStatistics();
		testMessageTypeIdentification();
	}

	/**
	 * Ensure that FallSensorHeader correctly identifies the message type.
	 */
	private static void testMessageTypeIdentification() {

		/* all status flags high, seqHi, seqLo and checksum are zero */
		int[] msg =
			{ 8, MobilityMessage.MOBILITYMSG_TYPE, 10, 11, 0xff, 0, 0, 0 };

		int receivedMsgType =
			TestEntryPoint.FallSensorHeaderMsgType(TestEntryPoint
				.FallSensorHeaderConstructor(msg));

		System.out.println("testFallSensorHeaderMessageTypeIdentification "
			+ ((receivedMsgType == MobilityMessage.MOBILITYMSG_TYPE)
				? "passed." : "failed."));

	}

	/**
	 * Test message too short catcher.
	 */
	private static void testMsgTooShort() {

		boolean caught = false;
		int[] msg = { 1, 2, 3 };

		try {
			TestEntryPoint.FallSensorHeaderConstructor(msg);
		}
		catch (IndexOutOfBoundsException e) {
			caught = true;

		}

		System.out.println("testFallSensorHeaderMsgTooShort "
			+ ((caught) ? "passed." : "failed."));

	}

	/**
	 * Test the bad sequence number error logging code of FallSensorHeader.
	 */
	private static void testBadSeqNumbers() {

		int source = 5;

		int[] msg = { 1, 2, 3, source, 5, 0, 7, 8 }; // sequence number == 7

		DataStore.store(source + Records.prependsrc.proto.sequenceerrors, 5);
		DataStore.store(source
			+ Records.prependsrc.proto.expectedsequencenumber, 10);

		TestEntryPoint.FallSensorHeaderConstructor(msg);

		int sequenceErrors =
			DataStore
				.retrieve(source + Records.prependsrc.proto.sequenceerrors);

		System.out.println("testFallSensorHeaderBadSeqNumbers "
			+ ((sequenceErrors == 6) ? "passed." : "failed."));

	}

	/**
	 * Test the bad sequence number error logging code of FallSensorHeader.
	 */
	private static void testGoodSeqNumbers() {

		int source = 5;
		int existingErrors = 6;
		int sequenceNumber = 7;

		int[] msg = { 1, 2, 3, source, 5, 0, sequenceNumber, 8 };

		DataStore.store(source + Records.prependsrc.proto.sequenceerrors,
			existingErrors);
		DataStore.store(source
			+ Records.prependsrc.proto.expectedsequencenumber, sequenceNumber);

		TestEntryPoint.FallSensorHeaderConstructor(msg);

		int sequenceErrors =
			DataStore
				.retrieve(source + Records.prependsrc.proto.sequenceerrors);

		System.out.println("testFallSensorHeaderBadSeqNumbers "
			+ ((sequenceErrors == existingErrors) ? "passed." : "failed."));

	}

	/**
	 * Test that values in the header are communicated to the DataStore.
	 */
	private static void testUpdateDataStore() {

		int source = 7;
		/* all status flags high, seqHi, seqLo and checksum are zero */
		int[] msg = { 10, 10, 10, source, 0xff, 0, 0, 0 };

		TestEntryPoint.FallSensorHeaderConstructor(msg);

		int highBits = 0;

		highBits +=
			DataStore.retrieve(source + Records.prependsrc.status.batterylow);
		highBits +=
			DataStore.retrieve(source + Records.prependsrc.status.cardfull);
		highBits +=
			DataStore.retrieve(source + Records.prependsrc.status.falldetected);
		highBits +=
			DataStore
				.retrieve(source + Records.prependsrc.status.messagemissed);
		highBits +=
			DataStore.retrieve(source + Records.prependsrc.status.onmainspower);
		highBits +=
			DataStore.retrieve(source + Records.prependsrc.status.requesttime);
		highBits +=
			DataStore.retrieve(source + Records.prependsrc.status.sensorinuse);
		highBits +=
			DataStore.retrieve(source + Records.prependsrc.status.signallow);

		System.out.println("testFallSensorHeaderUpdateDataStore "
			+ ((highBits == 8) ? "passed." : "failed."));

	}

	/**
	 * Test the message length and type monitoring for statistical purposes.
	 */
	private static void testLogStatistics() {

		int source = 4;
		int length = 50;
		int type = 60;
		int[] msg = { length, type, 0, source, 0, 0, 0, 0 };

		DataStore.store(source + Records.prependsrc.config.debugprotocol, 1);

		TestEntryPoint.FallSensorHeaderConstructor(msg);
		TestEntryPoint.FallSensorHeaderConstructor(msg);

		System.out.println("testFallSensorHeaderLogStatistics1 "
			+ ((DataStore.retrieve(source
				+ Records.prependsrc.proto.append.lengths + length) == 2)
				? "passed." : "failed."));

		System.out.println("testFallSensorHeaderLogStatistics2 "
			+ ((DataStore.retrieve(source
				+ Records.prependsrc.proto.append.types + type) == 2)
				? "passed." : "failed."));

	}

}
