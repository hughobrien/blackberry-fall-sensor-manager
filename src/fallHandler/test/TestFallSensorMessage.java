package fallHandler.test;

import fallHandler.datastore.DataStore;
import fallHandler.datastore.FallSensorHeader;
import fallHandler.datastore.Records;
import fallHandler.datastore.TestEntryPoint;

/**
 * Test the message dispatch features of FallSensorMessage.
 * 
 * @author Hugh O'Brien
 */
public final class TestFallSensorMessage {

	/**
	 * Run all defined tests.
	 */
	public static void runAll() {

		testValidMessageType();
		testInvalidMessageType();
	}

	/**
	 * Test that invalid message types are detected.
	 */
	private static void testInvalidMessageType() {

		int[] badMessage =
			{
				FallSensorHeader.HEADERMSG_LENGTH,
				0x50,
				0x00,
				0x00,
				0x4a,
				0x00,
				0x00,
				0x00, };

		int existingErrors = DataStore.retrieve(Records.errors.type);
		int expectedErrors = existingErrors + 1;

		TestEntryPoint.FallSensorMessageConstructor(badMessage);

		existingErrors = DataStore.retrieve(Records.errors.type);

		System.out.println("testFallSensorMessageInvalidMessageType "
			+ ((existingErrors == expectedErrors) ? "passed." : "failed."));

	}

	/**
	 * Test that valid message types are not logged.
	 */
	private static void testValidMessageType() {

		int[] goodMessage1 = SampleMessages.altMobilityMsg;

		int[] goodMessage2 =
			{
				FallSensorHeader.HEADERMSG_LENGTH,
				FallSensorHeader.HEADERMSG_TYPE,
				0x00,
				0x00,
				0x4a,
				0x00,
				0x00,
				0x00, };

		int existingErrors = DataStore.retrieve(Records.errors.type);
		int expectedErrors = existingErrors;

		TestEntryPoint.FallSensorMessageConstructor(goodMessage1);
		TestEntryPoint.FallSensorMessageConstructor(goodMessage2);

		existingErrors = DataStore.retrieve(Records.errors.type);

		System.out.println("testFallSensorMessageValidMessageType "
			+ ((existingErrors == expectedErrors) ? "passed." : "failed."));

	}

}
