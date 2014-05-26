package fallHandler.test;

import fallHandler.bytesource.SimulatedByteSource;
import fallHandler.datastore.DataStore;
import fallHandler.datastore.FallSensorHeader;
import fallHandler.datastore.MessageVerifier;
import fallHandler.datastore.MobilityMessage;
import fallHandler.datastore.Records;

/**
 * Test that error detection and value handling are correctly performed in the
 * MobilityMessage class.
 * 
 * @author Hugh O'Brien
 */
public final class TestMobilityMessage {

	/**
	 * Run all defined tests.
	 */
	public static void runAll() {

		testMsgTooShort();
		testMsgTooLong();
		testDataStoreUpdate();

	}

	/**
	 * Send a truncated mobility message.
	 */
	private static void testMsgTooShort() {

		boolean caught = false;

		int[] shortMsg =
			{
				MessageVerifier.MSG_MARK_START,
				FallSensorHeader.HEADERMSG_LENGTH + 1,
				MobilityMessage.MOBILITYMSG_TYPE,
				0,
				8,
				0,
				0,
				0,
				0,
				10,
				MessageVerifier.MSG_MARK_END };

		SimulatedByteSource byteSource = new SimulatedByteSource();

		try {
			byteSource.sendMessageAuto(shortMsg);
		}
		catch (IndexOutOfBoundsException e) {
			caught = true;
		}

		System.out.println("testMobilityMessageMsgTooShort "
			+ ((caught) ? "passed." : "failed."));

	}

	/**
	 * Send an over sized mobility message.
	 */
	private static void testMsgTooLong() {

		boolean caught = false;

		int[] longMsg =
			{
				MessageVerifier.MSG_MARK_START,
				FallSensorHeader.HEADERMSG_LENGTH
					+ MobilityMessage.MOBILITYMSG_LENGTH + 1,
				MobilityMessage.MOBILITYMSG_TYPE,
				0,
				8,
				0,
				0,
				0,
				0,
				10,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
				0,
				MessageVerifier.MSG_MARK_END };

		SimulatedByteSource byteSource = new SimulatedByteSource();

		try {
			byteSource.sendMessageAuto(longMsg);
		}
		catch (IndexOutOfBoundsException e) {
			caught = true;
		}

		System.out.println("testMobilityMessageMsgTooLong "
			+ ((caught) ? "passed." : "failed."));

	}

	/**
	 * Test that the values in the message are stored in the DataStore.
	 */
	private static void testDataStoreUpdate() {

		SimulatedByteSource byteSource = new SimulatedByteSource();
		int source = 11;

		SampleMessages.goodMobilityMsg[FallSensorHeader.HEADERINDEX_SRC + 1] =
			source;

		byteSource.sendMessageAuto(SampleMessages.goodMobilityMsg);

		int goodCount = 0;

		goodCount +=
			DataStore.retrieve(source + Records.prependsrc.mobility.cadence);

		goodCount +=
			DataStore.retrieve(source + Records.prependsrc.mobility.steps);

		goodCount +=
			DataStore.retrieve(source + Records.prependsrc.mobility.gaitphase);

		goodCount +=
			DataStore.retrieve(source + Records.prependsrc.mobility.support);

		goodCount +=
			DataStore.retrieve(source
				+ Records.prependsrc.mobility.swingphasetime);

		goodCount +=
			DataStore.retrieve(source
				+ Records.prependsrc.mobility.stancephasetime);

		goodCount +=
			DataStore.retrieve(source
				+ Records.prependsrc.mobility.doublesupporttime);

		goodCount +=
			DataStore.retrieve(source + Records.prependsrc.mobility.steplength);

		/* 36 is the sum of the values as they appear in the test message */
		System.out.println("testMobilityMessageTestDataStoreUpdate "
			+ ((goodCount == 36) ? "passed." : "failed."));
	}
}
